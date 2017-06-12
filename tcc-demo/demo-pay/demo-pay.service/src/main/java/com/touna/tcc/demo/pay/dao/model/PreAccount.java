package com.touna.tcc.demo.pay.dao.model;

import java.util.Date;

/**
 * Created by chenchaojian on 17/6/11.
 */
public class PreAccount {
    private String accountId;
    private String toAccountId;
    /**
     * '操作类型 0 消费  1充值 2转账'
     */
    private Integer operation;
    private String xid;
    private Double delta;
    private Date createTime;

    public PreAccount() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
