package com.touna.tcc.demo.itemcenter.facade.impl;

import javax.annotation.Resource;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.itemcenter.facade.intf.ItemFacade;
import com.touna.tcc.demo.itemcenter.service.intf.ItemService;

/**
 * Created by chenchaojian on 17/5/27.
 */
public class ItemFacadeImpl implements ItemFacade {
    @Resource
    protected ItemService itemService;


    @Override
    public void sell(String xid, String productId, Integer amount) {
        itemService.sell(xid,productId,amount);
    }

    @Override
    public void sellCommit(String xid, TccContext tccContext) {
        itemService.sellCommit(xid,tccContext);
    }

    @Override
    public void sellRollback(String xid, TccContext tccContext) {
        itemService.sellRollback(xid,tccContext);
    }
}
