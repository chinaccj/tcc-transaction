package com.touna.tcc.core.transaction;

import org.springframework.util.Assert;

/**
 * Created by chenchaojian on 17/6/24.
 */
public abstract class TransactionSynchronizationUtils {
    static final  String SPLIT = "_";
    public static String invokeMetadataKey(String clsName,String tryMethodName){
        Assert.notNull(clsName, "Value must not be null");
        Assert.notNull(tryMethodName, "Value must not be null");

        return clsName+SPLIT+tryMethodName;
    }


}
