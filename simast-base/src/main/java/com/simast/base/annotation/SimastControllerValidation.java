package com.simast.base.annotation;

import java.lang.annotation.*;

/**
 * 定义controller数据验证.
 * <blockquote>
 * <pre>
 *     @SimastControllerValidation
 *     public ApiOutput<String> submit(@Validated UserForm form,BindingResult bindingResult){
 *          throw new UnsupportedOperationException("")
 *     }
 * </pre>
 * </blockquote>
 * {@link UnsupportedOperationException}
 *
 * @author gwb
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimastControllerValidation {
}
