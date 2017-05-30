package com.touna.tcc.demo.trading.web.util;

import com.touna.tcc.demo.trading.web.controller.filter.RequestIdTracer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenchaojian on 17/1/8.
 * 方法执行时间监控
 */
public class MethodTimeAdvice implements MethodInterceptor {
    private Logger logger = LoggerFactory.getLogger("com.touna.perf");


//    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("start="+start);
        try {
            Object result = invocation.proceed();
            return  result;

        }
        catch (Throwable e){
            throw new Exception(e.getMessage(),e);
        }
        finally {
            long end = System.currentTimeMillis();
            System.out.println("end="+end);

            long run = end - start;

            String requestId = RequestIdTracer.getRequestId();
            logger.info("requestId="+requestId+" Takes:" + run + " ms ["
                    + invocation.getThis().getClass().getName() + "."
                    + invocation.getMethod().getName() + "(...)] ");
        }


        // 方法参数类型，转换成简单类型
//        Class[] params = invocation.getMethod().getParameterTypes();
//        String[] simpleParams = new String[params.length];
//        for (int i = 0; i < params.length; i++) {
//            simpleParams[i] = params[i].getSimpleName();
//        }
//        Object[] args = invocation.getArguments();




//        logger.info("Takes:" + run + " ms ["
//                + invocation.getThis().getClass().getName() + "."
//                + invocation.getMethod().getName() + "("
//                + StringUtils.join(simpleParams, ",") + ")("
//                + StringUtils.join(args, ",") + ")] ");


    }
}
