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
    public void placeOrder(String xid,String userId,String productId,Double price) {


        String uuid = UUID.randomUUID().toString();
        accountFacade.pay(xid, "1", price);

        itemFacade.sell(xid,"1",1);
        //db operate
    }

    @Override
    public void placeOrderWithTryException1(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithTryException2(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithTryException3(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithMockTryException(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithCommitException1(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithCommitException2(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithCommitException3(String userId, String productId, Float price) {

    }


    @Override
    public void placeOrderWithRollbackException(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithTCCAndSpringTx(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithTCCUnSpecifications(String userId, String productId, Float price) {

    }


//    @TCCTransactional
//    public void test() {
//        System.out.println("test ");
//    }
}
