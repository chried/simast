package com.simast.base.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * 分页查询.
 *
 * @author gaoweibing
 */
@ApiModel(description = "分页查询")
public class QueryPageForm extends QueryForm {

    /**
     * 当前页.
     */
    @ApiModelProperty(name = "当前页")
    private int pageNo = 1;

    /**
     * 每页展示.
     */
    @Min(1)
    @ApiModelProperty(name = "每页显示")
    private int pageSize = 10;

    /**
     * 总数.
     */
    @ApiModelProperty(name = "总数")
    private int totalCount;

    /**
     * 总页数.
     */
    @ApiModelProperty(name = "总页数")
    private int totalPage;

    /**
     * Gets the value of pageNo.
     *
     * @return the value of pageNo.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * Sets the pageNo.
     * <p>You can use getPageNo() to get the value of pageNo.</p>
     *
     * @param pageNo pageNo.
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * Gets the value of pageSize.
     *
     * @return the value of pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the pageSize.
     * <p>You can use getPageSize() to get the value of pageSize.</p>
     *
     * @param pageSize pageSize.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the value of totalCount.
     *
     * @return the value of totalCount.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the totalCount.
     * <p>You can use getTotalCount() to get the value of totalCount.</p>
     *
     * @param totalCount totalCount.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Gets the value of totalPage.
     *
     * @return the value of totalPage.
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Sets the totalPage.
     * <p>You can use getTotalPage() to get the value of totalPage.</p>
     *
     * @param totalPage totalPage.
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
