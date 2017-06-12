package com.touna.tcc.demo.pay.dao;

import com.touna.tcc.demo.pay.dao.model.PreAccount;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface AccountDao {

    void prePay(PreAccount preAccount);

    void payCommit(String xid);

    void payRollback(String xid);

}
