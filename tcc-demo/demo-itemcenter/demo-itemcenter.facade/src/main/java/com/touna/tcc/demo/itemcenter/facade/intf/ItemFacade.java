package com.touna.tcc.demo.itemcenter.facade.intf;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.core.TwoPhaseBusinessAction;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface ItemFacade {

    /**
     *
     * @param xid
     * @param productId
     * @param amount
     */
    @TwoPhaseBusinessAction(commitMethod="sellCommit",rollbackMethod="sellRollback")
    void sell(String xid,String productId,Integer amount);


    void sellCommit(String xid,TccContext tccContext);


    void testRollbackException(String xid, TccContext tccContext) ;


    void sellRollback(String xid,TccContext tccContext);

    /**               容错测试              **/
    /**
     *  容错测试
     *  TG  为test group 简称
     */
    /**
     * 正常调用
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="testCommit",rollbackMethod="testRollback")
    void test(String xid);
    void testCommit(String xid,TccContext tccContext);
    void testRollback(String xid,TccContext tccContext);


    /**
     * 模拟api1 try 失败场景。 rollback成功
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tryFailCommit",rollbackMethod="tryFailRollback")
    void tryFail(String xid);
    void tryFailCommit(String xid,TccContext tccContext);
    void tryFailRollback(String xid,TccContext tccContext);


    /**
     * try 超时。
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tryTimeoutCommit",rollbackMethod="tryTimeoutRollback")
    void tryTimeout(String xid);
    void tryTimeoutCommit(String xid,TccContext tccContext);
    void tryTimeoutRollback(String xid,TccContext tccContext);



    /**
     * UnSpecifications。
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="unSpecificationsCommit",rollbackMethod="unSpecificationsRollback")
    void unSpecifications(String xid);
    void unSpecificationsCommit(String xid);
    void unSpecificationsRollback(String xid);


}
