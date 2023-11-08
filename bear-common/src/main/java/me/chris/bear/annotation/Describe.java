package me.chris.bear.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 辅助注解 - 用于添加相关的描述
 *
 * @author Christopher
 * @date 2023/11/8
 **/
@Retention(RUNTIME)
@Documented
@Inherited
public @interface Describe {

    /** 概述 */
    String value();

    /** 明细 */
    String detail() default "";
}
