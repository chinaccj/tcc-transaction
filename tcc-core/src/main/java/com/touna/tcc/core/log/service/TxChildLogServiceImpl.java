package com.touna.tcc.core.log.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import com.touna.tcc.core.log.ClassWrapper;
import com.touna.tcc.core.log.dao.TxChildDao;
import com.touna.tcc.core.log.dao.model.TxChild;
import com.touna.tcc.core.transaction.XaState;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.touna.tcc.core.TccLogException;
import com.touna.tcc.core.log.ObjectWrapper;
import com.touna.tcc.core.log.Serializer;

/**
 * Created by chenchaojian on 17/5/29.
 */
public class TxChildLogServiceImpl implements TxChildLogService {

    private Serializer serializer;
    private TxChildDao txChildDao;


    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void trySuccess(int sequence ,String xid, String cXid, String clsName, String commitMethod,
                      String rollbackMethod, Class[] paramsTypes, Object[] paramValues,String dubboVersion) {

        checkParams(xid, cXid, clsName, commitMethod, rollbackMethod, paramsTypes, paramValues);
        TxChild txChild = null;
        try {
            byte[] bytesParamValues = serializer.serialize(new ObjectWrapper(paramValues));

            //paramsType 128byte足够。
            byte []clssBytes = serializer.serialize(new ClassWrapper(paramsTypes),128);

            txChild = new TxChild();
            txChild.setClsName(clsName);
            txChild.setCommitMethod(commitMethod);
            txChild.setcXid(cXid);
            txChild.setParamesTypes(clssBytes);
            txChild.setParamesValues(bytesParamValues);
            txChild.setRollbackMethod(rollbackMethod);
            txChild.setStatus(XaState.TRY_SUCCESS.getState());
            txChild.setXid(xid);
            txChild.setSequence(sequence);
            txChild.setDubboVersion(dubboVersion);

            txChildDao.insert(txChild);


        } catch (DuplicateKeyException ex){
            //this scene merely occur.only occur while consumer retry (dubbo use FailoverCluster handle timeout exception)
            txChildDao.updateState(txChild);

        }


    }


    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void tryFail(int sequence, String xid, String cXid, String clsName, String commitMethod, String rollbackMethod, Class[] paramsTypes, Object[] paramValues,String dubboVersion) {
        checkParams(xid, cXid, clsName, commitMethod, rollbackMethod, paramsTypes, paramValues);

        TxChild txChild = null;
        try {
            byte[] bytes = serializer.serialize(new ObjectWrapper(paramValues));

            //paramsType 128byte足够。
            byte []clssBytes = serializer.serialize(new ClassWrapper(paramsTypes),128);

            txChild = new TxChild();
            txChild.setClsName(clsName);
            txChild.setCommitMethod(commitMethod);
            txChild.setcXid(cXid);
            txChild.setParamesTypes(clssBytes);
            txChild.setParamesValues(bytes);
            txChild.setRollbackMethod(rollbackMethod);
            txChild.setStatus(XaState.TRY_FAIL.getState());
            txChild.setXid(xid);
            txChild.setSequence(sequence);
            txChild.setDubboVersion(dubboVersion);

            txChildDao.insert(txChild);


        } catch (DuplicateKeyException ex){
            //this scene merely occur.only occur while consumer retry (dubbo use FailoverCluster handle timeout exception)
            txChildDao.updateState(txChild);
        }

    }


    private void checkParams(String xid, String cXid, String clsName, String commitMethod,
                            String rollbackMethod, Class[] paramsTypes, Object[] paramValues) {
        Assert.notNull(xid, "xid can not be null");
        Assert.notNull(cXid, "cxid can not be null");
        Assert.notNull(clsName, "clsName can not be null");
        Assert.notNull(commitMethod, "commitMethod can not be null");
        Assert.notNull(rollbackMethod, "rollbackMethod can not be null");
        Assert.notNull(paramsTypes, "paramsTypes can not be null");
        Assert.notNull(paramValues, "paramValues can not be null");
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void finish(String xid, String cXid) {

        TxChild tx = new TxChild();
        tx.setcXid(cXid);
        tx.setXid(xid);
        tx.setStatus(XaState.FINISH.getState());
        tx.setEndTime(new Date());

        txChildDao.update(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void confirmFail(String xid, String cXid) {
        TxChild tx = new TxChild();
        tx.setXid(xid);
        tx.setcXid(cXid);
        tx.setStatus(XaState.CONFIRM_FAIL.getState());

        txChildDao.updateState(tx);
    }

    @Transactional(value = "tccTransactionManager",propagation = Propagation.REQUIRES_NEW)
    @Override
    public void rollbackFail(String xid, String cXid) {
        TxChild tx = new TxChild();
        tx.setXid(xid);
        tx.setcXid(cXid);
        tx.setStatus(XaState.ROLLBACK_FAIL.getState());

        txChildDao.updateState(tx);
    }

    @Override
    public List<TxChild> getChildTxsByXid(String xid) {
        List<TxChild> list = txChildDao.getChildTxsByXid(xid);
        //需要反序列化
        for(TxChild txChild: list){
            byte[] paramesTypes = txChild.getParamesTypes();
            byte[] paramesValues = txChild.getParamesValues();

            ClassWrapper classWrapper = (ClassWrapper)serializer.deserialize(paramesTypes, ClassWrapper.class);
            txChild.setParamsTypesArray(classWrapper.toClassArray());


            ObjectWrapper objectWrapper = (ObjectWrapper)serializer.deserialize(paramesValues, ObjectWrapper.class);
            txChild.setParamValuesArray(objectWrapper.toObjectArray());

        }

        return list;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public TxChildDao getTxChildDao() {
        return txChildDao;
    }

    public void setTxChildDao(TxChildDao txChildDao) {
        this.txChildDao = txChildDao;
    }
}
