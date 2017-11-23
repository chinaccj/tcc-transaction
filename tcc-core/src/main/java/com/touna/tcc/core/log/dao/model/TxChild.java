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
     * executed order, 1 prior 2
     */
    private int sequence;
;
    /**
     * 0 :begin,1:finish,2:confirm fail,3:rollback fail
     * 参考 com.touna.tcc.core.transaction.XaState
     */
    private int status;
    private Date beginTime;
    private Date endTime;


    private String clsName;

    private String commitMethod;

    private String rollbackMethod;

    /**
     * 参数类型列表 类型直接使用','分离
     */
    private byte paramesTypes[];
    /**
     * 参数列表序列号
     */
    private byte paramesValues[];

    /**
     * dubbo version
     */
    private String dubboVersion;

    /**
     * 冗余字段
     */
    private Class[] paramsTypesArray;
    /**
     * 冗余字段
     */
    private Object[] paramValuesArray;

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


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public byte[] getParamesTypes() {
        return paramesTypes;
    }

    public void setParamesTypes(byte[] paramesTypes) {
        this.paramesTypes = paramesTypes;
    }

    public byte[] getParamesValues() {
        return paramesValues;
    }

    public void setParamesValues(byte[] paramesValues) {
        this.paramesValues = paramesValues;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Class[] getParamsTypesArray() {
        return paramsTypesArray;
    }

    public void setParamsTypesArray(Class[] paramsTypesArray) {
        this.paramsTypesArray = paramsTypesArray;
    }

    public Object[] getParamValuesArray() {
        return paramValuesArray;
    }

    public void setParamValuesArray(Object[] paramValuesArray) {
        this.paramValuesArray = paramValuesArray;
    }

    public String getDubboVersion() {
        return dubboVersion;
    }

    public void setDubboVersion(String dubboVersion) {
        this.dubboVersion = dubboVersion;
    }
}
