/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simast.core.resolver;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.simast.base.output.ApiOutput;
import com.simast.base.params.ValidationError;
import com.simast.core.exception.ValidationEditionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一异常处理.
 *
 * @author chried
 * @date 2019-05-09
 * @since 1.0-SNAPSHOT
 */
@RestControllerAdvice
public class ExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * 系统异常.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiOutput<String> processException(Exception ex) {

        LOG.warn("出现了不可知的运行错误!", ex);

        return ApiOutput.of(ExceptionUtils.getStackTrace(ex)).fail("服务器内部错误!");
    }

    /**
     * 捕获空指针异常.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ApiOutput<String> processNullPointerException(NullPointerException ex) {

        LOG.warn("空指针异常", ex);

        return ApiOutput.of(ExceptionUtils.getStackTrace(ex))
                .fail("空指针异常!");
    }

    /**
     * 数据验证失败异常.
     *
     * @param ex 验证异常类.
     * @return 错误信息.
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<List<ValidationError>> processMelValidateErrorException(BindException ex) {

        LOG.debug("数据绑定错误", ex);

        List<ValidationError> errors = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        ex.getFieldErrors().forEach((fe) -> {
            errors.add(new ValidationError(fe.getField(), fe.getDefaultMessage()));
            sb.append(fe.getDefaultMessage());
            sb.append(",\n");
        });

        return ApiOutput.of(errors).fail(sb.toString());
    }

    /**
     * 传入的参数类型不匹配.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        LOG.debug("传入的参数类型不匹配", ex);

        return ApiOutput.of(ex.getMessage()).fail("传入的参数类型不匹配!");
    }

    /**
     * 反序列化失败.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        LOG.debug("反序列化失败", ex);

        return ApiOutput.of(ex.getMessage()).fail("反序列化失败!");
    }

    /**
     * 类型转换失败.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processInvalidFormatException(InvalidFormatException ex) {

        LOG.warn("类型转换失败", ex);

        return ApiOutput.of(ex.getMessage()).fail("类型转换失败!");
    }

    /**
     * 数据格式不合法的验证错误.
     *
     * @param ex 异常.
     * @return 带有详细信息的错误提示.
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<List<ValidationError>> processConstraintViolationException(ConstraintViolationException ex) {

        LOG.debug("数据格式校验未通过", ex);

        List<ValidationError> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach((cv) -> {
            errors.add(new ValidationError(
                    cv.getPropertyPath().toString(),
                    cv.getMessage()));
        });

        return ApiOutput.of(errors).fail(ex.getLocalizedMessage());
    }

    /**
     * 验证重复提交异常.
     *
     * @param ex 异常信息.
     * @return 消息.
     */
    @ExceptionHandler(value = {ValidationEditionException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processValidationEditionException(ValidationEditionException ex) {

        LOG.debug("数据已过期，请刷新后再提交！", ex);

        return ApiOutput.of(ex.getMessage()).fail(ex.getMessage());
    }

    /**
     * 全局拦截验证错误.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processValidationException(ValidationException ex) {

        LOG.debug(ex.getMessage(), ex);

        return ApiOutput.of(ex.getMessage()).fail(ex.getMessage());
    }

    /**
     * 输入参数不匹配.
     *
     * @param ex 异常
     * @return 消息
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiOutput<String> processIllegalArgumentException(IllegalArgumentException ex) {

        LOG.debug("输入参数不匹配", ex);

        return ApiOutput.of(ex.getMessage()).fail("输入参数不匹配!");
    }
}
