package com.touna.tcc.demo.itemcenter.service.impl;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.itemcenter.dao.ItemDao;
import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;
import com.touna.tcc.demo.itemcenter.service.intf.ItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenchaojian on 17/5/27.
 */

public class ItemServiceImpl implements ItemService {
    private ItemDao itemDao;

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sell(String xid, String productId, Integer amount) {
//        System.out.println("sell "+xid+" productId="+productId+" amount="+amount);

        ProductExt preProduct = new ProductExt();
        preProduct.setXid(xid);
        preProduct.setProductId(productId);
        preProduct.setDelta(amount);

        itemDao.preSell(preProduct);

    }

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sellCommit(String xid, TccContext tccContext) {
        itemDao.sellCommit(xid);
    }

    @Transactional(value="itemcenterTransactionManager")
    @Override
    public void sellRollback(String xid, TccContext tccContext) {
        itemDao.sellRollback(xid);
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
