package com.touna.tcc.demo.trading.service;

import com.touna.tcc.core.TCCTransactional;
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

    @Override
    @TCCTransactional()
    @Transactional
    public void placeOrder(String userId,String productId,Float price) {
        String xid = "";


        String uuid = UUID.randomUUID().toString();
        accountFacade.pay(uuid,"1",price);
    }

    @Override
    public void placeOrderWithTryException(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithCommitException(String userId, String productId, Float price) {

    }

    @Override
    public void placeOrderWithRollbackException(String userId, String productId, Float price) {

    }


//    @TCCTransactional
//    public void test() {
//        System.out.println("test ");
//    }
}
