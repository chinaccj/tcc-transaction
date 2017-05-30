package com.touna.tcc.demo.trading.web.controller.filter;

import java.util.UUID;

/**
 * Created by chenchaojian on 17/1/8.
 * 本地调用链跟踪，从 filter 到方法
 */
public class RequestIdTracer {
    private static final ThreadLocal<String> localRequestId =
            new ThreadLocal<String>() {
                @Override
                protected String initialValue() {
                    return UUID.randomUUID().toString();
                }
            };

    public static String getRequestId(){
        return localRequestId.get();
    }

    public static void setRequestId(String requestId){
        localRequestId.set(requestId);
    }
}
