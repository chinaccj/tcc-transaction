package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TccRollbackException extends Exception {

    public TccRollbackException() {
        super();
    }


    public TccRollbackException(String message) {
        super(message);
    }


    public TccRollbackException(String message, Throwable cause) {
        super(message, cause);
    }


    public TccRollbackException(Throwable cause) {
        super(cause);
    }
}
