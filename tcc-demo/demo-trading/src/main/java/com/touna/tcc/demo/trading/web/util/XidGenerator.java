package com.touna.tcc.demo.trading.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenchaojian on 17/6/6.
 */
public class XidGenerator {
    private static AtomicInteger index = new AtomicInteger(0);

    /**
     * 带有业务含义生成事务id，xid里面可以组合分库分表的散列值等
     * @param biz
     * @return
     */
    public static String getNewXid(String biz){
        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String currentTime = formatter.format(date);
        return biz+currentTime+index.incrementAndGet();
    }

    public static void main(String args[]){
        System.out.println(getNewXid("OD"));
    }

}
