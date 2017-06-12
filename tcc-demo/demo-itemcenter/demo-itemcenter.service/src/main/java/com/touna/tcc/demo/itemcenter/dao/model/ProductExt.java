package com.touna.tcc.demo.itemcenter.dao.model;

import java.util.Date;

/**
 * Created by chenchaojian on 17/6/9.
 * 兼容 product 和  pre_product_shipping
 */
public class ProductExt {
    private String productId;
    private Date createTime;
    private Integer remaining;
    private String xid;
    /**
     * 出货／进货量
     */
    private Integer delta;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }
}
