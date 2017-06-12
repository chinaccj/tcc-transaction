package com.touna.tcc.demo.itemcenter.facade.intf;

import com.touna.tcc.core.TccContext;
import com.touna.tcc.core.TwoPhaseBusinessAction;

/**
 * Created by chenchaojian on 17/5/27.
 */
public interface ItemFacade {

    /**
     *
     * @param xid
     * @param productId
     * @param amount
     */
    @TwoPhaseBusinessAction(commitMethod="sellCommit",rollbackMethod="sellRollback")
    void sell(String xid,String productId,Integer amount);


    void sellCommit(String xid,TccContext tccContext);


    void sellRollback(String xid,TccContext tccContext);

}
