package com.touna.tcc.demo.trading.dao;

/**
 * Created by chenchaojian on 17/6/8.
 */
public interface OrderDao {
    /**
     * 为了简化测试，只是更新商品库存数量
     * @param xid
     * @param userId
     * @param productId
     * @param price
     */
    void placeOrder(String xid,String userId,String productId,Float price);

}
