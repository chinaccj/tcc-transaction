package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TCCCommitException extends RuntimeException {

    public TCCCommitException() {
        super();
    }


    public TCCCommitException(String message) {
        super(message);
    }


    public TCCCommitException(String message, Throwable cause) {
        super(message, cause);
    }


    public TCCCommitException(Throwable cause) {
        super(cause);
    }
}
