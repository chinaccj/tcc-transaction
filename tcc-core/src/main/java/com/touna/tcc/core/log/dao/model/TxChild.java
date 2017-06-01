package com.touna.tcc.core.log.dao.model;

import java.util.Date;

/**
 * Created by chenchaojian on 17/5/30.
 */
public class TxChild {
    public TxChild() {
    }

    /**
     * 事务id
     */
    private String xid;

    /**
     * child xid
     */
    private String cXid;

    /**
     * 0 :begin,1:finish,2:confirm fail,3:rollback fail
     * 参考 com.touna.tcc.core.transaction.XaState
     */
    private int status;
    private Date begainTime;
    private Date endTime;
    private long duration;


    private String clsName;

    private String commitMethod;

    private String rollbackMethod;

    /**
     * 参数类型列表 类型直接使用','分离
     */
    private String paramesTypes;
    /**
     * 参数列表序列号
     */
    private String paramesValues;

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getcXid() {
        return cXid;
    }

    public void setcXid(String cXid) {
        this.cXid = cXid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBegainTime() {
        return begainTime;
    }

    public void setBegainTime(Date begainTime) {
        this.begainTime = begainTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public String getCommitMethod() {
        return commitMethod;
    }

    public void setCommitMethod(String commitMethod) {
        this.commitMethod = commitMethod;
    }

    public String getRollbackMethod() {
        return rollbackMethod;
    }

    public void setRollbackMethod(String rollbackMethod) {
        this.rollbackMethod = rollbackMethod;
    }

    public String getParamesTypes() {
        return paramesTypes;
    }

    public void setParamesTypes(String paramesTypes) {
        this.paramesTypes = paramesTypes;
    }

    public String getParamesValues() {
        return paramesValues;
    }

    public void setParamesValues(String paramesValues) {
        this.paramesValues = paramesValues;
    }
}
