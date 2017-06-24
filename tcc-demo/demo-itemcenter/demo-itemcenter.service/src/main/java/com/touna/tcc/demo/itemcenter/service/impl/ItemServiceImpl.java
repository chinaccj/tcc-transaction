package com.touna.tcc.demo.itemcenter.service.impl;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.itemcenter.dao.PreProductDao;
import com.touna.tcc.demo.itemcenter.dao.ProductDao;
import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;
import com.touna.tcc.demo.itemcenter.service.intf.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenchaojian on 17/5/27.
 */

public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private PreProductDao preProductDao;
    private ProductDao productDao;

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sell(String xid, String productId, Integer amount) {

        ProductExt preProduct = new ProductExt();
        preProduct.setXid(xid);
        preProduct.setProductId(productId);
        preProduct.setDelta(amount);

        try {//幂等性检查，xid必须为唯一性索引

            ProductExt product = productDao.selectById(productId);
            Integer preRemaining = product.getPreRemaining();
            if (preRemaining - amount < 0) {//库存不足校验
                throw new IllegalArgumentException("remaning product is less than delta");
            }

            preProductDao.insert(preProduct);

            productDao.preRemainingMinusByDelta(preProduct);
        } catch (DuplicateKeyException ex) {//该预处理已经提交过.忽略这种异常
            logger.warn("pre product had done before " + ex.getMessage(), ex);
        }
    }

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sellCommit(String xid, TccContext tccContext) {
        //并发处理
        ProductExt product = preProductDao.selectByXidForUpdate(xid);
        //幂等性
        if (product == null) {
            logger.warn("sellCommit xid " + xid + " has commit before. invalid operation");
            return;
        }

        int numAffected = productDao.shipping(product);
        //TODO should handle numAffected not equal 1 exception
        if(numAffected != 1){
            throw new RuntimeException("sell commit fail,not update product collectly");
        }

        int numDeleted = preProductDao.deleteByXid(xid);
        //TODO should handle numDeleted not equal 1 exception
        if(numDeleted != 1){
            throw new RuntimeException("sell commit fail,not delete pre_product collectly");
        }    }

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sellRollback(String xid, TccContext tccContext) {
        ProductExt productExt = preProductDao.selectByXid(xid);
        int rowAffected = preProductDao.deleteByXid(xid);
        if(rowAffected == 1){//幂等性
            productDao.preRemainingAddByDelta(productExt);
        }

    }

    public PreProductDao getPreProductDao() {
        return preProductDao;
    }

    public void setPreProductDao(PreProductDao preProductDao) {
        this.preProductDao = preProductDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
