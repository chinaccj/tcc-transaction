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

    Boolean payCommit(String xid,TccContext tccContext);

    Boolean payRollback(String xid,TccContext tccContext);



    /**
     *  容错测试
     *  TG  为test group 简称
     */
    /**
     * 模拟api1 try 失败场景。 rollback成功
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tg1Api1Commit",rollbackMethod="tg1Api1Rollback")
    void tg1Api1(String xid);
    void tg1Api1Commit(String xid,TccContext tccContext);
    void tg1Api1Rollback(String xid,TccContext tccContext);


    @TwoPhaseBusinessAction(commitMethod="tg1Api2Commit",rollbackMethod="tg1Api2Rollback")
    void tg1Api2(String xid);
    void tg1Api2Commit(String xid,TccContext tccContext);
    void tg1Api2Rollback(String xid,TccContext tccContext);


    /**
     * 测试 try1的链条上的异常。try1->try1'  2'抛异常
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tg1Api1PlusCommit",rollbackMethod="tg1Api1PlusRollback")
    void tg1Api1Plus(String xid);
    void tg1Api1PlusCommit(String xid,TccContext tccContext);
    void tg1Api1PlusRollback(String xid,TccContext tccContext);


    @TwoPhaseBusinessAction(commitMethod="tg1Api2PlusCommit",rollbackMethod="tg1Api2PlusRollback")
    void tg1Api2Plus(String xid);
    void tg1Api2PlusCommit(String xid,TccContext tccContext);
    void tg1Api2PlusRollback(String xid,TccContext tccContext);


    /**
     * 模拟api2 try 假失败场景.try2 调用privider超时，但是provider执行成功。 rollback失败
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tg2mockApi1Commit",rollbackMethod="tg2mockApi1Rollback")
    void tg2mockApi1(String xid);
    void tg2mockApi1Commit(String xid,TccContext tccContext);
    void tg2mockApi1Rollback(String xid,TccContext tccContext);


    @TwoPhaseBusinessAction(commitMethod="tg2mockApi2Commit",rollbackMethod="tg2mockApi2Rollback")
    void tg2mockApi2(String xid);
    void tg2mockApi2Commit(String xid,TccContext tccContext);
    void tg2mockApi2Rollback(String xid,TccContext tccContext);




    /**
     * 模拟api1 commit 失败场景
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tg4Api1Commit",rollbackMethod="tg4Api1Rollback")
    void tg4Api1(String xid);
    void tg4Api1Commit(String xid,TccContext tccContext);
    void tg4Api1Rollback(String xid,TccContext tccContext);


    @TwoPhaseBusinessAction(commitMethod="tg4Api2Commit",rollbackMethod="tg4Api2Rollback")
    void tg4Api2(String xid);
    void tg4Api2Commit(String xid,TccContext tccContext);
    void tg4Api2Rollback(String xid,TccContext tccContext);




    /**
     * test with UnSpecifications。测试try2 commit,rollback 方法没定义TccContext tccContext
     * @param xid
     */
    @TwoPhaseBusinessAction(commitMethod="tg5Api1Commit",rollbackMethod="tg5Api1Rollback")
    void tg5Api1(String xid);
    void tg5Api1Commit(String xid);
    void tg5Api1Rollback(String xid,TccContext tccContext);

    @TwoPhaseBusinessAction(commitMethod="tg5Api2Commit",rollbackMethod="tg5Api2Rollback")
    void tg5Api2(String xid);
    void tg5Api2Commit(String xid,TccContext tccContext);
    void tg5Api2Rollback(String xid,TccContext tccContext);



    /**
     * 支付预处理
     * @param accountId
     * @param amount
     */
    @TwoPhaseBusinessAction(commitMethod="testDummyTryCommit",rollbackMethod="testDummyTryRollback")
    void testDummyTry(String xid,@Attachment(key="accountId") String accountId,Double amount);

    void testDummyTryCommit(String xid,TccContext tccContext);

    void testDummyTryRollback(String xid,TccContext tccContext);
}
