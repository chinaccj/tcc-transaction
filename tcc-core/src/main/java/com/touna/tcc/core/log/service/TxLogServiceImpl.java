package com.touna.tcc.core.log.service;

import com.touna.tcc.core.log.dao.TxDao;
import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.transaction.Participant;
import com.touna.tcc.core.transaction.XaState;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by chenchaojian on 17/5/25.
 */
public class TxLogServiceImpl implements TxLogService {
    private TxDao txDao;

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void begin(String xid) {
        Tx tx = new Tx();
        tx.setXid(xid);
        tx.setStatus(XaState.BEGIN.getState());
        txDao.insert(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void trySuccess(String xid) {
        Tx tx = new Tx();
        tx.setXid(xid);
        tx.setStatus(XaState.TRY_SUCCESS.getState());

        txDao.updateState(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void finish(String xid,long beginTimeMillis) {
        Tx tx = new Tx();
        tx.setXid(xid);
        tx.setStatus(XaState.FINISH.getState());
        tx.setEndTime(new Date());
        tx.setDuration(System.currentTimeMillis() - beginTimeMillis);

        txDao.update(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void comfirmFail(String xid) {
        Tx tx = new Tx();
        tx.setXid(xid);
        tx.setStatus(XaState.CONFIRM_FAIL.getState());

        txDao.updateState(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void rollbackFail(String xid) {
        Tx tx = new Tx();
        tx.setXid(xid);
        tx.setStatus(XaState.ROLLBACK_FAIL.getState());

        txDao.updateState(tx);
    }

    public TxDao getTxDao() {
        return txDao;
    }

    public void setTxDao(TxDao txDao) {
        this.txDao = txDao;
    }
}
