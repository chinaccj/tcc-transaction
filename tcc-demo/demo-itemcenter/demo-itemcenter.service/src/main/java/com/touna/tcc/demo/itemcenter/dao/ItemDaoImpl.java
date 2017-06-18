package com.touna.tcc.demo.itemcenter.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;

/**
 * Created by chenchaojian on 17/6/9.
 */
public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = LoggerFactory.getLogger(ItemDaoImpl.class);

    private SqlSession sqlSession;

    @Override
    public void preSell(ProductExt preProduct) {
        /**
         * product.shipping
        
        
         pre_product
         insert
         deleteByXid
         */
        try {//幂等性检查，xid必须为唯一性索引

            ProductExt product = sqlSession.selectOne("product.selectById", preProduct);
            Integer remaining = product.getRemaining();
            if (remaining - preProduct.getDelta() < 0) {//库存不足校验
                throw new IllegalArgumentException("remaning product is less than delta");
            }

            sqlSession.insert("pre_product.insert", preProduct);
        } catch (DuplicateKeyException ex) {//该预处理已经提交过.忽略这种异常
            logger.warn("pre product had done before " + ex.getMessage(), ex);
        }

    }

    @Override
    public void sellCommit(String xid) {

        ProductExt product = sqlSession.selectOne("pre_product.selectByXid", xid);
        if (product == null) {
            logger.warn("sellCommit xid " + xid + " has commit before. invalid operation");
            return;
        }

        int numAffected = sqlSession.update("product.shipping", product);
        //TODO should handle numAffected not equal 1 exception
        if(numAffected != 1){
            throw new RuntimeException("sell commit fail,not update product collectly");
        }

        int numDeleted = sqlSession.delete("pre_product.deleteByXid", xid);
        //TODO should handle numDeleted not equal 1 exception
        if(numDeleted != 1){
            throw new RuntimeException("sell commit fail,not delete pre_product collectly");
        }


    }

    @Override
    public void sellRollback(String xid) {

        sqlSession.delete("pre_product.deleteByXid", xid);

    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
