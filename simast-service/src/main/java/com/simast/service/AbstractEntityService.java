package com.simast.service;


import com.simast.base.entity.AbstractEntity;
import com.simast.base.output.ApiOutput;
import com.simast.base.output.PageListApiOutput;
import com.simast.base.params.QueryPageForm;

import java.util.List;

/**
 * 设置父级service.
 *
 * @author chried
 */
public interface AbstractEntityService<T extends AbstractEntity> {

    /**
     * 根据id获取.
     *
     * @param id 主键id.
     * @return 对象.
     */
    T findById_(String id);

    /**
     * 根据id获取.
     *
     * @param id 主键.
     * @return 对象.
     */
    ApiOutput<T> findById(String id);

    /**
     * 批量获取.
     *
     * @param ids 主键ids.
     * @return 对象集合.
     */
    List<T> findByIds_(List<String> ids);

    /**
     * 批量获取.
     *
     * @param ids 主键ids.
     * @return 对象集合.
     */
    ApiOutput<List<T>> findByIds(List<String> ids);

    /**
     * 添加.
     *
     * @param t 对象.
     * @return 新对象.
     */
    T add_(T t);

    /**
     * 添加.
     *
     * @param t 对象.
     * @return 新对象.
     */
    ApiOutput<T> add(T t);

    /**
     * 保存.
     *
     * @param t 对象.
     * @return 新对象.
     */
    T save_(T t);

    /**
     * 保存.
     *
     * @param t 对象.
     * @return 新对象.
     */
    ApiOutput<T> save(T t);

    /**
     * 保存全部.
     *
     * @param ts 对象集合.
     * @return 新对象集合.
     */
    List<T> saveAll_(List<T> ts);

    /**
     * 保存全部.
     *
     * @param ts 对象集合.
     * @return 新对象集合.
     */
    ApiOutput<List<T>> saveAll(List<T> ts);

    /**
     * 更新.
     *
     * @param t 对象.
     * @return 新对象.
     */
    T modify_(T t);

    /**
     * 更新.
     *
     * @param t 对象.
     * @return 新对象.
     */
    ApiOutput<T> modify(T t);

    /**
     * 获取对象.
     *
     * @param id 主键.
     * @return 对象.
     */
    T get_(String id);

    /**
     * 获取对象.
     *
     * @param id 主键.
     * @return 对象.
     */
    ApiOutput<T> get(String id);

    /**
     * 获取用于编辑页面.
     *
     * @param id 主键id.
     * @return 对象.
     */
    T getOfEdit_(String id);

    /**
     * 获取用于编辑页面.
     *
     * @param id 主键id.
     * @return 对象.
     */
    ApiOutput<T> getOfEdit(String id);

    /**
     * 删除(物理删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    int delete_(String id);

    /**
     * 批量删除(物理删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    int deletes_(List<String> ids);

    /**
     * 删除(物理删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    ApiOutput<Integer> delete(String id);

    /**
     * 批量删除(物理删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    ApiOutput<Integer> deletes(List<String> ids);

    /**
     * 删除(逻辑删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    int remove_(String id);

    /**
     * 批量删除(逻辑删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    int removes_(List<String> ids);

    /**
     * 删除(逻辑删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    ApiOutput<Integer> remove(String id);

    /**
     * 删除(逻辑删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    ApiOutput<Integer> removes(List<String> ids);

    /**
     * 分页查询.
     *
     * @param queryPageForm form.
     * @return 分页信息.
     */
    PageListApiOutput<T> query(QueryPageForm queryPageForm);

}
