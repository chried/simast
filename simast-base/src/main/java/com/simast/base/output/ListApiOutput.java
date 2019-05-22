/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simast.base.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回列表类型结果.
 *
 * @author chried
 * @date 2019-05-09
 * @since 1.0-SNAPSHOT
 */
@ApiModel(description = "返回列表结构")
public class ListApiOutput<T> extends ApiOutput<List<T>> {

    /**
     * 创建该结构体自己.
     *
     * @param <TD> 实体类
     * @param data 列表数据
     * @return 该结构体
     */
    public static <TD> ListApiOutput<TD> of(List<TD> data) {
        return new ListApiOutput<>(data);
    }

    /**
     * 合计.
     */
    @ApiModelProperty(name = "合计")
    protected Map<String, Object> total;


    public ListApiOutput() {
        this.total = new HashMap<>();
    }

    /**
     * 构造函数.
     *
     * @param data 数据列表
     */
    public ListApiOutput(List<T> data) {
        super(data);
        this.total = new HashMap<>();
    }

    /**
     * 合计.
     *
     * @return the total
     */
    public Map<String, Object> getTotal() {
        return total;
    }

    /**
     * 合计.
     *
     * @param total the total to set
     */
    public void setTotal(Map<String, Object> total) {
        this.total = total;
    }

}
