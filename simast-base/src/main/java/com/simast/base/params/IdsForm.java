package com.simast.base.params;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 定义批量操作id.
 *
 * <pre>
 *    目前定义两种传递方式,字符串跟数组.
 *    字符串用英文逗号隔离，数组直接包装ID.
 * </pre>
 *
 * @author chried
 */
public class IdsForm implements Serializable {

    /*
     * 注意特殊情况，如果都有值，那么将ids清空，然后将id转成ids，在赋值给ids，而不是叠加.
     */

    /**
     * id组成字符串,用英文逗号隔开.
     * <pre>
     *     此属性作为辅助，无论是否有值，都会以属性ids传入后台进行处理.
     *     如果id有值，那么转换成属性ids.
     * </pre>
     */
    private String id;

    /**
     * id组成的数据.
     * <pre>
     *     如果id存在，那么需要将id转成ids.
     * </pre>
     */
    private List<String> ids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * <pre>
     *     如果id有值，那么以id为准，如果都有值，那么需要将ids清空，并且将id转成ids.
     * </pre>
     *
     * @return ids.
     */
    public List<String> toIds() {

        if (StringUtils.isNotBlank(id)) {
            if (ids == null) {
                ids = new ArrayList<>();
            }
            if (!ids.isEmpty()) {
                ids.clear();
            }
            ids.addAll(Arrays.asList(StringUtils.split(id, ",")));
        }

        return ids;
    }
}
