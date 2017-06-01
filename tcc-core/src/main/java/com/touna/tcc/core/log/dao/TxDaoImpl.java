package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import org.apache.ibatis.session.SqlSession;

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


    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
