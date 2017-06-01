package com.touna.tcc.core.log.service;

import com.touna.tcc.core.transaction.Participant;
import com.touna.tcc.core.transaction.XaState;

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

    void finish(String xid,long beginTimeMillis);

    void comfirmFail(String xid);

    void rollbackFail(String xid);


}
