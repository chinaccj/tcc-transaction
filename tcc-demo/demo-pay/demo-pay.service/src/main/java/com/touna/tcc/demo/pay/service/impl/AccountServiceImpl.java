package com.touna.tcc.demo.pay.service.impl;

import com.touna.tcc.demo.pay.dao.AccountDao;
import com.touna.tcc.demo.pay.dao.Operation;
import com.touna.tcc.demo.pay.dao.model.PreAccount;
import com.touna.tcc.demo.pay.service.intf.AccountService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 17/5/27.
 */

public class AccountServiceImpl implements AccountService {

    @Resource
    AccountDao accountDao;

    @Transactional(value="payTransactionManager")
    @Override
    public void pay(String xid, String accountId, Double amount) {
        PreAccount preAccount = new PreAccount();
        preAccount.setAccountId(accountId);
        preAccount.setDelta(amount);
        preAccount.setOperation(Operation.PAY.getValue());
        preAccount.setXid(xid);
        accountDao.prePay(preAccount);

    }

    @Transactional(value="payTransactionManager")
    @Override
    public void payCommit(String xid, String accountId) {
        accountDao.payCommit(xid);
    }


    @Transactional(value="payTransactionManager")
    @Override
    public void payRollback(String xid, String accountId) {
        accountDao.payRollback(xid);
    }

    @Override
    public void deposit(String xid,String uuid, String accountIdt) {

    }
}
