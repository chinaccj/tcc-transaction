package com.touna.tcc.demo.trading.service;

/**
 * Created by chenchaojian on 17/5/15.
 */
public interface OrderService {
    void placeOrder(String userId,String productId,Float price);
}
