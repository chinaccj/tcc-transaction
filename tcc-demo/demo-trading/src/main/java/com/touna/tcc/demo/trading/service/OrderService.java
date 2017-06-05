package com.touna.tcc.demo.trading.service;

/**
 * Created by chenchaojian on 17/5/15.
 */
public interface OrderService {
    void placeOrder(String userId,String productId,Float price);


    /**
     * 模拟try 失败的场景
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTryException(String userId,String productId,Float price);

    /**
     * 模拟commit 失败的场景
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithCommitException(String userId,String productId,Float price);

    /**
     * 模拟rollback 失败场景
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithRollbackException(String userId,String productId,Float price);





}
