package com.touna.tcc.demo.trading.web.controller.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenchaojian on 17/1/8.
 * 请求执行时间日志监控
 */
public class LogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger("com.touna.perf");

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requestHttp = (HttpServletRequest) request;
        String uri = requestHttp.getRequestURI();
        long begin = System.currentTimeMillis();

        try{
            chain.doFilter(request,response);
        }catch (Throwable e){
            long end = System.currentTimeMillis();
            long delta = end - begin;
            log(delta,uri,null,e);

        }finally {
            long end = System.currentTimeMillis();
            long delta = end - begin;

            log(delta,uri,null,null);
        }
    }

    private void log(long time,String uri,String parentRequestId,Throwable e){

        if(null != e){
            logger.error(e.getMessage(),e);
            logger.info("url="+uri+" time="+time+"ms requestId="+RequestIdTracer.getRequestId());

        }else {
            logger.info("url="+uri+" time="+time+"ms requestId="+RequestIdTracer.getRequestId());

        }
    }

    public void destroy() {

    }
}
