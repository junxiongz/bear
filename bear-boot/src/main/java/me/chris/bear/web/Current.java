package me.chris.bear.web;

import java.lang.annotation.*;

/**
 * 注入当前安全上下文中支持的对象,例如当前的用户或授权客户端等
 * @author Christopher
 * @date 2023/11/8
 **/
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Current {

    /**
     * 如果该参数为必须的,但没找到则抛出授权失败的异常
     *
     * @return 该注入是否必须
     */
    boolean required() default true;
}
