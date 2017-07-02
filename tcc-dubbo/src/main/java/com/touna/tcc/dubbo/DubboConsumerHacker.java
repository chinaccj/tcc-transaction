package com.touna.tcc.dubbo;

/**
 * Created by chenchaojian on 17/7/2.
 */
public interface DubboConsumerHacker {
    void invoke(Class cls, Class[] paramsTypes, Object[] arguments, String version,String invokeMethod)throws Exception;
}
