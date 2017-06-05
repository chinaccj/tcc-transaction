package com.touna.tcc.core.transaction;

import com.touna.tcc.core.NamedThreadLocal;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by chenchaojian on 17/5/21.
 */
public class TransactionSynchronizationManager {
    static final NamedThreadLocal<Transaction> transactionHolder = new NamedThreadLocal<Transaction>("tcc local transaction");

    /**
     * map should be ordered,first in ,first executed.
     */
    static final NamedThreadLocal<Map<String,TCCInvokeMetadata>> invokeMetadataHolder = new NamedThreadLocal<Map<String,TCCInvokeMetadata>>("tcc local invocation metadata");

    static final Object lock = new Object();

    public static Transaction getResource(){
        return transactionHolder.get();
    }

    public static TCCInvokeMetadata getInvokeMetadata(String clsName){
        Map<String,TCCInvokeMetadata> metadataMap = invokeMetadataHolder.get();
        if(metadataMap == null){
            return null;
        }

        return metadataMap.get(clsName);
    }

    public static List<TCCInvokeMetadata> getInvokeMetadataList(){
        Map<String,TCCInvokeMetadata> metadataMap = invokeMetadataHolder.get();
        if(metadataMap == null) return null;

        return new ArrayList<TCCInvokeMetadata>(metadataMap.values());

    }

    /**
     * thread unsafe,tcc not support concurrent call dubbo facade(api).
     * @param invokeMetadata
     * @return
     */
    public static int setTCCInvokeMetadata(TCCInvokeMetadata invokeMetadata){
        Map<String,TCCInvokeMetadata> metadataMap = invokeMetadataHolder.get();
        if(metadataMap == null){
            synchronized (lock) {
                if(metadataMap == null) {
                    //order map
                    metadataMap = new LinkedHashMap<String, TCCInvokeMetadata>();
                }
            }
        }

        metadataMap.put(invokeMetadata.getClsName(), invokeMetadata);
        int mapSize = metadataMap.size();
        invokeMetadataHolder.set(metadataMap);

        return mapSize;

    }

    public static void bindResource(Transaction txObject){
        Assert.notNull(txObject, "Value must not be null");
        transactionHolder.set(txObject);
    }

    public static void clear(){
        transactionHolder.remove();
        invokeMetadataHolder.remove();
    }
}
