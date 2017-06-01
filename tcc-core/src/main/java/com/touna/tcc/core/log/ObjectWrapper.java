package com.touna.tcc.core.log;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchaojian on 17/5/31.
 * 因为 Object[] 不好实现序列化和反序列化，做个包装类，方便序列化和反序列化
 * 实现 Object[] ->  ArrayList.
 * 支持序列化和反序列化
 */
public class ObjectWrapper {
    public ObjectWrapper() {
    }

    private List<Object> objects;

    public ObjectWrapper(Object[] objArray) {
        if(objects == null){
            Assert.notNull("objects can not be null");
        }

        objects = new ArrayList(objArray.length);
        for(int i=0; i<objArray.length; i++){
            objects.add(objArray[i]);
        }
    }


    public Object[] toObjectArray(){
        return this.objects.toArray();
    }

 }
