package com.touna.tcc.dubbo.filter;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.touna.tcc.core.TwoPhaseBusinessAction;
import com.touna.tcc.core.transaction.*;

/**
 * Created by chenchaojian on 17/5/25.
 * tcc chain logger
 */
@Activate(group = { Constants.CONSUMER })
public class TCCTransactionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TCCTransactionFilter.class);

    private AbstractTransactionManager tccTransaction;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Result result = invoker.invoke(invocation);

        /**
         * both success try and fail try need to log. both success try and fail try need to rollback while exception
         * occur(timeout or network may cause try fail,but provider may success executed).
         */
        try {
            Transaction txObject = TransactionSynchronizationManager.getResource();
            if (txObject != null) {//tcc transaction chain flag
                String xid = txObject.getXid();
                Class cls = invoker.getInterface();
                String methodName = invocation.getMethodName();
                Class[] paramsTypes = invocation.getParameterTypes();
                Method method = cls.getDeclaredMethod(methodName, paramsTypes);
                TwoPhaseBusinessAction annotation = method
                    .getAnnotation(TwoPhaseBusinessAction.class);

                // dubbo filter only log try data. commit and rollback data logged in TransactionManager
                if (annotation != null) {
                    String commitMethod = annotation.commitMethod();
                    String rollbackMethod = annotation.rollbackMethod();

                    //TCCInvokeMetadata not in threadlocal
                    if (TransactionSynchronizationManager
                        .getInvokeMetadata(cls.getName()) == null) {//try阶段
                        Object[] arguments = invocation.getArguments();

                        String cXid = XidGenerator.newCXid();

                        TCCInvokeMetadata invokeMetadata = new TCCInvokeMetadata();
                        invokeMetadata.setClsName(cls.getName());
                        invokeMetadata.setCommitMethod(commitMethod);
                        invokeMetadata.setRollbackMethod(rollbackMethod);
                        invokeMetadata.setParamsTypes(paramsTypes);
                        invokeMetadata.setParamValues(arguments);
                        invokeMetadata.setcXid(cXid);

                        int sequence = TransactionSynchronizationManager.setTCCInvokeMetadata(invokeMetadata);
                        tccTransaction.getTxChildLogService().trySuccess(sequence,xid, cXid, cls.getName(),
                            commitMethod, rollbackMethod, paramsTypes, arguments);

                    }

                }
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);

            RpcResult rpcResult = new RpcResult();
            rpcResult.setException(ex);

            return rpcResult;
        }

        return result;
    }

    public AbstractTransactionManager getTccTransaction() {
        return tccTransaction;
    }

    public void setTccTransaction(AbstractTransactionManager tccTransaction) {
        this.tccTransaction = tccTransaction;
    }
}
