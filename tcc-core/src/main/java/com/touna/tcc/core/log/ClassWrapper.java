package com.touna.tcc.core.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Created by chenchaojian on 17/5/31.
 * 因为 Object[] 不好实现序列化和反序列化，做个包装类，方便序列化和反序列化
 * 实现 Object[] ->  ArrayList.
 * 支持序列化和反序列化
 */
public class ClassWrapper {
    public ClassWrapper() {
    }

    private List<Class> objects;

    public ClassWrapper(Class[] objArray) {
        if(objects == null){
            Assert.notNull("objects can not be null");
        }

        objects = new ArrayList(objArray.length);
        for(int i=0; i<objArray.length; i++){
            objects.add(objArray[i]);
        }
    }


    public Class[] toClassArray(){
        Class[] clss = new Class[this.objects.size()];
        for(int i=0;i<objects.size();i++){
            clss[i] = objects.get(i);
        }

        return clss;
    }

 }
