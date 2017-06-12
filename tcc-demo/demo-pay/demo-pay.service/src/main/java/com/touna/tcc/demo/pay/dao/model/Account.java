package com.touna.tcc.demo.pay.dao.model;

import java.util.Date;

/**
 * Created by chenchaojian on 17/6/11.
 */
public class Account {
    public Account() {
    }

    private String accountId;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 本次操作金额
     */
    private Double delta;
    private Date createTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }
}
