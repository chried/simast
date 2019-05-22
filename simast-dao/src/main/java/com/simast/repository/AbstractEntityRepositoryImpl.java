package com.simast.repository;

import com.simast.base.entity.AbstractEntity;
import com.simast.base.output.PageListApiOutput;
import com.simast.base.params.EntityParameter;
import com.simast.base.params.QueryPageForm;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义父级dao实现.
 *
 * @author chried
 */
public abstract class AbstractEntityRepositoryImpl<T extends AbstractEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager em;

    /**
     * 当前类型.
     */
    protected Class<T> entityClass;

    /**
     * 构造函数.
     *
     * @param entityClass 传入子类类型.
     */
    public AbstractEntityRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 根据id获取.
     *
     * @param id 主键id.
     * @return 对象.
     */
    public T findById_(String id) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);

        Root<T> root = query.from(entityClass);
        query.select(root);

        query.where(
                builder.equal(root.get("id"), id),
                builder.equal(root.get("status"), EntityParameter.STATUS_ACTIVE)
        );

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException nre) {
            LOG.warn("实体{}没有找到主键为{}的数据.", entityClass.getName(), id);
        }

        return null;
    }

    /**
     * 根据code查询.
     *
     * @param code
     * @return 对象.
     */
    public T findByCode_(String code) {

        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);

        Root<T> root = query.from(entityClass);
        query.select(root);

        query.where(
                builder.equal(root.get("code"), code),
                builder.equal(root.get("status"), EntityParameter.STATUS_ACTIVE)
        );

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException nre) {
            LOG.warn("实体{}没有找到编号为{}的数据.", entityClass.getName(), code);
        }

        return null;
    }

    /**
     * 批量删除(物理删除).
     *
     * @param ids 主键ids.
     * @return 成功数.
     */
    public int deletes_(List<String> ids) {

        CriteriaBuilder builder = this.em.getCriteriaBuilder();

        CriteriaDelete<T> delete = builder.createCriteriaDelete(this.entityClass);
        Root<T> root = delete.from(this.entityClass);
        Expression<String> exp = root.get("id");
        delete.where(
                builder.in(exp)
        );

        this.em.createQuery(delete);

        return ids.size();
    }

    /**
     * 分页查询.
     *
     * @param queryForm 查询条件
     * @param fields    匹配条件
     * @return 分页列表
     */
    public PageListApiOutput<T> queryPaged_(QueryPageForm queryForm, String... fields) {

        CriteriaBuilder builder = this.em.getCriteriaBuilder();

        CriteriaQuery<Long> count = builder.createQuery(Long.class);
        CriteriaQuery<T> query = builder.createQuery(entityClass);

        Root<T> rootCount = count.from(entityClass);
        Root<T> rootQuery = query.from(entityClass);

        count.select(builder.count(rootCount));
        query.select(rootQuery);

        count.where(this.queryWhere_(builder, rootCount,
                queryForm, fields));
        Long total = em.createQuery(count).getSingleResult();

        queryForm.setTotalCount(total.intValue());

        query.where(this.queryWhere_(builder, rootCount,
                queryForm, fields));

        // 如果用户自定义了排序规则，那么按照用户定义规则，否则使用默认规则.
        if (MapUtils.isNotEmpty(queryForm.getOrderMap())) {

            List<Order> orderList = new ArrayList<>();
            queryForm.getOrderMap().forEach((k, v) -> {

                if (StringUtils.equalsIgnoreCase("desc", v)) {
                    orderList.add(builder.desc(rootQuery.get(k)));

                } else {
                    orderList.add(builder.asc(rootQuery.get(k)));
                }

            });
            query.orderBy(orderList.toArray(new Order[]{}));
        } else {
            query.orderBy(builder.desc(rootQuery.get("createdOn")));
        }

        PageListApiOutput<T> result = PageListApiOutput.of(
                em.createQuery(query)
                        .setFirstResult((queryForm.getPageNo() - 1) * queryForm.getPageSize())
                        .setMaxResults(queryForm.getPageSize())
                        .getResultList(),
                total.intValue(), queryForm.getTotalPage(), queryForm.getPageNo(), queryForm.getPageSize());

        result.setTotalPages(result.getTotalCount() % result.getPageSize() == 0 ? result.getTotalCount() / result.getPageSize() : result.getTotalCount() / result.getPageSize() + 1);

        return result;
    }

    /**
     * 匹配字段.
     *
     * @param builder   查询构建器.
     * @param root      查询模板类.
     * @param queryForm 查询对象.
     * @param fields    匹配属性.
     * @return 查询条件.
     */
    private Predicate[] queryWhere_(
            CriteriaBuilder builder, Root<T> root,
            QueryPageForm queryForm, String... fields) {

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("status"), EntityParameter.STATUS_ACTIVE));

        if (StringUtils.isNotBlank(queryForm.getKeyword()) && fields != null && fields.length > 0) {
            Predicate[] search = new Predicate[fields.length];

            // 匹配目前做的是全匹配.
            for (int i = 0; i < fields.length; i++) {
                search[i] = builder.like(getExpressionByField(root, fields[i]), "%" + queryForm.getKeyword() + "%");
            }

            predicates.add(builder.or(search));
        }

        // 做一次过滤.
        queryForm.setParams(filterNullValue(queryForm.getParams()));

        if (MapUtils.isNotEmpty(queryForm.getParams())) {

            Predicate[] search = new Predicate[queryForm.getParams().size()];

            int i = 0;
            for (Map.Entry<String, Object> map : queryForm.getParams().entrySet()) {

                String keys[] = StringUtils.split(map.getKey(), ".");

                search[i] = builder.equal(keys.length == 1 ? root.get(keys[0]) : root.get(keys[0]).get(keys[1]), map.getValue());
                i++;
            }

            predicates.add(builder.and(search));
        }

        Predicate[] where = predicates.toArray(new Predicate[predicates.size()]);

        return where;
    }

    /**
     * 获取field的查询Expression.
     *
     * @param root  节点.
     * @param field 参数.
     * @return 表达式.
     */
    private Expression<String> getExpressionByField(Root<T> root, String field) {
        String[] fieldArr = StringUtils.split(field, ".");
        return getExpression(root, fieldArr, 0);
    }

    /**
     * 获取path.
     *
     * @param root     path节点.
     * @param fieldArr 拆分数组.
     * @param startInd 起点.
     * @return path.
     */
    private Path getExpression(Path root, String[] fieldArr, int startInd) {
        if (startInd >= fieldArr.length) {
            return root;
        }

        String field = fieldArr[startInd];
        if (StringUtils.isBlank(field)) {
            return root;
        }

        if (startInd < fieldArr.length - 1) {
            Path path = root.get(field);
            return getExpression(path, fieldArr, startInd + 1);
        }
        return root.get(field);
    }

    /**
     * 过滤掉无效的数据.
     *
     * @param params 参数.
     * @return 新结构.
     */
    private Map<String, Object> filterNullValue(Map<String, Object> params) {
        if (MapUtils.isEmpty(params)) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, Object> map : params.entrySet()) {

            if (map.getValue() instanceof String) {
                if (StringUtils.isNotBlank(map.getValue().toString())) {
                    resultMap.put(map.getKey(), map.getValue());
                }
            } else {
                if (map.getValue() != null) {
                    resultMap.put(map.getKey(), map.getValue());
                }
            }

        }

        return resultMap;

    }

    /**
     * 根据sql获取对象.
     *
     * @param sql    sql.
     * @param values 键值对.
     * @return 对象数据.
     */
    public List<T> getListBySQL(String sql, Map<String, Object> values) {
        LOG.info("sql:{}", sql);
        LOG.info("values:[{}]", values);

        TypedQuery<T> query = em.createQuery(sql, this.entityClass);

        if (!values.isEmpty()) {
            values.forEach(query::setParameter);
        }

        return query.getResultList();
    }

}
