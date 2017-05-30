package com.touna.tcc.demo.pay.service.impl;

import com.touna.tcc.demo.pay.service.intf.AccountService;

/**
 * Created by chenchaojian on 17/5/27.
 */

public class AccountServiceImpl implements AccountService {
    @Override
    public void pay(String uuid,String accountId, Float amount) {
        System.out.println("pay accountId ="+accountId+" amount="+amount);
    }

    @Override
    public void payCommit(String uuid, String accountId, Float amount) {
        System.out.println("payCommit accountId ="+accountId+" amount="+amount);
    }

    @Override
    public void payRollback(String uuid, String accountId, Float amount) {
        System.out.println("payRollback accountId ="+accountId+" amount="+amount);

    }

    @Override
    public void deposit(String uuid,String accountId, Float amount) {
        System.out.println("deposit accountId ="+accountId+" amount="+amount);

    }
}
