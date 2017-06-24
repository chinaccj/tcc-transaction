package com.touna.tcc.core.transaction;

import com.touna.tcc.core.TccCommitException;
import com.touna.tcc.core.TccRollbackException;
import com.touna.tcc.core.interceptor.TransactionInfo;
import com.touna.tcc.core.log.service.TxChildLogService;
import com.touna.tcc.core.log.service.TxLogService;
import com.touna.tcc.core.support.DefaultTransactionStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by chenchaojian on 17/5/21.
 */
abstract public class AbstractTransactionManager implements TransactionManager, BeanFactoryAware, InitializingBean {
    /**
     * 记录服务
     */
    protected TxLogService txLogService;
    protected TxChildLogService txChildLogService;
    private int serializeMaxCapacity;

    protected BeanFactory beanFactory;

    public AbstractTransactionManager(int serializeMaxCapacity){
        this.serializeMaxCapacity = serializeMaxCapacity;
    }
    public final TransactionStatus getTransaction(String xid) {
        Transaction tx = TransactionSynchronizationManager.getResource();
        if (tx != null) {
            return new DefaultTransactionStatus(false,tx);
        }
        else
        {
            tx = new Transaction(xid);

            //begin transaction
            begin(tx);

            TransactionSynchronizationManager.bindResource(tx);
            return new DefaultTransactionStatus(true,tx);
        }
    }



    protected void begin(Transaction tx) {

//        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));

        txLogService.begin(tx.getXid());

    }



    /**
     * 为tcc 协议预留
     * 提交由第三方SOA 框架实现
     */
    @Override
    public  void commit(TransactionInfo txInfo)throws TccCommitException{
        if(txInfo.getTransactionStatus().isNewTransaction()){//else 非最外层方法，不做处理

            doCommit(txInfo);
        }
    }

    public abstract void doCommit(TransactionInfo txInfo)throws TccCommitException;

    public abstract void  doRollback(TransactionInfo txInfo)throws TccRollbackException;

    /**
     *  回滚由第三方SOA 框架实现
     */
    @Override
    public void rollback(TransactionInfo txInfo)throws TccRollbackException{
        if(txInfo.getTransactionStatus().isNewTransaction()){//else 非最外层方法，不做处理
            doRollback(txInfo);
        }
    }


    public TxLogService getTxLogService() {
        return txLogService;
    }

    public void setTxLogService(TxLogService txLogService) {
        this.txLogService = txLogService;
    }

    public TxChildLogService getTxChildLogService() {
        return txChildLogService;
    }

    public void setTxChildLogService(TxChildLogService txChildLogService) {
        this.txChildLogService = txChildLogService;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public int getSerializeMaxCapacity() {
        return serializeMaxCapacity;
    }

    public void setSerializeMaxCapacity(int serializeMaxCapacity) {
        this.serializeMaxCapacity = serializeMaxCapacity;
    }
}
