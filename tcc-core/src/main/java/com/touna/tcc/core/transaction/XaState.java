package com.touna.tcc.core.transaction;

/**
 * Created by chenchaojian on 17/5/29.
 */
public enum  XaState {
    BEGIN(0,"BEGIN"),
    /**
     * confirm success /rollback success = FINISH
     */
    FINISH(1,"FINISH"),

    /**
     *
     */
    TRY_SUCCESS(2,"TRY_SUCCESS"),
    /**
     * reserved for future use.
     * for child tx, not need to record. tcc only rollback try success child tx
     * for global tx,not need to use temporary. because,after try fail ,tcc will call rollback immediately。
     */
    TRY_FAIL(3,"TRY_FAIL"),

    /**
     * need redo by job
     */
    CONFIRM_FAIL(4,"CONFIRM_FAIL"),
    /**
     * need redo by job
     */
    ROLLBACK_FAIL(5,"ROLLBACK_FAIL");

    private int state;
    private String desc;

    XaState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
