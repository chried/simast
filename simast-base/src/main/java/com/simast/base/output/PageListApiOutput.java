package com.simast.base.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页对象.
 *
 * @param <T>
 * @author chried
 */
@ApiModel(description = "返回分页数据")
public class PageListApiOutput<T> extends ListApiOutput<T> {

    /**
     * 当前页.
     */
    @ApiModelProperty(name = "当前页")
    private int pageNo;

    /**
     * 每页展示.
     */
    @ApiModelProperty(name = "每页展示")
    private int pageSize = 1;

    /**
     * 总数.
     */
    @ApiModelProperty(name = "总数")
    private int totalCount;

    /**
     * 总页数.
     */
    @ApiModelProperty(name = "总页数")
    private int totalPages;

    /**
     * 构造器.
     * <pre>
     *     spring page赋值.
     * </pre>
     *
     * @param data
     */
    public PageListApiOutput(Page<T> data) {

        super(data.getContent());

        this.totalCount = (int) data.getTotalElements();
        this.totalPages = data.getTotalPages();
        this.pageNo = data.getNumber() + 1;
        this.pageSize = data.getSize();
    }

    /**
     * 构造器.
     *
     * @param data       数据.
     * @param totalCount 总数.
     * @param totalPages 总页数.
     * @param pageNo     当前页.
     * @param pageSize   每页显示.
     */
    public PageListApiOutput(List<T> data, int totalCount, int totalPages, int pageNo, int pageSize) {

        this.setData(data);
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * 扩展方法.
     *
     * @param data       数据.
     * @param totalCount 总数.
     * @param totalPages 总页数.
     * @param pageNo     当前页.
     * @param pageSize   每页显示.
     * @param <TD>
     * @return 分页.
     */
    public static <TD> PageListApiOutput<TD> of(List<TD> data, int totalCount, int totalPages, int pageNo, int pageSize) {

        return new PageListApiOutput(data, totalCount, totalPages, pageNo, pageSize);
    }

    /**
     * 获取对象.
     *
     * @param page 分页信息.
     * @param <TD> 泛型.
     * @return 分页数据.
     */
    public static <TD> PageListApiOutput<TD> of(Page<TD> page) {

        return new PageListApiOutput(page);
    }

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
     * Gets the value of totalPages.
     *
     * @return the value of totalPages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the totalPages.
     * <p>You can use getTotalPages() to get the value of totalPages.</p>
     *
     * @param totalPages totalPages.
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
