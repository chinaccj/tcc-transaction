package com.touna.tcc.demo.trading.service;

import com.touna.tcc.core.log.dao.TxChildDao;
import com.touna.tcc.core.log.dao.TxDao;
import com.touna.tcc.core.log.dao.model.TxChild;
import com.touna.tcc.core.transaction.XaState;
import com.touna.tcc.demo.trading.base.BaseTest;
import com.touna.tcc.demo.trading.web.util.XidGenerator;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenchaojian on 17/6/2.
 * 容错测试
 */
public class ToleranceTest extends BaseTest {

    @Resource
    protected OrderService orderService;

    @Resource(name="txChildDao")
    protected TxChildDao txChildDao;

    @Resource(name="txDao")
    protected TxDao txDao;

    /**
     * 模拟try 失败的场景 ，第一个facade try失败。
     * 用例测试:
     * 场景
     *   api1->api1'
     *   api2->api2'
     *
     *  其中api1 try失败,api1 rollback成功
     *  输出:
     *  log表 ：tcc_tx 事务状态1（成功），tcc_tx_child只有一条记录，事务状态1（成功）。
     *  业务：收到异常。(try失败意味着业务失败)
     *
     */
    @Test
    public void placeOrderWithTry1Exception(){
        String xid = XidGenerator.getNewXid("OD");

        try {
            orderService.placeOrderWithTryException1(xid);
            Assert.assertTrue("try fail,means biz error. should occur ",false);
        }catch (Exception ex){
            ex.printStackTrace();

            XaState xaState = txDao.getStateByXid(xid);
            List<TxChild> txChildren =  txChildDao.getChildTxsByXid(xid);
            TxChild txChild = txChildren.get(0);

            Assert.assertTrue(xaState.getState() == 1);
            Assert.assertTrue(txChildren.size() == 1);
            Assert.assertTrue(txChild.getStatus() == 1);

        }
    }

    /**
     * 模拟try2 失败的场景
     * 用例测试:
     * 场景
     *   api1->api1'
     *   api2->api2'
     *
     *  其中api2' 失败,api1,api2 rollback成功
     *  输出:
     *  log表 ：tcc_tx 事务状态1（成功），tcc_tx_child有2条记录，事务状态都是2（成功）。
     *  业务：收到异常。(try失败意味着业务失败)
     *
     */
    @Test
    public void placeOrderWithTry2Exception(){
        String xid = XidGenerator.getNewXid("OD");

        try {
            orderService.placeOrderWithTryException2(xid);
            Assert.assertTrue("try fail,means biz error. should occur ",false);
        }catch (Exception ex){
            ex.printStackTrace();

            XaState xaState = txDao.getStateByXid(xid);
            List<TxChild> txChildren =  txChildDao.getChildTxsByXid(xid);
            TxChild txChild1 = txChildren.get(0);
            TxChild txChild2 = txChildren.get(1);


            Assert.assertTrue(xaState.getState() == 1);
            Assert.assertTrue(txChildren.size() == 2);
            Assert.assertTrue(txChild1.getStatus() == 1);
            Assert.assertTrue(txChild2.getStatus() == 1);

        }
    }



    /**
     * 模拟try 失败，其实provider 已经执行成功场景。比如provider超时。rollback失败
     */
    @Test
    public void placeOrderWithMockTryException() {
        String xid = XidGenerator.getNewXid("OD");
        orderService.placeOrderWithMockTryException(xid);
    }
    /**
     * 模拟commit 失败的场景 第一个facade commit 失败。commit失败，算业务成功
     */
    @Test
    public void placeOrderWithCommitException1(){
        String xid = XidGenerator.getNewXid("OD");
        orderService.placeOrderWithCommitException1(xid);
    }


    /**
     * 不遵守tcc规范，比如xid类型不对,xid 位置不对，commit/rollback参数不对等
     */
    @Test
    public void placeOrderWithTCCUnSpecifications(){
        String xid = XidGenerator.getNewXid("OD");
        orderService.placeOrderWithTCCUnSpecifications(xid);
    }
}