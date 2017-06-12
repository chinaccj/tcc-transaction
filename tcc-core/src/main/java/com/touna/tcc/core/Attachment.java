package com.touna.tcc.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenchaojian on 17/5/6.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface Attachment {
    /**
     * key of parameter to be attached to TccContext
     * @return
     */
    String key();
}
