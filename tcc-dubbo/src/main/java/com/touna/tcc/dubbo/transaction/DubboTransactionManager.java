package com.touna.tcc.dubbo.transaction;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.touna.tcc.core.TccCommitException;
import com.touna.tcc.core.TccRollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.touna.tcc.core.interceptor.TransactionInfo;
import com.touna.tcc.core.transaction.*;

/**
 * Created by chenchaojian on 17/5/14.
 */
public class DubboTransactionManager extends AbstractTransactionManager
                                     implements TransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(DubboTransactionManager.class);

    protected ConcurrentHashMap<String, Object> proxyCache = new ConcurrentHashMap<String, Object>();

    public DubboTransactionManager(int serializeMaxCapacity) {
        super(serializeMaxCapacity);
    }

    @Override
    public void doRollback(TransactionInfo txInfo) throws TccRollbackException {

        List<TCCInvokeMetadata> listInvokeMetadata = TransactionSynchronizationManager
            .getInvokeMetadataList();

        if (listInvokeMetadata == null) {
            //出现这种情况的一种可能是加了@TCCTransactional 注解，但是方法里面没有调用dubbo的接口
            logger.warn("can not get listInvokeMetadata from TransactionSynchronizationManager. ");
            return;
        }

        Transaction tx = TransactionSynchronizationManager.getResource();
        String xid = tx.getXid();
        /*
          rollback will be executed sequentially,if some node fail,follower node will not execute.
          failed node and not executed node will be redo by compensatory job
         */
        try {
            for (TCCInvokeMetadata invokeMetadata : listInvokeMetadata) {
                try {
                    String clsName = invokeMetadata.getClsName();
                    String rollbackMethod = invokeMetadata.getRollbackMethod();
                    Class[] parameterTypes = invokeMetadata.getParamsTypes();
                    Object[] paramValues = invokeMetadata.getParamValues();

                    Object proxy = proxyCache.get(clsName);
                    if (proxy == null) {
                        proxy = loadProxy(clsName);
                    }

                    Method method = proxy.getClass().getMethod(rollbackMethod, parameterTypes);
                    method.invoke(proxy, paramValues);
                    txChildLogService.finish(tx.getXid(), invokeMetadata.getcXid(),
                            tx.getBeginTimeMillis());
                }catch (Throwable ex){
                    txChildLogService.rollbackFail(tx.getXid(), invokeMetadata.getcXid());
                    throw ex;
                }

            }

            txLogService.finish(xid, tx.getBeginTimeMillis());

        } catch (Throwable e) {
            txLogService.rollbackFail(xid);
            throw new TccRollbackException(e);
        }

    }

    @Override
    public void doCommit(TransactionInfo txInfo) throws TccCommitException {

        List<TCCInvokeMetadata> listInvokeMetadata = TransactionSynchronizationManager
            .getInvokeMetadataList();

        if (listInvokeMetadata == null) {
            //出现这种情况的一种可能是加了@TCCTransactional 注解，但是方法里面没有调用dubbo的接口
            logger.warn("can not get listInvokeMetadata from TransactionSynchronizationManager. ");
            return;
        }

        Transaction tx = TransactionSynchronizationManager.getResource();
        String xid = tx.getXid();

        //commit will be executed sequentially,if some node fail,follower node will not execute.
        //failed node and not executed node will be redo by compensatory job
        try {
            for (TCCInvokeMetadata invokeMetadata : listInvokeMetadata) {
                try {
                    String clsName = invokeMetadata.getClsName();
                    String commitMethod = invokeMetadata.getCommitMethod();
                    Class[] parameterTypes = invokeMetadata.getParamsTypes();
                    Object[] paramValues = invokeMetadata.getParamValues();

                    Object proxy = proxyCache.get(clsName);
                    if (proxy == null) {
                        proxy = loadProxy(clsName);
                    }

                    Method method = proxy.getClass().getMethod(commitMethod, parameterTypes);
                    method.invoke(proxy, paramValues);

                    txChildLogService.finish(xid, invokeMetadata.getcXid(), tx.getBeginTimeMillis());
                }catch (Throwable ex){
                    txChildLogService.confirmFail(xid, invokeMetadata.getcXid());
                    throw ex;
                }
            }

            txLogService.finish(xid, tx.getBeginTimeMillis());

        } catch (Throwable ex) {
            txLogService.comfirmFail(xid);
            throw new TccCommitException(ex);
        }

    }

    private Object loadProxy(String clsName) {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory aac = (DefaultListableBeanFactory) beanFactory;
            Class cls = null;
            try {
                cls = Class.forName(clsName);
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(
                    "ClassNotFoundException for load class definition clsName=" + clsName);
            }

            Map<String, Class> map = aac.getBeansOfType(cls);
            if (map == null) {
                throw new NullPointerException(
                    "can not get bean from beanFactory with class " + clsName);
            }

            if (map.size() != 1) {
                //按tcc目前的实现，一个facade 接口只有一个实现。否则实力化接口的时候不知道调用哪个实现
                throw new UnsupportedOperationException(
                    "can not get bean from beanFactory with class " + clsName);
            }

            Set<String> set = map.keySet();
            Iterator<String> ite = set.iterator();
            String facadeBeanId = ite.next();
            Object proxyTmp = map.get(facadeBeanId);
            proxyCache.put(clsName, proxyTmp);
            return proxyTmp;
        } else {
            throw new UnsupportedOperationException(
                "beanFactory is not instanceof DefaultListableBeanFactory,tcc framework bug!");
        }
    }
}
