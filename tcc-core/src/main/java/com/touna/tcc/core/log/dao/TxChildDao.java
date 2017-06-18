package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.log.dao.model.TxChild;
import com.touna.tcc.core.transaction.XaState;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/30.
 */
public interface TxChildDao {
    public void insert(TxChild tx) ;

    public int update(TxChild tx) ;

    public int updateState(TxChild tx) ;

    public TxChild getChildTxByCxid(String cxid);

    public XaState getStateByCxid(String cxid);

    public List<TxChild> getChildTxsByXid(String xid);


}
