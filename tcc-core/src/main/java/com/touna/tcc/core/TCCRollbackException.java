package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TCCRollbackException extends RuntimeException {

    public TCCRollbackException() {
        super();
    }


    public TCCRollbackException(String message) {
        super(message);
    }


    public TCCRollbackException(String message, Throwable cause) {
        super(message, cause);
    }


    public TCCRollbackException(Throwable cause) {
        super(cause);
    }
}
