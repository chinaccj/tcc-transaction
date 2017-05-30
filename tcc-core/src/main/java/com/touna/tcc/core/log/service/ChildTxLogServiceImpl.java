package com.touna.tcc.core.log.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Created by chenchaojian on 17/5/29.
 */
public class ChildTxLogServiceImpl implements ChildTxLogService {
    @Override
    public void begin(String xid, String cXid, String clsName, String commitMethod, String rollbackMethod
            , Class[] paramsTypes, Object [] paramValues) {

        Assert.notNull(xid,"xid can not be null");
        Assert.notNull(cXid,"cxid can not be null");
        Assert.notNull(clsName,"clsName can not be null");
        Assert.notNull(commitMethod,"commitMethod can not be null");
        Assert.notNull(rollbackMethod,"rollbackMethod can not be null");

        Assert.notNull(paramsTypes,"paramsTypes can not be null");
        Assert.notNull(paramValues,"paramValues can not be null");





        List<Object> list = new ArrayList<Object>();
        if (paramValues != null) {
            for (int i = 0; i < paramValues.length; i++) {
                list.add(paramValues[i]);
            }
            //                  serialized
            //                Kryo kryo = new Kryo();
            //                        Output output = new Output(new FileOutputStream("/tmp/arguments.bin"));
            //                        kryo.writeObject(output,list);
            //                        output.close();
            //
            //                  deserialized
            //                Kryo kryo = new Kryo();
            //                input = new Input(new FileInputStream("/tmp/arguments.bin"));
            //
            //                List<Object> objList = kryo.readObject(input, ArrayList.class);

            System.out.println("child xa begin");
        }
    }

    @Override
    public void finish(String xid, String cXid) {
        System.out.println("child xa finish");

    }

    @Override
    public void rollback(String xid, String cXid) {
        System.out.println("child xa rollback");

    }

    @Override
    public void confirmFail(String xid, String cXid) {
        System.out.println("child xa confirmFail");

    }

    @Override
    public void rollbackFail(String xid, String cXid) {
        System.out.println("child xa rollbackFail");

    }
}
