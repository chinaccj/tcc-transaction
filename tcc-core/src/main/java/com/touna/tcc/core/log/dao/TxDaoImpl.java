package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.transaction.XaState;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/30.
 */
public class TxDaoImpl implements TxDao{
    private SqlSession sqlSession;

    @Override
    public void insert(Tx tx) {
        sqlSession.insert("tcc_tx.insert",tx);
    }

    @Override
    public int update(Tx tx) {
        return sqlSession.update("tcc_tx.update",tx);
    }

    @Override
    public int updateState(Tx tx) {
        return sqlSession.update("tcc_tx.updateState",tx);
    }

    @Override
    public Tx getTxByXid(String xid) {
        return sqlSession.selectOne("tcc_tx.selectByXid", xid);
    }

    @Override
    public XaState getStateByXid(String xid) {
        int status = sqlSession.selectOne("tcc_tx.selectStatusByXid", xid);
        return XaState.valueOf(status);
    }

    @Override
    public List<Tx> selectExceptionalXids() {
        return sqlSession.selectList("tcc_tx.selectExceptionalXids");
    }


    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
