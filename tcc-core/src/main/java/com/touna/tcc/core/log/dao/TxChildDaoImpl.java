package com.touna.tcc.core.log.dao;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.log.dao.model.TxChild;
import org.apache.ibatis.session.SqlSession;

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


    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
