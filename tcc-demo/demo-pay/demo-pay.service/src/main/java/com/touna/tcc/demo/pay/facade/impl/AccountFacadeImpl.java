package com.touna.tcc.demo.pay.facade.impl;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.itemcenter.facade.intf.ItemFacade;
import com.touna.tcc.demo.pay.facade.intf.AccountFacade;
import com.touna.tcc.demo.pay.service.intf.AccountService;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 17/5/27.
 */
public class AccountFacadeImpl implements AccountFacade {
    @Resource
    protected AccountService accountService;

    @Resource
    protected ItemFacade itemFacade;

    @Override
    public void pay(String xid,String accountId, Double amount) {
        accountService.pay(xid,accountId,amount);
    }

    @Override
    public void payCommit(String xid,TccContext tccContext) {
        String accountId = (String)tccContext.getAttachment("accountId");
        System.out.println("test tcc Context accountId = "+accountId);
        accountService.payCommit(xid, accountId);
    }

    @Override
    public void payRollback(String xid,TccContext tccContext) {
        String accountId = (String)tccContext.getAttachment("accountId");
        accountService.payRollback(xid, accountId);
    }


    /**
     * 模拟api1 try 失败场景。 rollback成功
     * @param xid
     */
    @Override
    public void tg1Api1(String xid) {
        itemFacade.test(xid);
        throw new RuntimeException("test");
    }

    @Override
    public void tg1Api1Commit(String xid, TccContext tccContext) {
        itemFacade.testCommit(xid,tccContext);
    }

    @Override
    public void tg1Api1Rollback(String xid, TccContext tccContext) {
        itemFacade.testRollbackException(xid,tccContext);
    }

    @Override
    public void tg1Api2(String xid) {
        itemFacade.test(xid);
    }

    @Override
    public void tg1Api2Commit(String xid, TccContext tccContext) {
        itemFacade.testCommit(xid,tccContext);
    }

    @Override
    public void tg1Api2Rollback(String xid, TccContext tccContext) {
        itemFacade.testRollback(xid,tccContext);
    }

    /**
     * 测试 try2的链条上的异常。try2->try2'  2'抛异常
     * @param xid
     */
    @Override
    public void tg1Api1Plus(String xid) {
        itemFacade.test(xid);
    }

    @Override
    public void tg1Api1PlusCommit(String xid, TccContext tccContext) {
        itemFacade.tryFailCommit(xid,tccContext);
    }

    @Override
    public void tg1Api1PlusRollback(String xid, TccContext tccContext) {
        itemFacade.tryFailRollback(xid, tccContext);

    }

    @Override
    public void tg1Api2Plus(String xid) {
        itemFacade.tryFail(xid);
    }

    @Override
    public void tg1Api2PlusCommit(String xid, TccContext tccContext) {
        itemFacade.testCommit(xid,tccContext);
    }

    @Override
    public void tg1Api2PlusRollback(String xid, TccContext tccContext) {
        itemFacade.testRollback(xid, tccContext);

    }

    /**
     * 模拟api2 try 假失败场景.try2 调用privider超时，但是provider执行成功。 rollback失败
     * @param xid
     */
    @Override
    public void tg2mockApi1(String xid) {
        System.out.println("tg2mockApi1 try ...");

        itemFacade.tryTimeout(xid);
    }

    @Override
    public void tg2mockApi1Commit(String xid, TccContext tccContext) {
        itemFacade.tryTimeoutCommit(xid,tccContext);
    }

    @Override
    public void tg2mockApi1Rollback(String xid, TccContext tccContext) {
        System.out.println("tg2mockApi1Rollback ...");
        itemFacade.tryTimeoutRollback(xid, tccContext);

    }

    @Override
    public void tg2mockApi2(String xid) {
        System.out.println("tg2mockApi2 try ...");
        itemFacade.test(xid);

    }

    @Override
    public void tg2mockApi2Commit(String xid, TccContext tccContext) {
        itemFacade.testCommit(xid, tccContext);

    }

    @Override
    public void tg2mockApi2Rollback(String xid, TccContext tccContext) {
        itemFacade.testRollback(xid, tccContext);

    }

    /**
     * 模拟api1 commit 失败场景
     * @param xid
     */
    @Override
    public void tg4Api1(String xid) {
        itemFacade.test(xid);
    }

    @Override
    public void tg4Api1Commit(String xid,TccContext tccContext) {
        itemFacade.testCommit(xid,tccContext);
        throw new RuntimeException("test");

    }

    @Override
    public void tg4Api1Rollback(String xid,TccContext tccContext) {
        itemFacade.testRollback(xid, tccContext);

    }

    @Override
    public void tg4Api2(String xid) {
        itemFacade.test(xid);

    }

    @Override
    public void tg4Api2Commit(String xid, TccContext tccContext) {
        itemFacade.testCommit(xid, tccContext);

    }

    @Override
    public void tg4Api2Rollback(String xid, TccContext tccContext) {
        itemFacade.testRollback(xid, tccContext);

    }
    /**
     * test with UnSpecifications。测试try2 commit,rollback 方法没定义TccContext tccContext
     * @param xid
     */
    @Override
    public void tg5Api1(String xid) {
        itemFacade.unSpecifications(xid);
    }

    @Override
    public void tg5Api1Commit(String xid) {
        itemFacade.unSpecificationsCommit(xid);
    }

    @Override
    public void tg5Api1Rollback(String xid, TccContext tccContext) {
        itemFacade.unSpecificationsRollback(xid);
    }

    @Override
    public void tg5Api2(String xid) {
        itemFacade.test(xid);

    }

    @Override
    public void tg5Api2Commit(String xid,TccContext tccContext) {
        itemFacade.testCommit(xid,tccContext);

    }

    @Override
    public void tg5Api2Rollback(String xid,TccContext tccContext) {
        itemFacade.testRollback(xid,tccContext);

    }

    //************************************** 容错测试 ***************************************//



}
