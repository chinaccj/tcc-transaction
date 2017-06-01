package com.touna.tcc.dubbo.filter;

import com.alibaba.dubbo.rpc.Result;
import com.touna.tcc.core.TwoPhaseBusinessAction;
import com.touna.tcc.core.log.service.TxChildLogService;
import com.touna.tcc.core.transaction.*;
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

    private AbstractTransactionManager tccTransaction;

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

                        tccTransaction.getTxChildLogService().begin(xid, cXid, cls.getName(), commitMethod, rollbackMethod, paramsTypes, arguments);

                    }

                }
            }
        }
        catch (Throwable ex){
            logger.error(ex.getMessage(),ex);

            RpcResult rpcResult = new RpcResult();
            rpcResult.setException(ex);

            return rpcResult;
        }


        Result result = invoker.invoke(invocation);

        return result;
    }

    public AbstractTransactionManager getTccTransaction() {
        return tccTransaction;
    }

    public void setTccTransaction(AbstractTransactionManager tccTransaction) {
        this.tccTransaction = tccTransaction;
    }
}
