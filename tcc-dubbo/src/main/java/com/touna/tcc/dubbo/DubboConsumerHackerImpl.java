package com.touna.tcc.dubbo;

import java.lang.reflect.Method;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.Result;

/**
 * Created by chenchaojian on 17/7/2.
 */
public class DubboConsumerHackerImpl implements DubboConsumerHacker {

    private RegistryConfig registryConfig;

    private ApplicationConfig application;

    @Override
    public void invoke(Class cls, Class[] paramsTypes, Object[] arguments, String version,String invokeMethod)throws Exception{
        ReferenceConfig reference = new ReferenceConfig();
        reference.setInterface(cls);
        if(version != null) {
            reference.setVersion(version);
        }
        reference.setApplication(application);
        reference.setRegistry(registryConfig);

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        Object obj = cache.get(reference);
        Method method = obj.getClass().getDeclaredMethod(invokeMethod, paramsTypes);
        Boolean successful = (Boolean)method.invoke(obj, arguments);
        if(successful != null){
            if(!successful){
                throw new Exception("provider excute error!");
            }
        }

//        if (result.getException() != null) {
//            throw new Exception(result.getException());
//        }
    }


    /**
     * 释放 ReferenceConfig 资源
     */
    protected void destory(){
        ReferenceConfigCache.getCache().destroyAll();
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
}
