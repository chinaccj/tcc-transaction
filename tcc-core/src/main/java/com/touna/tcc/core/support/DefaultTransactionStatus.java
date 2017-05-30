package com.touna.tcc.core.support;

import com.touna.tcc.core.transaction.Transaction;
import com.touna.tcc.core.transaction.TransactionStatus;

/**
 * Created by chenchaojian on 17/5/10.
 */
public class DefaultTransactionStatus implements TransactionStatus {
    private final boolean newTransaction;
    private final Transaction tx;

    public DefaultTransactionStatus(boolean newTransaction,Transaction tx) {
        this.newTransaction = newTransaction;
        this.tx = tx;
    }


//    private boolean isTransactionComplete;

    @Override
    public boolean isNewTransaction() {
        return this.newTransaction;
    }

//    @Override
//    public boolean isTransactionComplete() {
//        return this.isTransactionComplete;
//    }
}
