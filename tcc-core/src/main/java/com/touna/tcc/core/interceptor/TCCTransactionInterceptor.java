package com.touna.tcc.core.interceptor;

/**
 * Created by chenchaojian on 17/5/10.
 */

import com.touna.tcc.core.TwoPhaseBusinessAction;
import com.touna.tcc.core.log.service.TxLogService;
import com.touna.tcc.core.transaction.Transaction;
import com.touna.tcc.core.transaction.TransactionManager;
import com.touna.tcc.core.transaction.TransactionStatus;
import com.touna.tcc.core.transaction.TransactionSynchronizationManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import java.lang.annotation.Annotation;
import java.util.Map;

@Aspect
public class TCCTransactionInterceptor implements BeanFactoryAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TCCTransactionInterceptor.class);

    private BeanFactory beanFactory;

    private  String transactionManagerBeanName;// = "tccTransaction";
    private TransactionManager transactionManager = null;

    private TxLogService txLogService;

    @Around("@annotation(com.touna.tcc.core.TCCTransactional)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object retVal = null;
        TransactionManager tm = determineTransactionManager();

        TransactionInfo txInfo = createTransactionIfNecessary(tm);

        try {

            retVal = point.proceed();

            //try success log
            logTrySuccess();

            //commit
            txInfo.getTransactionManager().commit(txInfo);

            //
        } catch (Throwable ex) {
            logger.error(ex.toString(),ex);

            //回滚
            txInfo.getTransactionManager().rollback(txInfo);
            throw ex;
        }finally {
            //clear thread local
            cleanupTransactionInfo(txInfo);
        }

        return retVal;
    }

    protected void logTrySuccess(){
        Transaction tx = TransactionSynchronizationManager.getResource();
        String xid = tx.getXid();

        txLogService.trySuccess(xid);

    }
    protected void cleanupTransactionInfo(TransactionInfo txInfo){
        if(txInfo != null) {
            if (txInfo.getTransactionStatus().isNewTransaction()) {//else 非最外层方法，不做处理
                TransactionSynchronizationManager.clear();
            }
        }
    }

    protected TransactionInfo createTransactionIfNecessary(TransactionManager tm){
        TransactionStatus status = tm.getTransaction();
        return new TransactionInfo(status,tm);
    }

    private TransactionManager determineTransactionManager(){
        if(transactionManager != null){
            return this.transactionManager;
        }
        else if (this.transactionManagerBeanName != null) {
            return this.beanFactory.getBean(this.transactionManagerBeanName, TransactionManager.class);
        }
        else if (this.beanFactory instanceof ListableBeanFactory) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(((ListableBeanFactory) this.beanFactory), TransactionManager.class);
        }
        else {
            throw new IllegalStateException(
                    "Cannot retrieve PlatformTransactionManager beans from non-listable BeanFactory: " + this.beanFactory);
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





