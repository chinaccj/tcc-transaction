package com.touna.tcc.core.log.service;

import com.touna.tcc.core.transaction.Participant;
import com.touna.tcc.core.transaction.XaState;

/**
 * Created by chenchaojian on 17/5/25.
 */
public class TxLogServiceImpl implements TxLogService {
    @Override
    public void begin(String xid) {
        System.out.println("begin global xid="+xid);
    }

    @Override
    public void finish(String xid) {
        System.out.println("finish global xid="+xid);

    }

    @Override
    public void comfirmFail(String xid) {
        System.out.println("comfirmFail global xid="+xid);

    }

    @Override
    public void rollbackFail(String xid) {
        System.out.println("rollbackFail global xid="+xid);

    }


}
