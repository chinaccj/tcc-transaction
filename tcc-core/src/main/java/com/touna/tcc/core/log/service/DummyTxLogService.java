package com.touna.tcc.core.log.service;

import com.touna.tcc.core.log.dao.model.Tx;

import java.util.List;

/**
 * Created by chenchaojian on 17/6/1.
 * 空的事务记录类。用途：
 * 1 框架性能测试（排查数据库）
 * 2 有些业务场景不需要记录调用背景入数据库
 *
 */
public class DummyTxLogService implements TxLogService {
    @Override
    public void begin(String xid) {

    }

    @Override
    public void trySuccess(String xid) {

    }

    @Override
    public void finish(String xid) {

    }

    @Override
    public void comfirmFail(String xid) {

    }

    @Override
    public void rollbackFail(String xid) {

    }

    @Override
    public List<Tx> getExceptionalTxs() {
        return null;
    }
}
