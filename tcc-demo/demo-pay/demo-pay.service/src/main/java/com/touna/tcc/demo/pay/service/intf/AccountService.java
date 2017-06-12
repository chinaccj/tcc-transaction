package com.touna.tcc.demo.pay.service.intf;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface AccountService {
    /**
     * 支付
     * @param accountId
     * @param amount
     */
    void pay(String xid,String accountId,Double amount);

    void payCommit(String xid, String accountId);


    void payRollback(String xid, String accountId);


    /**
     *
     * @param uuid
     * @param accountId
     */
    void deposit(String xid,String uuid,String accountId);
}
