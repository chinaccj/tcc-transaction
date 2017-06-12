package com.touna.tcc.core;

/**
 * Created by chenchaojian on 17/5/28.
 */
public class TccCommitException extends TccFrameworkException {

    public TccCommitException() {
        super();
    }


    public TccCommitException(String message) {
        super(message);
    }


    public TccCommitException(String message, Throwable cause) {
        super(message, cause);
    }


    public TccCommitException(Throwable cause) {
        super(cause);
    }
}
