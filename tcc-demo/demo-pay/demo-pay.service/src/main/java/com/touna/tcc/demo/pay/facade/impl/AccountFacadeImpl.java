package com.touna.tcc.demo.pay.facade.impl;

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
    public void pay(String uuid,String accountId, Float amount) {
        accountService.pay(uuid,accountId,amount);
//        if(true){
//            throw new RuntimeException("xx");
//        }
    }

    @Override
    public void payCommit(String uuid, String accountId, Float amount) {
        accountService.payCommit(uuid,accountId,amount);
    }

    @Override
    public void payRollback(String uuid, String accountId, Float amount) {
        accountService.payRollback(uuid,accountId,amount);
    }

    @Override
    public void deposit(String uuid,String accountId, Float amount) {
        accountService.deposit(uuid,accountId,amount);
    }
}
