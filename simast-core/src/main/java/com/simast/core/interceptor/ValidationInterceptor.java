/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simast.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

/**
 * 验证拦截器.
 *
 * @author chried
 */
@Aspect
@Component
public class ValidationInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationInterceptor.class);

    /**
     * 验证拦截切面.
     * <pre>
     * execution 指拦截哪些运行方法，编写格式为
     * 修饰符 返回类型 包.包.包.方法(参数)
     * .. 表示“所有(包、参数)”的意思
     * </pre>
     *
     * @param pjp           切入点
     * @param bindingResult 拦截变量
     * @return 对象.
     * @throws Throwable 异常
     */
    @Around("@annotation(com.simast.base.annotation.SimastControllerValidation) && args(.., bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult)
            throws Throwable {

        if (bindingResult.hasErrors()) {
            LOG.debug("验证出现错误");
            throw new BindException(bindingResult);
        }

        return pjp.proceed();
    }

}
