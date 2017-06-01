package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;

/**
 * Created by chenchaojian on 17/5/30.
 */
public interface TxDao {
    void insert(Tx tx);

    int update(Tx tx);

    int updateState(Tx tx);

}
