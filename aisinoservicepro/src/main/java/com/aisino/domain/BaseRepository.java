package com.aisino.domain;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Martin.Ou
 * Date: 11/24/11
 * Time: 4:13 PM
 */
public interface BaseRepository<T extends AbstractBaseDomain> {
    /**
     * 添加数据
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @param entity   数据库操作实体
     * @return 添加成功多少
     */
    Integer insert(String stmtName, T entity);

    /**
     * 获取所有的T 实体
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @return T实体列表
     */
    List<T> findAll(String stmtName);

    /**
     * 根据条件获取的T 实体
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @param paramMap 查询条件
     * @return T实体列表
     */
    List<T> findByCondition(String stmtName, Object paramMap);

    /**
     * 根据条件更新的T 实体
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @return T实体列表
     */
    Integer update(String stmtName, Object params);

    /**
     * 根据条件删除的T 实体
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @return T实体列表
     */
    Integer delete(String stmtName, Object params);

    /**
     * 根据获取的T 实体总数
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @return T实体
     */
    Integer getCount(String stmtName);

    /**
     * 根据条件获取的T 实体总数
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @param paramMap 查询条件
     * @return T实体
     */
    Integer getCountByCondition(String stmtName, Object paramMap);

    /**
     * 根据条件获取的T 实体
     *
     * @param stmtName 对应的MyBatis的映射信息
     * @param params   查询条件
     * @return T实体
     */
    T get(String stmtName, Object params);
}

