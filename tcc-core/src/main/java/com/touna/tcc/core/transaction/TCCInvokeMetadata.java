package com.touna.tcc.core.transaction;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/29.
 * tcc 调用的facade摘要信息
 */
public class TCCInvokeMetadata {
    /**
     * xid
     */
    private String xid;
    /**
     * child xid
     */
    private String cXid;
    /**
     * 接口名字
     */
    private String clsName;
    /**
     * 接口参数
     */
    private String commitMethod;

    private String rollbackMethod;

    private Class[] paramsTypes;

    private Object[] paramValues;

    private int index;

    public TCCInvokeMetadata() {
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

    public Class[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }

    public String getcXid() {
        return cXid;
    }

    public void setcXid(String cXid) {
        this.cXid = cXid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }
}
