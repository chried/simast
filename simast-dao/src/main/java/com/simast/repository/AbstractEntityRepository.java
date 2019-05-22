package com.simast.repository;

import com.simast.base.entity.AbstractEntity;
import com.simast.base.output.PageListApiOutput;
import com.simast.base.params.QueryPageForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

/**
 * 定义父级dao.
 *
 * @param <T>
 * @author chried
 */
@NoRepositoryBean
public interface AbstractEntityRepository<T extends AbstractEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    /**
     * 根据id获取.
     *
     * @param id 主键id.
     * @return 对象.
     */
    T findById_(String id);

    /**
     * 根据code查询.
     *
     * @param code
     * @return 对象.
     */
    T findByCode_(String code);

    /**
     * 批量删除.
     *
     * @param ids 主键ids.
     * @return 成功数.
     */
    int deletes_(List<String> ids);

    /**
     * 根据sql获取对象.
     * <pre>
     *    注：此方法适用于确认对象返回结果，如果组合查询返回的结果不在此方法考虑范围.
     * </pre>
     *
     * @param sql    sql.
     * @param values 键值对.
     * @return 数据.
     */
    List<T> getListBySQL(String sql, Map<String, Object> values);

    /**
     * 分页查询.
     *
     * @param queryForm 分页form.
     * @param fields    参数.
     * @return 分页信息.
     */
    PageListApiOutput<T> queryPaged_(QueryPageForm queryForm, String... fields);
}
