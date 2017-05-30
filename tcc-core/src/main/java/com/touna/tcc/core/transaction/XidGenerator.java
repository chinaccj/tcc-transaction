package com.touna.tcc.core.transaction;

import java.util.UUID;

/**
 * Created by chenchaojian on 17/5/24.
 */
public class XidGenerator {
    //未来可扩展：xid生成有业务含义，通过在TCCTransactional 传递业务模块标识
    public static String newXid(){
        return UUID.randomUUID().toString();
    }

    /**
     * 生产子事务的 事务id
     * @return
     */
    public static String newCXid(){
        return UUID.randomUUID().toString();
    }

}
