package com.touna.tcc.core.transaction;

import com.touna.tcc.core.interceptor.TransactionInfo;

/**
 * Created by chenchaojian on 17/5/10.
 */
public interface TransactionManager {

    /**
     * 提交事务
     */
    void commit(TransactionStatus status);

    /**
     * 回滚事务
     */
    void rollback(TransactionStatus status);


    TransactionStatus getTransaction();


}
