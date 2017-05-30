package com.touna.tcc.core.transaction;

/**
 * Created by chenchaojian on 17/5/10.
 *  用来解决@TCCTransaction 注解嵌套的问题。常规的commit 解决思路通过stack实现，本方法参考spring
 * 的TransactionInterceptor实现，很巧妙
 */
public interface TransactionStatus {


    /**
     * Return whether the present transaction is new (else participating
     * in an existing transaction, or potentially not running in an
     * actual transaction in the first place).
     *
     *
     */
    public boolean isNewTransaction();


}
