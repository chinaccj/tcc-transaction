package com.touna.tcc.demo.itemcenter.dao;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface ItemDao {
    /**
     * 出货预处理
     */
    void preSell(ProductExt preProduct);


    void sellCommit(String xid);

    void sellRollback(String xid);


}
