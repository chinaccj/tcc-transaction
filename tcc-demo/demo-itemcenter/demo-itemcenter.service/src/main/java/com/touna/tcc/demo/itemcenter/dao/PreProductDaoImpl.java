package com.touna.tcc.demo.itemcenter.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;

/**
 * Created by chenchaojian on 17/6/9.
 */
public class PreProductDaoImpl implements PreProductDao {
    private static final Logger logger = LoggerFactory.getLogger(PreProductDaoImpl.class);

    private SqlSession sqlSession;


    @Override
    public void insert(ProductExt preProduct) {
        sqlSession.insert("pre_product.insert", preProduct);
    }

    @Override
    public int deleteByXid(String xid) {
        return  sqlSession.delete("pre_product.deleteByXid", xid);
    }

    @Override
    public ProductExt selectByXid(String xid) {
        return  sqlSession.selectOne("pre_product.selectByXid", xid);
    }

    @Override
    public ProductExt selectByXidForUpdate(String xid) {
        return sqlSession.selectOne("pre_product.selectByXidForUpdate", xid);
    }


    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
