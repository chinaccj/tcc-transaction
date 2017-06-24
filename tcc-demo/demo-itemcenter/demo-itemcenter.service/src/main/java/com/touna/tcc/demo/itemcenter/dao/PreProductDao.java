package com.touna.tcc.demo.itemcenter.dao;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface PreProductDao {

    void insert(ProductExt preProduct);

    int deleteByXid(String xid);

    ProductExt selectByXid(String xid);

    /**
     * x lock
     * @param xid
     * @return
     */
    ProductExt selectByXidForUpdate(String xid);


}
