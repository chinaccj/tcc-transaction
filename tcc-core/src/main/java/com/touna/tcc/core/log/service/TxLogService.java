package com.touna.tcc.core.log.service;

import com.touna.tcc.core.log.dao.model.Tx;
import com.touna.tcc.core.transaction.Participant;
import com.touna.tcc.core.transaction.XaState;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/25.
 * 记录tcc事务的轨迹，协助框架confirm,rollback,redo （补偿）使用
 * 记录root节点即可。
 *
 * @TCCTransactional
 *    facadeA->facadeA'-->facadeA''
 *    facadeB->facadeB'-->facadeB''
 *  其中，facadeA,facadeB 为root节点。 confirm,roolback控制root节点即可。
 */
public interface TxLogService {
    /**
     * 开始全局事务
     * @param xid
     */
    void begin(String xid);

    void trySuccess(String xid);

    void finish(String xid);

    void comfirmFail(String xid);

    void rollbackFail(String xid);

    //获取有异常的事务
    List<Tx> getExceptionalTxs();

}
