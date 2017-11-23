package com.touna.tcc.core.log.dao.model;

import java.util.Date;

/**
 * Created by chenchaojian on 17/5/30.
 */
public class Tx {
    /**
     * 事务id
     */
    private String xid;
    /**
     * 0 :begin,1:finish,2:confirm fail,3:rollback fail
     * 参考 com.touna.tcc.core.transaction.XaState
     */
    private int status;
    private Date beginTime;
    private Date endTime;


    public Tx() {
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
