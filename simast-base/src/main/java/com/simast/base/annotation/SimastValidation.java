package com.simast.base.annotation;

import java.lang.annotation.*;

/**
 * 控制器数据校验.
 *
 * @author chried
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimastValidation {

    /**
     * 该属性是否可以为null.
     *
     * @return 布尔值.
     */
    boolean nullable() default true;

    /**
     * 该属性是否可以为null或者"".
     * <pre>
     *     如果为false,那么字符串不允许为空或者空字符串.
     * </pre>
     *
     * @return 布尔值
     */
    boolean empty() default true;
}
