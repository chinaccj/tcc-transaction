package com.touna.tcc.demo.itemcenter.service.intf;

import com.touna.tcc.core.TccContext;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface ItemService {
    void sell(String xid, String productId, Integer amount);


    void sellCommit(String xid, TccContext tccContext);


    void sellRollback(String xid, TccContext tccContext);
}