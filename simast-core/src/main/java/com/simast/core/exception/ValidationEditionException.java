/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simast.core.exception;

import javax.validation.ValidationException;

/**
 * 验证重复提交异常.
 *
 * @author chried
 * @date 2019-05-09
 * @since 1.0-SNAPSHOT
 */
public class ValidationEditionException extends ValidationException {

    /**
     * 数据id.
     */
    private final String id;

    /**
     * 错误edition.
     */
    private final String edition;

    /**
     * 默认构造器.
     *
     * @param message 消息.
     * @param id      id.
     * @param edition 错误edition.
     */
    public ValidationEditionException(String message,
                                      String id, String edition) {
        super(message);

        this.id = id;
        this.edition = edition;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the edition
     */
    public String getEdition() {
        return edition;
    }


}
