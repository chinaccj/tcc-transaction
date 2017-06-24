package com.touna.tcc.demo.itemcenter.dao;

import com.touna.tcc.demo.itemcenter.dao.model.ProductExt;

/**
 * Created by chenchaojian on 17/6/9.
 */
public interface ProductDao {

    ProductExt selectById(String productId);

    /**
     * 排他锁
     * @param productId
     * @return
     */
    ProductExt selectByIdForUpdate(String productId);


    int shipping(ProductExt product);


    int preRemainingMinusByDelta(ProductExt productExt);

    int preRemainingAddByDelta(ProductExt productExt);


}
