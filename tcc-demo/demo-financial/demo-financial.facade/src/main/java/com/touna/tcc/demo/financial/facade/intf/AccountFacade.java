package com.touna.tcc.demo.financial.facade.intf;

import com.touna.tcc.core.TwoPhaseBusinessAction;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface AccountFacade {



    /**
     * 支付
     * @param accountId
     * @param amount
     */
    @TwoPhaseBusinessAction(commitMethod="payCommit",rollbackMethod="payRollback")
    void pay(String uuid,String accountId,Float amount);


    void payCommit(String uuid,String accountId,Float amount);


    void payRollback(String uuid,String accountId,Float amount);



    /**
     * 充值
     * @param accountId
     * @param amount
     */
    void deposit(String uuid,String accountId,Float amount);
}
