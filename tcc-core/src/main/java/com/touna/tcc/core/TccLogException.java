package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/31.
 */
public class TccLogException extends TccFrameworkException {

    public TccLogException() {
        super();
    }


    public TccLogException(String message) {
        super(message);
    }


    public TccLogException(String message, Throwable cause) {
        super(message, cause);
    }


    public TccLogException(Throwable cause) {
        super(cause);
    }
}