package com.touna.tcc.demo.trading.service;

import com.touna.tcc.demo.trading.base.BaseTest;
import com.touna.tcc.demo.trading.web.util.XidGenerator;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenchaojian on 17/5/15.
 * 正常流程测试。
 * 测试用例：
 * commit 和rollback的顺序必须要和try 一致
 */
public class OrderServiceTest extends BaseTest {

    @Resource
    protected OrderService orderService;
    @Test
    public void testPlaceOrder(){

        String xid = XidGenerator.getNewXid("OD");
        orderService.placeOrder(xid,"1","1",11.00);
    }
}
