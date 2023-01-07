package org.example.yiji.dolphin.controller;

import org.example.yiji.dolphin.org.examplemon.CommonException;
import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.org.examplemon.ResultCode;
import org.example.yiji.dolphin.model.primary.Function;
import org.example.yiji.dolphin.model.PO.RoleAddPo;
import org.example.yiji.dolphin.model.PO.RoleUpdatePo;
import org.example.yiji.dolphin.model.primary.Role;
import org.example.yiji.dolphin.service.FunctionService;
import org.example.yiji.dolphin.service.RoleService;
import org.example.yiji.dolphin.model.VO.RoleVo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Min;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色
 */
@RestController
@RequestMapping("/role")
@Validated
public class RoleController {

  @Autowired
  RoleService roleService;
  @Autowired
  FunctionService functionService;

  /**
   * 新增
   * @param po
   */
  @PostMapping("/add")
  public void add(@Validated @RequestBody RoleAddPo po) {
    Role entity = new Role();
    BeanUtils.copyProperties(po,entity);
    if(po.getFunctionIds()!=null&&po.getFunctionIds().size()>0){
      HashSet<Function> functions = new HashSet<>();
      for (String functionId : po.getFunctionIds()) {
        Function function = functionService.selectByKey(functionId);
        functions.add(function);
      }
      entity.setFunctions(functions);
    }
    roleService.save(entity);
  }

  /**
   * 通过id删除
   * @param id
   */
  @DeleteMapping("/delete")
  public void delete(@RequestParam Long id) {
    Role role = roleService.selectByKey(id);
    if(!role.getUsers().isEmpty()){
      throw new CommonException(ResultCode.VALIDATE_ERROR,"请先变更该角色下的用户");
    }
    roleService.deleteById(id);
  }

  /**
   * 修改
   * @param po
   */
  @PutMapping("/update")
  public void update(@Validated @RequestBody RoleUpdatePo po) {
    Role entity = new Role();
    BeanUtils.copyProperties(po,entity);
    if(po.getFunctionIds()!=null&&po.getFunctionIds().size()>0){
      HashSet<Function> functions = new HashSet<>();
      for (String functionId : po.getFunctionIds()) {
        Function function = functionService.selectByKey(functionId);
        functions.add(function);
      }
      entity.setFunctions(functions);
    }
    roleService.update(entity,entity.getId());
  }

  /**
   * 通过id查询
   * @param id
   * @return
   */
  @GetMapping("/id")
  public RoleVo findById(@RequestParam Long id) {
    return data2vo(roleService.selectByKey(id));
  }

  /**
   * 查询全部
   * @return
   */
  @GetMapping("/all")
  public List<RoleVo> findAll() {
    return data2vo(roleService.selectAll());
  }

  /**
   * 分页查询
   * @param str
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/search")
  public PageResponse<RoleVo> search(@RequestParam(required = false) String str,
      @Min(value = 1,message = "请输入≥1的页码值") @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    return data2vo(roleService.findBySearch(str,pageNum,pageSize));
  }

  private RoleVo data2vo(Role data){
    RoleVo vo = new RoleVo();
    BeanUtils.copyProperties(data,vo);
    Set<Function> set = new HashSet<>();
    for (Function function : data.getFunctions()) {
      set.add(function);
    }
    vo.setFunctions(set);
    return vo;
  }

  private List<RoleVo> data2vo(List<Role> data){
    ArrayList<RoleVo> list = new ArrayList<>();
    for (Role role : data) {
      list.add(data2vo(role));
    }
    return list;
  }

  private PageResponse<RoleVo> data2vo(PageResponse<Role> page){
    ArrayList<RoleVo> list = new ArrayList<>();
    List<Role> data = page.getContent();
    for (Role role : data) {
      list.add(data2vo(role));
    }
    return new PageResponse<>(page,list);
  }

}
