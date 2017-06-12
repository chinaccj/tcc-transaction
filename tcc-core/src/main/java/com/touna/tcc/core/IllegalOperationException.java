package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/6/7.
 */
public class IllegalOperationException extends TccFrameworkException {

    public IllegalOperationException() {
        super();
    }


    public IllegalOperationException(String message) {
        super(message);
    }


    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalOperationException(Throwable cause) {
        super(cause);
    }
}
