package com.touna.tcc.demo.trading.service;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.touna.tcc.demo.trading.base.BaseTest;
import com.touna.tcc.demo.trading.web.util.XidGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.type.MethodMetadata;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenchaojian on 17/5/27.
 * 性能测试：测试吞吐量，fgc，内存泄漏，多线程环境测试.
 * 多线程测试：
 * 测试事务是否会窜掉。得模拟真实trading例子。模拟一个账户不停得充值（多线程），同时不停的消费（多线程）。最终看数据是否对。
 */
public class PerformanceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

    AtomicInteger index = new AtomicInteger();

    @Resource
    protected OrderService orderService;

    private static ExecutorService exec = new ThreadPoolExecutor(100, 100, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000),
            new ThreadPoolExecutor.CallerRunsPolicy());



    @Test
    public void testPlaceOrder(){
        for(int i=0;i<10000;i++) {
            exec.execute(new Event());
            exec.execute(new Event1());

        }

        try {
            Thread.sleep(1000*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private  class Event implements Runnable{

        @Override
        public void run() {
            try{
                String xid = XidGenerator.getNewXid("OD");
                if(logger.isInfoEnabled()){
                    logger.info(xid);
                }

                orderService.placeOrder(xid, "1", "1", 1.00);

                System.out.println(index.incrementAndGet());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private  class Event1 implements Runnable{

        @Override
        public void run() {
            try{
                String xid = XidGenerator.getNewXid("OD");
                if(logger.isInfoEnabled()){
                    logger.info(xid);
                }

                orderService.placeOrder(xid, "1", "1", 3.00);

                System.out.println(index.incrementAndGet());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
