package com.touna.tcc.demo.trading.service;

/**
 * Created by chenchaojian on 17/5/15.
 */
public interface OrderService {

    void placeOrder(String xid,String accountId,String productId,Double price);


    /**
     * 模拟try 失败的场景 ，第一个facade try失败
     */
    void placeOrderWithTryException1(String xid);
    /**
     * 模拟try 失败的场景 ，第二个facade try失败
     */
    void placeOrderWithTryException2(String xid);


    /**
     * 模拟try 失败，其实provider 已经执行成功场景。比如provider超时
     */
    void placeOrderWithMockTryException(String xid);

    /**
     * 模拟commit 失败的场景 第一个facade commit 失败。commit失败，算业务成功
     */
    void placeOrderWithCommitException1(String xid);


    /**
     * 不遵守tcc规范，比如xid类型不对,xid 位置不对，commit/rollback参数不对等
     */
    void placeOrderWithTCCUnSpecifications(String xid);


}
