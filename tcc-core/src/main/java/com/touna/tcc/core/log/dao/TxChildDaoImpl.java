package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.log.dao.model.TxChild;
import com.touna.tcc.core.transaction.XaState;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/30.
 */
public class TxChildDaoImpl implements TxChildDao {
    private SqlSession sqlSession;

    @Override
    public void insert(TxChild tx) {
        sqlSession.insert("tcc_tx_child.insert",tx);
    }

    @Override
    public int update(TxChild tx) {
        return sqlSession.update("tcc_tx_child.update",tx);
    }

    @Override
    public int updateState(TxChild tx) {
        return sqlSession.update("tcc_tx_child.updateState",tx);
    }

    @Override
    public TxChild getChildTxByCxid(String cxid) {
        return sqlSession.selectOne("tcc_tx_child.selectByCxid");
    }

    @Override
    public XaState getStateByCxid(String cxid) {
        int status = sqlSession.selectOne("tcc_tx_child.selectStatusByXid", cxid);
        return XaState.valueOf(status);
    }

    @Override
    public List<TxChild> getChildTxsByXid(String xid) {
        return sqlSession.selectList("tcc_tx_child.selectByXid", xid);
    }


    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }



}
