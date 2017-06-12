package com.touna.tcc.demo.trading.service;

import com.touna.tcc.demo.trading.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 17/6/2.
 * 容错测试
 */
public class ToleranceTest extends BaseTest {

    @Resource
    protected OrderService orderService;
    @Test
    public void testPlaceOrder(){
        orderService.placeOrder("","1","1",11.00);
    }
}