package com.touna.tcc.demo.trading.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.touna.tcc.demo.trading.base.BaseTest;
import com.touna.tcc.demo.trading.web.util.XidGenerator;

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

        orderService.placeOrder(xid, "1", "1", 1.00);
    }

}
