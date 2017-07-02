package com.touna.tcc.admin.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.demo.itemcenter.facade.intf.ItemFacade;
import com.touna.tcc.dubbo.DubboConsumerHacker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.touna.tcc.demo.trading.base.BaseTest;

/**
 * Created by chenchaojian on 17/5/15.
 * 正常流程测试。
 * 测试用例：
 * commit 和rollback的顺序必须要和try 一致
 */
public class TccAdminTest extends BaseTest {

    @Resource
    protected DubboConsumerHacker dubboConsumerHacker;

    @Test
    public void testItem(){
        //    void sell(String xid,String productId,Integer amount);

        Class[] paramsTypes = new Class[2];
        paramsTypes[0] = String.class;
        paramsTypes[1] = TccContext.class;

        Object[] arguments = new Object[2];
        arguments[0] = "OD201707021517468611";

        TccContext tccContext = new TccContext();
        tccContext.setAttachment("source","tcc_admin");
        arguments[1] = tccContext;


        try {
            dubboConsumerHacker.invoke(ItemFacade.class,paramsTypes,arguments,null,"sellCommit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
