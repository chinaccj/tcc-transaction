package com.touna.tcc.core.transaction;

import java.util.List;

/**
 * Created by chenchaojian on 17/5/25.
 */
public class Participant {
    /**
     * 根参与者的数据库主键id
     */
    private String pId;
    /**
     * 事务状态
     */
    private XaState xaState;

    /**
     * 接口名字
     */
    private String clsName;
    /**
     * 接口参数
     */
    private String commitMethod;

    private String rollbackMethod;

    private Class[] paramsTypes;

    private List<Object> paramValues;


}
