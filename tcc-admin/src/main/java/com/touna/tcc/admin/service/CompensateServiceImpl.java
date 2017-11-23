package com.touna.tcc.admin.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.log.dao.model.TxChild;
import com.touna.tcc.core.log.service.TxChildLogService;
import com.touna.tcc.core.log.service.TxLogService;
import com.touna.tcc.core.transaction.XaState;
import com.touna.tcc.dubbo.DubboConsumerHacker;

/**
 * Created by chenchaojian on 17/10/24.
 */
@Component
public class CompensateServiceImpl implements CompensateService {
    private static final Logger logger = LoggerFactory.getLogger(CompensateServiceImpl.class);


    static ExecutorService executorService =   new ThreadPoolExecutor(2,
                       8,
                       3000L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
                       new ThreadFactory(){
                           @Override
                           public Thread newThread(Runnable r) {
                                return new Thread(r,"Compensate job thread pool");
                           }
                       },
            new ThreadPoolExecutor.AbortPolicy());


    @Autowired
    TxLogService txLogService;

    @Autowired
    TxChildLogService txChildLogService;

    @Autowired
    private DubboConsumerHacker dubboConsumerHacker;

    private static Set<String> xidInProcessing = new ConcurrentHashSet<String>();

    @Override
    public void load() {

        List<Tx> txList = txLogService.getExceptionalTxs();
        if(txList != null){
            for(Tx tx:txList){
                //上一次的事务还没跑完，不能重复跑
                if(xidInProcessing.contains(tx.getXid())){
                    continue;
                }

                //所有的子事务回滚
                List<TxChild> txChildList = txChildLogService.getChildTxsByXid(tx.getXid());

                executorService.execute(new RecoverJob(tx,txChildList));

            }
        }
    }



    class RecoverJob implements Runnable{
        Tx tx;
        List<TxChild> txChildList;


        public RecoverJob(Tx tx,List<TxChild> txChildList){
            this.tx = tx;
            this.txChildList = txChildList;
        }

        @Override
        public void run() {
            try{

                if(XaState.TRY_FAIL.getState() == tx.getStatus() || XaState.ROLLBACK_FAIL.getState() == tx.getStatus()){

                    try {
                        for (TxChild ctx : txChildList) {
                        //child tx 不管是TRY_SUCCESS,TRY_FAIL都需要回滚。有可能是假的TRY_FAIL
                        //（因为网络原因，可能provider已经处理成功了。 ）

                            if(ctx.getStatus() == XaState.FINISH.getState()){//rollback 成功的不需要继续rollback
                                continue;
                            }

                            String clsName = ctx.getClsName();
                            Class clsIntf = Class.forName(clsName);

                            Class[] paramesTypes = ctx.getParamsTypesArray();
                            Object[] paramesValues = ctx.getParamValuesArray();

                            dubboConsumerHacker.invoke(clsIntf, paramesTypes, paramesValues, ctx.getDubboVersion(), ctx.getRollbackMethod());

                            //调用成功，更新child tx 状态
                            txChildLogService.trySuccess(ctx.getSequence(), ctx.getXid(), ctx.getcXid(), ctx.getClsName(), ctx.getCommitMethod(),
                                        ctx.getRollbackMethod(), ctx.getParamsTypesArray(), ctx.getParamValuesArray(), ctx.getDubboVersion());

                        }

                        txLogService.finish(tx.getXid());

                    }catch (Exception ex){
                        logger.warn(ex.getMessage(),ex);
                    }

                    //update xa state

                }
                else if(XaState.CONFIRM_FAIL.getState() == tx.getStatus()){
                    //confirm fail，需要重新confirm
                    try {
                        for (TxChild ctx : txChildList) {
                            if(ctx.getStatus() == XaState.CONFIRM_FAIL.getState()){
                                String clsName = ctx.getClsName();
                                Class clsIntf = Class.forName(clsName);

                                Class[] paramesTypes = ctx.getParamsTypesArray();
                                Object[] paramesValues = ctx.getParamValuesArray();

                                dubboConsumerHacker.invoke(clsIntf, paramesTypes, paramesValues, ctx.getDubboVersion(), ctx.getCommitMethod());

                                txChildLogService.finish(tx.getXid(), ctx.getcXid());
                            }
                        }

                        txLogService.finish(tx.getXid());
                    }
                    catch(Exception ex){
                        logger.warn(ex.getMessage(),ex);
                    }

                }
            }catch (Exception ex){
                logger.error("recover transaction "+tx.getXid()+" fail!",ex);
            }finally {
                //该事务处理完，从map删除
                xidInProcessing.remove(tx.getXid());
            }

        }
    }

}
