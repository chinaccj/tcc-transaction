package com.touna.tcc.demo.pay.facade.impl;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.pay.facade.intf.AccountFacade;
import com.touna.tcc.demo.pay.service.intf.AccountService;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 17/5/27.
 */
public class AccountFacadeImpl implements AccountFacade {
    @Resource
    protected AccountService accountService;

    @Override
    public void pay(String xid,String accountId, Double amount) {
        accountService.pay(xid,accountId,amount);
//        if(true){
//            throw new RuntimeException("xx");
//        }
    }

    @Override
    public void payCommit(String xid,TccContext tccContext) {
        String accountId = (String)tccContext.getAttachment("accountId");
        System.out.println("test tcc Context accountId = "+accountId);
        accountService.payCommit(xid,accountId);
    }

    @Override
    public void payRollback(String xid,TccContext tccContext) {
        String accountId = (String)tccContext.getAttachment("accountId");
        accountService.payRollback(xid,accountId);
    }

    @Override
    public void deposit(String xid,String uuid,String accountId, Float amount) {
        accountService.deposit(xid,uuid,accountId);
    }
}
