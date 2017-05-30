package com.touna.tcc.demo.trading.service;

import org.springframework.core.type.MethodMetadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenchaojian on 17/5/27.
 */
public class ScheduleTest {
    static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public static void main(String args[]){

//        Runnable runnable = new Runnable() {
//            public void run() {
//                // task to run goes here
//                System.out.println("Hello !!");
//            }
//        };
//
//        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
//        service.scheduleAtFixedRate(runnable, 1, 100, TimeUnit.SECONDS);
        Hello h = new Hello();
        Method[] methods = Hello.class.getMethods();
        for(Method m:methods){
            if(Modifier.isPublic(m.getModifiers())){
                try {
                    if(m.getName().equals("hello")) {
                        Object[] argums = new Object[0];
                        List list = new ArrayList(0);
                        m.invoke(h, list.toArray());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static class Hello{
        public Hello() {
        }

        public void hello(){
            System.out.println("hello world");

        }
    }
}
