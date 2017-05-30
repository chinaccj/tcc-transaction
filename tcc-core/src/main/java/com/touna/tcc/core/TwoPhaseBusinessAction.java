package com.touna.tcc.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenchaojian on 17/5/6.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TwoPhaseBusinessAction {
    /**
     * comfirm method of tcc transaction
     * @return
     */
    String commitMethod();

    /**
     * cancel method of tcc transaction
     * @return
     */
    String rollbackMethod();
}
