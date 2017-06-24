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
        itemService.sellCommit(xid, tccContext);
    }

    @Override
    public void sellRollback(String xid, TccContext tccContext) {
        itemService.sellRollback(xid, tccContext);
    }

    @Override
    public void test(String xid) {
        System.out.println("ItemFacadeImpl try " + xid);
    }

    @Override
    public void testCommit(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl commit "+xid);

    }

    @Override
    public void testRollback(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl rollback "+xid);

    }

    @Override
    public void testRollbackException(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl rollback with exception "+xid);
        throw new RuntimeException("test");

    }

    @Override
    public void tryFail(String xid) {
        throw new RuntimeException("test");
    }

    @Override
    public void tryFailCommit(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl commit "+xid);
    }

    @Override
    public void tryFailRollback(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl rollback "+xid);
    }

    @Override
    public void tryTimeout(String xid) {
        try {
            Thread.sleep(1000*10);
            System.out.println("tryTimeout ...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tryTimeoutCommit(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl commit "+xid);

    }

    @Override
    public void tryTimeoutRollback(String xid, TccContext tccContext) {
        System.out.println("ItemFacadeImpl rollback "+xid);
    }

    @Override
    public void unSpecifications(String xid) {
        System.out.println("ItemFacadeImpl try "+xid);

    }

    @Override
    public void unSpecificationsCommit(String xid) {
        System.out.println("ItemFacadeImpl commit "+xid);

    }

    @Override
    public void unSpecificationsRollback(String xid) {
        System.out.println("ItemFacadeImpl rollback "+xid);

    }
}
