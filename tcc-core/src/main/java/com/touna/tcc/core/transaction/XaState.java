package com.touna.tcc.core.transaction;

/**
 * Created by chenchaojian on 17/5/29.
 */
public enum  XaState {
    BEGIN(0,"BEGIN"),
    FINISH(1,"FINISH"),
    CONFIRM_FAIL(2,"CONFIRM_FAIL"),
    ROLLBACK_FAIL(3,"ROLLBACK_FAIL");

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
