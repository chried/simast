package com.simast.base.params;

/**
 * 定义实体类状态.
 *
 * @author chried
 */
public final class EntityParameter {

    /**
     * 定义活动状态.
     */
    public static final String STATUS_ACTIVE = EnumEntityStatus.ACTIVE.name();

    /**
     * 定义删除状态.
     */
    public static final String STATUS_DELETE = EnumEntityStatus.DELETE.name();

    /**
     * 定义状态.
     */
    private enum EnumEntityStatus {
        ACTIVE, // 激活.
        DELETE, // 删除.
    }
}
