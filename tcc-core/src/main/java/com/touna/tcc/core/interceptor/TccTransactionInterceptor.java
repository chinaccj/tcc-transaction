package com.touna.tcc.core.interceptor;

/**
 * Created by chenchaojian on 17/5/10.
 */

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import com.touna.tcc.core.TccIllegalOperationException;
import com.touna.tcc.core.TccFrameworkException;
import com.touna.tcc.core.TccTransactional;
import com.touna.tcc.core.log.service.TxLogService;
import com.touna.tcc.core.transaction.TransactionManager;
import com.touna.tcc.core.transaction.TransactionStatus;
import com.touna.tcc.core.transaction.TransactionSynchronizationManager;

@Aspect
public class TccTransactionInterceptor implements BeanFactoryAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TccTransactionInterceptor.class);

    private BeanFactory beanFactory;

    private String             transactionManagerBeanName;// = "tccTransaction";
    private TransactionManager transactionManager = null;

    private TxLogService txLogService;

    @Around("@annotation(com.touna.tcc.core.TccTransactional)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Object retVal = null;
        TransactionManager tm = determineTransactionManager();

        //fetch xid from invoke parameter
        String xid = fetchXid(pjp);

        TransactionInfo txInfo = createTransactionIfNecessary(xid, tm);

        try {

            //try
            retVal = pjp.proceed();

            //try success log
            logTrySuccess(xid);

            //commit 有异常,业务算成功
            try {
                txInfo.getTransactionManager().commit(txInfo);
            }catch (Throwable ex){
                logger.warn(ex.getMessage(), ex);
                return retVal;
            }

            //
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);

            //if try face exception,rollback
            txInfo.getTransactionManager().rollback(txInfo);
            throw ex;
        } finally {
            //clear thread local
            cleanupTransactionInfo(txInfo);
        }

        return retVal;
    }

    protected String fetchXid(ProceedingJoinPoint pjp) {
        try {
            String methodName = pjp.getSignature().getName();
            Class<?> classTarget = pjp.getTarget().getClass();
            Class<?>[] parameterTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
            Method method = classTarget.getMethod(methodName, parameterTypes);
            TccTransactional tccAnnotation = method.getAnnotation(TccTransactional.class);
            int index = tccAnnotation.xidIndex();
            Object[] args = pjp.getArgs();
            Object obj = args[index];

            return (String) obj;

        } catch (ClassCastException ex) {
            throw new TccIllegalOperationException(
                "illegal use of tcc framework. method with @TCCTransactional must have a param with xid(String type)");

        } catch (Throwable ex) {
            throw new TccFrameworkException(ex.getMessage(), ex);
        }
    }


    protected void logCommitFail(String xid) {
        txLogService.comfirmFail(xid);
    }

    protected void logTrySuccess(String xid) {
        txLogService.trySuccess(xid);

    }

    protected void cleanupTransactionInfo(TransactionInfo txInfo) {
        if (txInfo != null) {
            if (txInfo.getTransactionStatus().isNewTransaction()) {//else 非最外层方法，不做处理
                TransactionSynchronizationManager.clear();
            }
        }
    }

    protected TransactionInfo createTransactionIfNecessary(String xid, TransactionManager tm) {
        TransactionStatus status = tm.getTransaction(xid);
        return new TransactionInfo(status, tm);
    }

    private TransactionManager determineTransactionManager() {
        if (transactionManager != null) {
            return this.transactionManager;
        } else if (this.transactionManagerBeanName != null) {
            return this.beanFactory.getBean(this.transactionManagerBeanName,
                TransactionManager.class);
        } else if (this.beanFactory instanceof ListableBeanFactory) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(
                ((ListableBeanFactory) this.beanFactory), TransactionManager.class);
        } else {
            throw new IllegalStateException(
                "Cannot retrieve PlatformTransactionManager beans from non-listable BeanFactory: "
                                            + this.beanFactory);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public String getTransactionManagerBeanName() {
        return transactionManagerBeanName;
    }

    public void setTransactionManagerBeanName(String transactionManagerBeanName) {
        this.transactionManagerBeanName = transactionManagerBeanName;
    }

    public TxLogService getTxLogService() {
        return txLogService;
    }

    public void setTxLogService(TxLogService txLogService) {
        this.txLogService = txLogService;
    }
}
