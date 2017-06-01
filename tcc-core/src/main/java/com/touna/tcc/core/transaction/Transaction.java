package com.touna.tcc.core.transaction;

import java.util.Date;

/**
 * Created by chenchaojian on 17/5/21.
 */
public class Transaction {
    /**
     * 事务id
     */
    private String xid;

    /**
     * 事务开始时间
     */
    private Date beginTime;
    /**
     * 事务结束时间
     */
    private Date endTime;

    /**
     * 事务状态
     */
    private XaState xaState;

    private long beginTimeMillis;

    public Transaction(String xid) {
        this.xid = xid;
        beginTimeMillis = System.currentTimeMillis();
    }

    public String getXid() {
        return xid;
    }

    public long getBeginTimeMillis() {
        return beginTimeMillis;
    }
}
