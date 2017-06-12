package com.touna.tcc.core.transaction;

import com.touna.tcc.core.NamedThreadLocal;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenchaojian on 17/5/21.
 */
public class TransactionSynchronizationManager {
    static final NamedThreadLocal<Transaction> transactionHolder = new NamedThreadLocal<Transaction>("tcc local transaction");

    /**
     * map should be ordered,first in ,first executed.
     */
    static final NamedThreadLocal<Map<String,TCCInvokeMetadata>> invokeMetadataHolder = new NamedThreadLocal<Map<String,TCCInvokeMetadata>>("tcc local invocation metadata");

    static final NamedThreadLocal<AtomicInteger> childTxIndexHolder = new NamedThreadLocal<AtomicInteger>("tcc local child tx holder");


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
    public static void setTCCInvokeMetadata(TCCInvokeMetadata invokeMetadata){
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
        invokeMetadataHolder.set(metadataMap);
    }

    public static int newChildTxIndex(){
        AtomicInteger holder = childTxIndexHolder.get();
        if(holder == null) holder = new AtomicInteger();

        int index = holder.incrementAndGet();

        childTxIndexHolder.set(holder);
        return index;
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
