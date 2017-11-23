package com.touna.tcc.demo.trading.service;

import com.touna.tcc.core.TccTransactional;
import com.touna.tcc.demo.itemcenter.facade.intf.ItemFacade;
import com.touna.tcc.demo.pay.facade.intf.AccountFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by chenchaojian on 17/5/15.
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    AccountFacade accountFacade;

    @Resource
    ItemFacade itemFacade;

    @Override
    @TccTransactional(xidIndex = 0)
    @Transactional()
    public void placeOrder(String xid,String accountId,String productId,Double price) {


        String uuid = UUID.randomUUID().toString();
        accountFacade.pay(xid, accountId, price);

        itemFacade.sell(xid, productId, 1);
        //db operate
    }

    @TccTransactional(xidIndex = 0)
    @Override
    public void placeOrderWithTryException1(String xid) {
        accountFacade.tg1Api1(xid);
        accountFacade.tg1Api2(xid);
    }

    @TccTransactional(xidIndex = 0)
    @Override
    public void placeOrderWithTryException2(String xid) {
        accountFacade.tg1Api1Plus(xid);
        accountFacade.tg1Api2Plus(xid);
    }


    @TccTransactional(xidIndex = 0)
    @Override
    public void placeOrderWithMockTryException(String xid) {
        accountFacade.tg2mockApi1(xid);
        accountFacade.tg2mockApi2(xid);

    }

    @TccTransactional(xidIndex = 0)
    @Override
    public String placeOrderWithCommitException1(String xid) {
        accountFacade.tg4Api1(xid);//commit throw exception
        accountFacade.tg4Api2(xid);

        return "OK";
    }

    @TccTransactional(xidIndex = 0)
    @Override
    public String placeOrderWithCommitException2(String xid) {
        accountFacade.tg4Api2(xid);
        accountFacade.tg4Api1(xid);//commit throw exception

        return "OK";
    }


    @TccTransactional(xidIndex = 0)
    @Override
    public void placeOrderWithTCCUnSpecifications(String xid) {
        accountFacade.tg5Api1(xid);
        accountFacade.tg5Api2(xid);
    }

    @Override
    public void testDummyTry(String xid) {

    }

}
