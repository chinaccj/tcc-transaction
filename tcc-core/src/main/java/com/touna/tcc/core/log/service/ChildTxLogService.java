package com.touna.tcc.core.log.service;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/29.
 */
public interface ChildTxLogService {
    void begin(String xid, String cXid, String clsName, String commitMethod,
                          String rollbackMethod, Class[] paramsTypes, Object []paramValues);

    void finish(String xid,String cXid);

    void rollback(String xid,String cXid);

    void confirmFail(String xid,String cXid);

    void rollbackFail(String xid,String cXid);


}
