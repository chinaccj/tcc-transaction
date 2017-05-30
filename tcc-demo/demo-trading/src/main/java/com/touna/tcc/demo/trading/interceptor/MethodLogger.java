package com.touna.tcc.demo.trading.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by chenchaojian on 17/1/20.
 * service dao 拦截，监控servie/dao响应时间
 */

@Aspect
@Component
public class MethodLogger {

    @Around("(execution(* com.touna.singlepc.demo.trading..*.*(..)))")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long start = System.currentTimeMillis();
        Object retVal = null;
        try {
            retVal = point.proceed();
            System.out.println("========"+point.getSignature().toString());
        } catch (Throwable ex) {
            ex.printStackTrace();
           throw ex;
        }finally {


        }

        return retVal;
    }

    private String formatLog(String requestId,String userName,String method,long elapsed){
        StringBuilder sb = new StringBuilder();
        sb.append("requestId=").append(requestId);
        sb.append("|").append(userName);
        sb.append("|").append(method);
        sb.append("|takes ms:").append(elapsed);

        return sb.toString();
    }
}
