package com.touna.tcc.core.log;

/**
 * Created by chenchaojian on 17/5/31.
 */
public interface Serializer {
    byte[] serialize(Object object);

    byte[] serialize(Object object,int capacity);


    Object deserialize(byte[] bytes,Class type);
}
