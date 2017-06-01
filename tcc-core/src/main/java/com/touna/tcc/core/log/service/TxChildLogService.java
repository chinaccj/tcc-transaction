package com.touna.tcc.core.log.service;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/29.
 */
public interface TxChildLogService {
    /**
     *
     * @param xid
     * @param cXid
     * @param clsName
     * @param commitMethod
     * @param rollbackMethod
     * @param paramsTypes
     * @param paramValues  为了序列化和反序列化方便，paramValues存储的是 List<Object[]>通过kryo 序列化的结果
     */
    void begin(String xid, String cXid, String clsName, String commitMethod,
                          String rollbackMethod, Class[] paramsTypes, Object []paramValues);

    void finish(String xid,String cXid,long beginTimeMillis);

    void confirmFail(String xid,String cXid);

    void rollbackFail(String xid,String cXid);


}
