package com.simast.service;

import com.simast.base.entity.AbstractEntity;
import com.simast.base.output.ApiOutput;
import com.simast.base.output.PageListApiOutput;
import com.simast.base.params.EntityParameter;
import com.simast.base.params.QueryPageForm;
import com.simast.repository.AbstractEntityRepository;
import com.simast.utils.SnowFlake;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 父级service实现.
 * <pre>
 * 1、约定查询单个数据采用find*;返回对象加"_",返回ApiOutput不需要.例如:
 * <code>
 *     public ApiOutput<User> findByCode(String code);
 *     public User findByCode_(String code);
 *
 * </code>
 * 2、约定多个查询用query*;约定跟1相同.
 * 3、删除约定,remove是假删除;delete是真删除,样式跟1一样.
 * </pre>
 *
 * @param <T>
 * @author chried
 */
public abstract class AbstractEntityServiceImpl<T extends AbstractEntity> implements AbstractEntityService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityServiceImpl.class);

    /**
     * 获取dao.
     *
     * @return 子类dao.
     */
    protected abstract AbstractEntityRepository<T> getRepository();

    /**
     * 当前对象.
     */
    private Class<T> entityClass;

    /**
     * 构造器.
     *
     * @param entityClass 当前对象.
     */
    public AbstractEntityServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 根据id获取.
     *
     * @param id 主键id.
     * @return 对象.
     */
    @Override
    public T findById_(String id) {

        LOG.info("根据id获取,id=[{}]", id);

        return this.getRepository().findById(id).orElse(null);
    }

    /**
     * 根据id获取.
     *
     * @param id 主键.
     * @return 对象.
     */
    @Override
    public ApiOutput<T> findById(String id) {

        return ApiOutput.of(this.findById_(id));
    }

    /**
     * 批量获取.
     *
     * @param ids 主键ids.
     * @return 对象集合.
     */
    @Override
    public List<T> findByIds_(List<String> ids) {

        LOG.info("批量获取...ids=[{}]", ids);

        return this.getRepository().findAllById(ids);
    }

    /**
     * 批量获取.
     *
     * @param ids 主键ids.
     * @return 对象集合.
     */
    @Override
    public ApiOutput<List<T>> findByIds(List<String> ids) {

        return ApiOutput.of(this.findByIds_(ids));
    }

    /**
     * 添加.
     *
     * @param t 对象.
     * @return 新对象.
     */
    @Override
    @Transactional
    public T add_(T t) {

        LOG.info("添加对象");
        if (StringUtils.isBlank(t.getId())) {
            // 使用传说中的雪花算法生成ID.
            t.setId(Long.toBinaryString(SnowFlake.nextId()));
        }
        t.setCreateDate(LocalDateTime.now());
        t.setUpdateDate(t.getCreateDate());
        t.setEdition_(UUID.randomUUID().toString());
        t.setStatus_(EntityParameter.STATUS_ACTIVE);

        return this.getRepository().save(t);
    }

    /**
     * 添加.
     *
     * @param t 对象.
     * @return 新对象.
     */
    @Override
    public ApiOutput<T> add(T t) {

        return ApiOutput.of(this.add_(t));
    }

    @Override
    public T save_(T t) {

        LOG.info("保存对象...");

        // ip存在，那么是保存.
        if (StringUtils.isNotBlank(t.getId())) {

            return this.modify_(t);
        } else {

            return this.add_(t);
        }
    }

    @Override
    public ApiOutput<T> save(T t) {

        return ApiOutput.of(this.save_(t));
    }

    /**
     * 保存全部.
     *
     * @param ts 对象集合.
     * @return 新对象集合.
     */
    @Override
    public List<T> saveAll_(List<T> ts) {

        LOG.info("保存全部对象...");

        List<T> list = new ArrayList<>();

        for (T t : ts) {
            list.add(this.save_(t));
        }

        return list;
    }

    /**
     * 保存全部.
     *
     * @param ts 对象集合.
     * @return 新对象集合.
     */
    @Override
    public ApiOutput<List<T>> saveAll(List<T> ts) {

        return ApiOutput.of(this.saveAll_(ts));
    }

    /**
     * 更新.
     *
     * @param t 对象.
     * @return 新对象.
     */
    @Override
    @Transactional
    public T modify_(T t) {

        LOG.info("更新对象");

        t.setUpdateDate(LocalDateTime.now());
        t.setEdition_(UUID.randomUUID().toString());

        return this.getRepository().save(t);
    }

    /**
     * 更新.
     *
     * @param t 对象.
     * @return 新对象.
     */
    @Override
    public ApiOutput<T> modify(T t) {

        return ApiOutput.of(this.modify_(t));
    }

    /**
     * 获取对象.
     *
     * @param id 主键.
     * @return 对象.
     */
    @Override
    public T get_(String id) {

        LOG.info("获取对象,id=[{}]", id);

        return this.getRepository().getOne(id);
    }

    /**
     * 获取对象.
     *
     * @param id 主键.
     * @return 对象.
     */
    @Override
    public ApiOutput<T> get(String id) {

        return ApiOutput.of(this.get_(id));
    }

    /**
     * 获取用于编辑页面.
     *
     * @param id 主键id.
     * @return 对象.
     */
    @Override
    public T getOfEdit_(String id) {

        LOG.info("获取用于编辑,id=[{}]", id);

        T t = null;

        if (StringUtils.isBlank(id)) {
            try {
                t = this.entityClass.getConstructor().newInstance();
            } catch (Exception e) {
                LOG.error("getOfEdit_初始化错误:{}", e);
            }
        } else {
            t = this.get_(id);
        }

        return t;
    }

    /**
     * 获取用于编辑页面.
     *
     * @param id 主键id.
     * @return 对象.
     */
    @Override
    public ApiOutput<T> getOfEdit(String id) {

        return ApiOutput.of(this.getOfEdit_(id));
    }

    /**
     * 删除(物理删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    @Override
    public int delete_(String id) {

        LOG.info("删除(物理删除)...");

        this.getRepository().deleteById(id);

        return 1;
    }

    /**
     * 批量删除(物理删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    @Override
    public int deletes_(List<String> ids) {

        LOG.info("批量删除(物理删除)...ids={}", ids);

        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }

        return this.getRepository().deletes_(ids);
    }

    /**
     * 删除(物理删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    @Override
    public ApiOutput<Integer> delete(String id) {

        return ApiOutput.of(this.delete_(id));
    }

    /**
     * 批量删除(物理删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    @Override
    public ApiOutput<Integer> deletes(List<String> ids) {

        return ApiOutput.of(this.deletes_(ids));
    }

    /**
     * 删除(逻辑删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    @Override
    public int remove_(String id) {

        LOG.info("删除(逻辑删除),id=[{}]", id);

        T t = this.get_(id);

        t.setStatus_(EntityParameter.STATUS_DELETE);

        this.getRepository().save(t);

        return 1;
    }

    /**
     * 批量删除(逻辑删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    @Override
    public int removes_(List<String> ids) {

        LOG.info("批量删除(逻辑删除),ids=[{}]", ids);

        List<T> list = this.getRepository().findAllById(ids);

        for (T t : list) {

            t.setStatus_(EntityParameter.STATUS_DELETE);

        }

        this.getRepository().saveAll(list);

        return ids.size();
    }

    /**
     * 删除(逻辑删除).
     *
     * @param id 主键id.
     * @return 删除数.
     */
    @Override
    public ApiOutput<Integer> remove(String id) {

        return ApiOutput.of(this.remove_(id));
    }

    /**
     * 删除(逻辑删除).
     *
     * @param ids 主键ids.
     * @return 删除数.
     */
    @Override
    public ApiOutput<Integer> removes(List<String> ids) {

        return ApiOutput.of(this.removes_(ids));
    }

    /**
     * 分页查询.
     *
     * @param queryPageForm form.
     * @return 分页信息.
     */
    @Override
    public PageListApiOutput<T> query(QueryPageForm queryPageForm) {

        LOG.info("分页查询...");

        String[] fields = CollectionUtils.isNotEmpty(queryPageForm.getKeys()) ? queryPageForm.getKeys().toArray(new String[]{}) : new String[]{};

        return getRepository().queryPaged_(queryPageForm, fields);
    }
}
