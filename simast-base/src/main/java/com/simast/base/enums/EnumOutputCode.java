package com.simast.base.enums;

/**
 * 定义apiOutput返回code及msg.
 *
 * @author chried
 */
public enum EnumOutputCode {

    /**
     * 定义错误信息.
     */
    ERROR(801, "系统错误"),
    /**
     * 定义正确返回信息.
     * <pre>
     *     为了增加兼容性，以及特定的需求，规定200~300之间返回success皆为true.
     * </pre>
     */
    SUCCESS(200, "操作成功"),
    ;

    private int code;

    private String msg;

    EnumOutputCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
    }}
