package com.simast.base.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 查询form.
 *
 * @author gaoweibing
 */
@ApiModel(description = "查询form")
public class QueryForm implements Serializable {

    /**
     * 关键字.
     * <pre>
     *     配合keys使用，不可单独使用，主要解决一个输入框可以匹配模糊查询多个属性需求.
     *     例如:
     *     -------------------
     *     -  密码/用户名/昵称 -
     *     -------------------
     * </pre>
     */
    @ApiModelProperty(name = "关键字")
    private String keyword;

    /**
     * 关键字匹配参数.
     * <pre>
     *     配合keyword使用，不可单独存在.
     * </pre>
     */
    @ApiModelProperty(name = "关键字匹配参数")
    private List<String> keys;

    /**
     * 定义参数.
     */
    @ApiModelProperty(name = "参数")
    private Map<String, Object> params;

    /**
     * 排序map.
     * <pre>
     *     需要排序的字段按照:字段=DESC/ASC格式进行封装.
     * </pre>
     */
    @ApiModelProperty(name = "排序参数")
    private Map<String, String> orderMap;

    /**
     * Gets the value of keyword.
     *
     * @return the value of keyword.
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Sets the keyword.
     * <p>You can use getKeyword() to get the value of keyword.</p>
     *
     * @param keyword keyword.
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Gets the value of keys.
     *
     * @return the value of keys.
     */
    public List<String> getKeys() {
        return keys;
    }

    /**
     * Sets the keys.
     * <p>You can use getKeys() to get the value of keys.</p>
     *
     * @param keys keys.
     */
    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    /**
     * Gets the value of params.
     *
     * @return the value of params.
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * Sets the params.
     * <p>You can use getParams() to get the value of params.</p>
     *
     * @param params params.
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, String> orderMap) {
        this.orderMap = orderMap;
    }
}
