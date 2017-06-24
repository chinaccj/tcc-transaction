package com.touna.tcc.demo.pay.dao;

import com.touna.tcc.demo.pay.dao.model.Account;
import com.touna.tcc.demo.pay.dao.model.PreAccount;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface AccountDao {

    void insert(PreAccount preAccount);

    Account selectAccountById(String accountId);

    /**
     * 排他锁
     * @param accountId
     * @return
     */
    Account selectAccountByIdForUpdate(String accountId);


    PreAccount selectPreAccountByXid(String xid);

    /**
     * x lock with for update
     * @param xid
     * @return
     */
    PreAccount selectPreAccountByXidForUpdate(String xid);



    int balanceMinusByDelta(Account account);

    int preBalanceMinusByDelta(Account account);

    int preBalanceAddByDelta(Account account);


    int deletePreAccountByXid(String xid);
}
