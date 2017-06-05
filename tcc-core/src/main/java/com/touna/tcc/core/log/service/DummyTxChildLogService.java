package com.touna.tcc.core.log.service;

/**
 * Created by chenchaojian on 17/6/1.
 * 空的子事务记录类。用途：
 * 1 框架性能测试（排查数据库）
 * 2 有些业务场景不需要记录调用背景入数据库
 *
 */
public class DummyTxChildLogService implements TxChildLogService{
    @Override
    public void trySuccess(String xid, String cXid, String clsName, String commitMethod, String rollbackMethod, Class[] paramsTypes, Object[] paramValues) {

    }

    @Override
    public void finish(String xid, String cXid, long beginTimeMillis) {

    }

    @Override
    public void confirmFail(String xid, String cXid) {

    }

    @Override
    public void rollbackFail(String xid, String cXid) {

    }
}
