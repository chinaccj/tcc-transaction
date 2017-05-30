package com.touna.tcc.core.interceptor;

import com.touna.tcc.core.transaction.TransactionManager;
import com.touna.tcc.core.transaction.TransactionStatus;

/**
 * Created by chenchaojian on 17/5/10.
 */
public class TransactionInfo {
    private TransactionStatus transactionStatus;

    private TransactionManager transactionManager;

    public TransactionInfo(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }



    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public TransactionInfo(TransactionStatus transactionStatus, TransactionManager transactionManager) {
        this.transactionStatus = transactionStatus;
        this.transactionManager = transactionManager;
    }
}
