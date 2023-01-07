package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wanglin
 * @date 2020/5/11 16:04
 */
public interface BaseService<E,ID> {

  /**
   * 分页查询
   *
   * @param pageNum  当前页
   * @param pageSize 每页的数据条数
   * @param entity   可根对象信息进行精确查找
   * @return Map
   */
  PageResponse<E> page(int pageNum, int pageSize, E entity);

  /**
   * 分页查询
   *
   * @param pageNum  当前页
   * @param pageSize 每页的数据条数
   * @return Map
   */
  PageResponse<E> page(int pageNum, int pageSize);

  /**
   * 查询所有
   *
   * @return
   */
  List<E> selectAll();

  /**
   * 根据主键查询，未查找到会抛出异常
   *
   * @param key
   * @return
   */
  E selectByKey(ID key);

  /**
   * 根据主键查询，未查找到不会抛出异常
   *
   * @param id
   * @return
   */
  Optional<E> selectById(ID id);

  /**
   * 保存实体
   *
   * @param entity
   * @return
   */
  E save(E entity);

  /**
   * 批量新增
   *
   * @param entity
   * @return
   */
  List<E> saveBatch(List<E> entity);

  /**
   * 删除对象
   *
   * @param entity
   */
  void delete(E entity);

  /**
   * 批量删除对象
   *
   * @param entities entities
   */
  void deleteBatch(List<E> entities);

  /**
   * 批量删除根据id
   *
   * @param ids ids
   */
  void deleteBatch(ID[] ids);

  /**
   * 根据主键删除
   *
   * @param id
   */
  void deleteById(ID id);

  /**
   * 更新实体（实体成员变量为null的不会更新）
   *
   * @param entity
   * @return
   */
  E update(E entity, ID id);

  /**
   * 批量更新对象
   *
   * @param entities entities
   */
  void updateBatch(List<E> entities, ID[] ids);

}
