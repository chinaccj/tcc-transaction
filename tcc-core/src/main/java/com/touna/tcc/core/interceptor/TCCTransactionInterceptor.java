package com.touna.tcc.core.interceptor;

/**
 * Created by chenchaojian on 17/5/10.
 */

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

@Aspect
public class TCCTransactionInterceptor implements BeanFactoryAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TCCTransactionInterceptor.class);

    private BeanFactory beanFactory;

    private  String transactionManagerBeanName;// = "cc_transaction";
    private TransactionManager transactionManager = null;

    @Around("@annotation(com.touna.tcc.core.TCCTransactional)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object retVal = null;
        TransactionManager tm = determineTransactionManager();

        TransactionInfo txInfo = createTransactionIfNecessary(tm);

        try {

            retVal = point.proceed();

            //if not exception throw,excute commit
            txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());

            //
        } catch (Throwable ex) {
            logger.error(ex.toString(),ex);

            //回滚
            txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
            throw ex;
        }finally {
            //clear thread local
            TransactionSynchronizationManager.clear();
        }

        return retVal;
    }

    protected TransactionInfo createTransactionIfNecessary(TransactionManager tm
    ){
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
}





