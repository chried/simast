package com.simast.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定义顶级entity.
 * <pre>
 *     所有需要存库的建议都继承该类.
 * </pre>
 *
 * @author chried
 */
@Data
@ApiModel(description = "顶层entity")
public abstract class AbstractEntity implements Serializable {

    /**
     * 主键id.
     */
    @Id
    @Column(name = "x_id")
    @ApiModelProperty(name = "主键")
    private String id;

    /**
     * 验证编码.
     * <pre>
     *     主要用于验证重复提交.
     * </pre>
     */
    @Column(name = "x_edition")
    @ApiModelProperty(name = "编辑码")
    private String edition_;

    /**
     * 创建时间.
     */
    @Column(name = "create_date", updatable = false)
    @ApiModelProperty(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 更新时间.
     */
    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "更新时间")
    private LocalDateTime updateDate;

    /**
     * 删除时间.
     */
    @Column(name = "delete_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "删除时间")
    private LocalDateTime deleteDate;

    /**
     * 状态.
     * <pre>
     *     数据状态，可以用于逻辑删除.
     * </pre>
     */
    @Column(name = "x_status")
    @ApiModelProperty(name = "数据状态")
    private String status_;

}
