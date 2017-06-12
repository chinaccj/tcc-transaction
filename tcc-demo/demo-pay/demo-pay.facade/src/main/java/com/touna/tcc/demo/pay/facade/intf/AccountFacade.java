package com.touna.tcc.demo.pay.facade.intf;

import com.touna.tcc.core.Attachment;
import com.touna.tcc.core.TccContext;
import com.touna.tcc.core.TwoPhaseBusinessAction;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface AccountFacade {



    /**
     * 支付预处理
     * @param accountId
     * @param amount
     */
    @TwoPhaseBusinessAction(commitMethod="payCommit",rollbackMethod="payRollback")
    void pay(String xid,@Attachment(key="accountId") String accountId,Double amount);


    void payCommit(String xid,TccContext tccContext);


    void payRollback(String xid,TccContext tccContext);



    /**
     * 充值
     * @param accountId
     * @param amount
     */
    void deposit(String xid,String uuid,String accountId,Float amount);
}
