package com.simast.base.output;

import com.simast.base.enums.EnumOutputCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 定义顶层返回结构.
 *
 * @param <T>
 * @author chried
 */
@ApiModel(description = "返回结构")
public class ApiOutput<T> implements Serializable {

    /**
     * 编码.
     */
    @ApiModelProperty(name = "编码")
    private int code;

    /**
     * 返回信息.
     */
    @ApiModelProperty(name = "返回信息")
    private String msg;

    /**
     * 是否成功.
     */
    @ApiModelProperty(name = "成功标志")
    private boolean success;

    /**
     * 返回数据.
     */
    @ApiModelProperty(name = "返回数据")
    private T data;


    /**
     * 扩展构造器.
     * 手动传入信息.
     * <pre>
     *     主要用于自定义的错误信息.
     * </pre>
     *
     * @param code 编码.
     * @param msg  信息.
     */
    public ApiOutput(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 默认构造器.
     * <pre>
     *     初始化正确信息.
     * </pre>
     */
    public ApiOutput() {
        this.code = EnumOutputCode.SUCCESS.getCode();
        this.msg = EnumOutputCode.SUCCESS.getMsg();
    }

    /**
     * 传入数据构造器.
     *
     * @param data 数据.
     */
    public ApiOutput(T data) {
        this();
        this.data = data;
    }

    /**
     * 建议用此方法初始化对象.
     *
     * @param <TD> 泛型.
     * @return 对象.
     */
    public static <TD> ApiOutput<TD> of() {
        return new ApiOutput<TD>();
    }

    /**
     * 直接传入数据.
     *
     * @param data 数据.
     * @param <TD> 泛型.
     * @return 对象.
     */
    public static <TD> ApiOutput<TD> of(TD data) {
        return new ApiOutput<TD>(data);
    }

    /**
     * 设置编码，消息.
     *
     * @param code 编码.
     * @param msg  消息.
     * @param <TD> 泛型.
     * @return 对象.
     */
    public static <TD> ApiOutput<TD> of(int code, String msg) {
        return new ApiOutput<TD>(code, msg);
    }

    /**
     * 设置信息.
     *
     * @param msg 信息.
     * @return 当前对象.
     */
    public ApiOutput msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 设置消息.
     *
     * @param enumOutputCode 信息枚举.
     * @return 当前对象.
     */
    public ApiOutput setOutput(EnumOutputCode enumOutputCode) {

        this.msg = enumOutputCode.getMsg();
        this.code = enumOutputCode.getCode();
        return this;
    }

    /**
     * 设置错误消息.
     *
     * @param msg 错误消息.
     * @return 当前对象.
     */
    public ApiOutput fail(String msg) {

        this.msg = msg;
        this.code = EnumOutputCode.ERROR.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 任何不等于200的错误码返回false.
     *
     * @return 返回成功标志.
     */
    public boolean isSuccess() {
        return this.code >= 200 && this.code < 300;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
