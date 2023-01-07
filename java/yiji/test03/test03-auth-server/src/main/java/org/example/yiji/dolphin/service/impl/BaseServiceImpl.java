package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.org.examplemon.PageStaticField;
import org.example.yiji.dolphin.service.BaseService;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanglin
 * @date 2020/5/11 17:49
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<E,ID> implements BaseService<E,ID> {

  //完成JpaRepository注入
  private JpaRepository<E, ID> repository;

  @Autowired
  //警告可忽略是正确的
  public void setRepository(JpaRepository<E, ID> repository) {
    this.repository = repository;
  }

  /**
   * 条件分页查询
   *
   * @param pageNum  当前页
   * @param pageSize 每页的数据条数
   * @param entity   可根对象信息进行精确查找
   * @return
   */
  @Override
  public PageResponse<E> page(int pageNum, int pageSize, E entity) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    //查询的实例（条件）
    Example<E> example = Example.of(entity);
    Page<E> page = repository.findAll(example, pageable);
    return new PageResponse<>(page);
  }

  /**
   * 分页查询全部
   *
   * @param pageNum  当前页
   * @param pageSize 每页的数据条数
   * @return
   */
  @Override
  public PageResponse<E> page(int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    //查询的实例（条件）
    Page<E> page = repository.findAll(pageable);
    return new PageResponse<>(page);
  }

  /**
   * 查询全部
   *
   * @return list集合
   */
  @Override
  public List<E> selectAll() {
    return repository.findAll();
  }

  /**
   * 单条件根据主键查询，未查找到会抛出异常
   *
   * @param key id
   * @return domain
   */
  @Override
  public E selectByKey(ID key) {
    return repository.getOne(key);
  }

  /**
   * 单条件根据主键查询，未查找到不会抛出异常
   *
   * @param id id
   * @return Optional
   */
  @Override
  public Optional<E> selectById(ID id) {
    return repository.findById(id);
  }

  /**
   * 保存
   *
   * @param entity 对象
   * @return domain
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public E save(E entity) {
    return repository.save(entity);
  }

  /**
   * 批量新增
   *
   * @param entity
   * @return
   */
  @Override
  public List<E> saveBatch(List<E> entity) {
    return repository.saveAll(entity);
  }

  /**
   * 删除对象
   *
   * @param entity 对象
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(E entity) {
    repository.delete(entity);
  }

  /**
   * 根据id删除
   *
   * @param id id
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteById(ID id) {
    repository.deleteById(id);
  }

  /**
   * 批量删除根据id
   *
   * @param ids ids
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteBatch(ID[] ids) {
    List<E> list = new ArrayList<>();
    for (ID id : ids) {
      E entity = repository.getOne(id);
      list.add(entity);
    }
    repository.deleteInBatch(list);
  }

  /**
   * 批量删除对象
   *
   * @param entities entities
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteBatch(List<E> entities) {
    repository.deleteInBatch(entities);
  }

  /**
   * 更新
   *
   * @param entity 更新参数
   * @return 更新的结果
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public E update(E entity,ID id) {
    E object = repository.getOne(id);
    //将object复制给entity 忽略的属性是有值的属性（将object复制到entity属性为null）
    BeanUtils.copyProperties(object, entity, getNoNullProperties(entity));
    repository.save(entity);
    return entity;
  }

  /**
   * 批量更新
   *
   * @param entities entities
   * @param ids
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateBatch(List<E> entities, ID[] ids) {
    List<E> list = new ArrayList<>();
    for (E entity : entities) {
      for (ID id : ids) {
        E object = repository.getOne(id);
        //注意不要导错包
        BeanUtils.copyProperties(object, entity, getNoNullProperties(entity));
        list.add(entity);
      }
    }
    repository.saveAll(list);
  }

  /**
   * 获取对象不为空的属性
   *
   * @param o 参数
   * @return 将目标源中不为空的字段取出
   */
  protected static String[] getNoNullProperties(Object o) {
    //获取对象的bean的包装类
    BeanWrapper bean = new BeanWrapperImpl(o);
    //获取属性（字段）的描述
    PropertyDescriptor[] descriptors = bean.getPropertyDescriptors();
    Set<String> set = new HashSet<>();
    for (PropertyDescriptor descriptor : descriptors) {
      Object value = bean.getPropertyValue(descriptor.getName());
      if (!Objects.equals(value, null)) set.add(descriptor.getName());
    }
    String[] result = new String[set.size()];
    return set.toArray(result);
  }

}
