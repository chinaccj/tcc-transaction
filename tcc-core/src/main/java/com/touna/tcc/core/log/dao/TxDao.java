package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.transaction.XaState;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/30.
 */
public interface TxDao {
    void insert(Tx tx);

    int update(Tx tx);

    int updateState(Tx tx);

    Tx getTxByXid(String xid);

    XaState getStateByXid(String xid);

    List<Tx> selectExceptionalXids();
}
