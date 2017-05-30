package com.touna.tcc.dubbo.filter;

import com.alibaba.dubbo.rpc.Result;
import com.touna.tcc.core.TCCFrameworkException;
import com.touna.tcc.core.TwoPhaseBusinessAction;
import com.touna.tcc.core.log.service.ChildTxLogService;
import com.touna.tcc.core.transaction.TCCInvokeMetadata;
import com.touna.tcc.core.transaction.Transaction;
import com.touna.tcc.core.transaction.TransactionSynchronizationManager;
import com.touna.tcc.core.transaction.XidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import java.lang.reflect.Method;

/**
 * Created by chenchaojian on 17/5/25.
 * tcc 分布式事务调用链记录。只需要记录root Participant的confirm,rollback上下文
 *
 * 注意：该拦截器需要最先靠近业务处理。避免其他的拦截器对invoker.invoke的返回结果做二次开发
 *
 */
@Activate(group = { Constants.CONSUMER },order=999)
public class TCCTransactionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TCCTransactionFilter.class);


    private ChildTxLogService childTxLogService;



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try{

            Transaction txObject = TransactionSynchronizationManager.getResource();
            if(txObject != null){//tcc分布式事务内的dubbo调用
                String xid = txObject.getXid();
                Class cls = invoker.getInterface();
                String methodName = invocation.getMethodName();
                Class[] paramsTypes = invocation.getParameterTypes();
                Method method = cls.getDeclaredMethod(methodName, paramsTypes);
                TwoPhaseBusinessAction annotation = method.getAnnotation(TwoPhaseBusinessAction.class);

                // dubbo filter 只需要记录try 阶段的数据,commit 和rollback阶段的在transactionManager记录
                if(annotation != null){
                    String commitMethod = annotation.commitMethod();
                    String rollbackMethod = annotation.rollbackMethod();


                    //TCCInvokeMetadata not in threadlocal
                    if(TransactionSynchronizationManager.getInvokeMetadata(cls.getName()) == null) {//try阶段
                        Object[] arguments = invocation.getArguments();

                        String cXid = XidGenerator.newCXid();

                        TCCInvokeMetadata invokeMetadata = new TCCInvokeMetadata();
                        invokeMetadata.setClsName(cls.getName());
                        invokeMetadata.setCommitMethod(commitMethod);
                        invokeMetadata.setRollbackMethod(rollbackMethod);
                        invokeMetadata.setParamsTypes(paramsTypes);
                        invokeMetadata.setParamValues(arguments);
                        invokeMetadata.setcXid(cXid);
                        TransactionSynchronizationManager.setTCCInvokeMetadata(invokeMetadata);

                        childTxLogService.begin(xid,cXid,cls.getName(),commitMethod,rollbackMethod,paramsTypes,arguments);

                    }

                }
            }
        }catch (NoSuchMethodException ex){
            logger.error(ex.getMessage(),ex);
            throw new TCCFrameworkException(ex.getMessage(),ex);
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new TCCFrameworkException(ex.getMessage(),ex);
        }


        Result result = invoker.invoke(invocation);

        return result;
    }

    public ChildTxLogService getChildTxLogService() {
        return childTxLogService;
    }

    public void setChildTxLogService(ChildTxLogService childTxLogService) {
        this.childTxLogService = childTxLogService;
    }
}
