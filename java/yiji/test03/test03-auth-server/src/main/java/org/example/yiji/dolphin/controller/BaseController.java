package org.example.yiji.dolphin.controller;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.BaseEntity;
import org.example.yiji.dolphin.service.BaseService;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wanglin
 * @date 2020/5/11 16:55
 */
@Validated
public abstract class BaseController<E extends BaseEntity> {

  @Autowired
  private BaseService<E,Long> baseService;

  /**
   * 新增
   * @param entity
   */
  @PostMapping("/add")
  public void add(@Validated @RequestBody E entity) {
    baseService.save(entity);
  }

  /**
   * 修改
   * @param entity
   */
  @PutMapping("/update")
  public void update(@Validated @RequestBody E entity) {
    baseService.update(entity,entity.getId());
  }

  /**
   * 通过id查询
   * @param id
   * @return
   */
  @GetMapping(value = "/byId")
  public E getById(@RequestParam Long id) {
    return baseService.selectByKey(id);
  }

  /**
   * 通过id删除
   * @param id
   */
  @DeleteMapping(value = "/deleteById")
  public void deleteById(@RequestParam Long id) {
    baseService.deleteById(id);
  }

  /**
   * 查询全部
   * @return
   */
  @GetMapping(value = "/all")
  public List<E> getAll() {
    return baseService.selectAll();
  }

  /**
   * 分页查询
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping(value = "/page")
  public PageResponse<E> getByPage(@Min(value = 1,message = "请输入≥1的页码值") @RequestParam int pageNum, @RequestParam int pageSize) {
    return baseService.page(pageNum, pageSize);
  }

}
