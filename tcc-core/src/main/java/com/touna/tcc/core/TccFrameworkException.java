package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TccFrameworkException extends RuntimeException {

    public TccFrameworkException() {
        super();
    }


    public TccFrameworkException(String message) {
        super(message);
    }


    public TccFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }


    public TccFrameworkException(Throwable cause) {
        super(cause);
    }
}
