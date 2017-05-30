package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TCCFrameworkException extends RuntimeException {

    public TCCFrameworkException() {
        super();
    }


    public TCCFrameworkException(String message) {
        super(message);
    }


    public TCCFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }


    public TCCFrameworkException(Throwable cause) {
        super(cause);
    }
}
