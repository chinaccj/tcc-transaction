package com.touna.tcc.demo.trading.service;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 17/6/2.
 */
public class ToleranceTest1 extends BaseTest {

    @Resource
    protected OrderService orderService;
    @Test
    public void testPlaceOrder(){
        orderService.placeOrder("1","1",11f);
    }
}