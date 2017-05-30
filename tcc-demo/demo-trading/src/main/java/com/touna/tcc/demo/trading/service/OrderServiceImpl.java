package com.touna.tcc.demo.trading.service;

import com.touna.tcc.core.TCCTransactional;
import com.touna.tcc.demo.pay.facade.intf.AccountFacade;
import org.springframework.stereotype.Service;

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
    @TCCTransactional
    public void placeOrder(String userId,String productId,Float price) {
        String uuid = UUID.randomUUID().toString();
        accountFacade.pay(uuid,"1",price);
    }


//    @TCCTransactional
//    public void test() {
//        System.out.println("test ");
//    }
}
