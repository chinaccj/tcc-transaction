package com.touna.tcc.demo.pay.dao;

import com.touna.tcc.demo.pay.dao.model.Account;
import com.touna.tcc.demo.pay.dao.model.PreAccount;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface AccountDao {

    void insertWithoutToAccountId(PreAccount preAccount);

    Account selectAccountById(String accountId);

    PreAccount selectPreAccountByXid(String xid);


    int updateAccount(Account account);

    int deletePreAccountByXid(String xid);
}
