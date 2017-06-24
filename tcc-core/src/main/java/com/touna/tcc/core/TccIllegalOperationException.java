package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/6/7.
 */
public class TccIllegalOperationException extends TccFrameworkException {

    public TccIllegalOperationException() {
        super();
    }


    public TccIllegalOperationException(String message) {
        super(message);
    }


    public TccIllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }


    public TccIllegalOperationException(Throwable cause) {
        super(cause);
    }
}
