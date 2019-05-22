package com.simast.base.params;

import java.io.Serializable;

/**
 * 用于扩展属性.
 * <pre>
 *    慎用,主要用于不变属性的引用,作为冗余字段.
 * </pre>
 *
 * @author chried
 * @date 2019-05-09
 * @since 1.0-SNAPSHOT
 */
public class Type implements Serializable {

    /**
     * 编号.
     */
    private String code;

    /**
     * 名称.
     */
    private String name;

    /**
     * 无参构造器.
     */
    public Type() {
    }

    /**
     * 扩展构造器.
     *
     * @param code 编号.
     * @param name 名称.
     */
    public Type(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
