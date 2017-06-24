package com.touna.tcc.demo.itemcenter.dao;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenchaojian on 17/6/20.
 */
public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    private SqlSession sqlSession;

    @Override
    public ProductExt selectById(String productId) {
        return sqlSession.selectOne("product.selectById", productId);
    }

    @Override
    public ProductExt selectByIdForUpdate(String productId) {
        return sqlSession.selectOne("product.selectByIdForUpdate", productId);
    }

    @Override
    public int shipping(ProductExt product) {
        return sqlSession.update("product.shipping", product);
    }

    @Override
    public int preRemainingMinusByDelta(ProductExt productExt) {
        return  sqlSession.update("product.preShipping", productExt);
    }

    @Override
    public int preRemainingAddByDelta(ProductExt productExt) {
        return  sqlSession.update("product.preShippingRollback", productExt);
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
