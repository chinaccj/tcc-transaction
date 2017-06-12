package com.touna.tcc.demo.pay.dao;

import org.springframework.transaction.TransactionDefinition;

/**
 * Created by chenchaojian on 17/6/11.
 */
public enum Operation {
    PAY(0,"支付"),
    DESIPOTE(1,"充值"),
    TRANSFER(2,"转账");

    //操作类型 0 消费  1充值 2转账
    private final int value;
    private final String desc;

    Operation(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
