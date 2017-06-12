package com.touna.tcc.demo.trading.service;

/**
 * Created by chenchaojian on 17/5/15.
 */
public interface OrderService {

    void placeOrder(String xid,String userId,String productId,Double price);


    /**
     * 模拟try 失败的场景 ，第一个facade try失败
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTryException1(String userId,String productId,Float price);
    /**
     * 模拟try 失败的场景 ，第二个facade try失败
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTryException2(String userId,String productId,Float price);
    /**
     * 模拟try 失败的场景 ，第三个facade try失败
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTryException3(String userId,String productId,Float price);


    /**
     * 模拟try 失败，其实provider 已经执行成功场景。比如provider超时
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithMockTryException(String userId,String productId,Float price);

    /**
     * 模拟commit 失败的场景 第一个facade commit 失败。commit失败，算业务成功
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithCommitException1(String userId,String productId,Float price);

    /**
     * 模拟commit 失败的场景 第二个facade commit 失败。commit失败，算业务成功
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithCommitException2(String userId,String productId,Float price);

    /**
     * 模拟commit 失败的场景 第三个facade commit 失败。 commit失败，算业务成功
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithCommitException3(String userId,String productId,Float price);
    /**
     * 模拟rollback 失败场景。 rollback失败，业务就算失败
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithRollbackException(String userId,String productId,Float price);


    /**
     * 模拟 @TCCTransactional 嵌套 @TCCTransactional 再嵌套@Transactional场景
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTCCAndSpringTx(String userId,String productId,Float price);


    /**
     * 不遵守tcc规范，比如xid类型不对,xid 位置不对，commit/rollback参数不对等
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrderWithTCCUnSpecifications(String userId,String productId,Float price);


}
