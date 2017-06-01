package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/31.
 */
public class TCCLogException  extends RuntimeException {

    public TCCLogException() {
        super();
    }


    public TCCLogException(String message) {
        super(message);
    }


    public TCCLogException(String message, Throwable cause) {
        super(message, cause);
    }


    public TCCLogException(Throwable cause) {
        super(cause);
    }
}