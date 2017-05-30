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
    void pay(String uuid,String accountId,Float amount);

    void payCommit(String uuid, String accountId, Float amount);


    void payRollback(String uuid, String accountId, Float amount);


    /**
     * 充值
     * @param accountId
     * @param amount
     */
    void deposit(String uuid,String accountId,Float amount);
}
