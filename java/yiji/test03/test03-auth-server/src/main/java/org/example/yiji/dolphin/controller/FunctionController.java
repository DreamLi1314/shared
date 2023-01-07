package org.example.yiji.dolphin.controller;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.VO.FunctionTreeVo;
import org.example.yiji.dolphin.model.VO.FunctionVo;
import org.example.yiji.dolphin.model.primary.Permission;
import org.example.yiji.dolphin.service.FunctionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能
 */
@RestController
@RequestMapping("/function")
@Validated
public class FunctionController {

  @Autowired
  private FunctionService functionService;

//  /**
//   * 新增前端功能
//   * @param po
//   */
//  @PostMapping("/add")
//  public void add(@Validated @RequestBody PermissionAddPo po) {
//    Permission entity = new Permission();
//    BeanUtils.copyProperties(po,entity);
//    entity.setLevel(3);
//    entity.setType(2);
//    Permission permission = permissionService.selectByKey(po.getPermissionId());
//    entity.setParentPermission(permission);
//    entity.setSystemName(permission.getSystemName());
//    entity.setModuleName(permission.getModuleName());
//    permissionService.save(entity);
//  }
//
//  /**
//   * 通过id删除
//   * @param id
//   */
//  @DeleteMapping("/delete")
//  public void delete(@RequestParam Long id) {
//    Permission permission = permissionService.selectByKey(id);
//    if(permission.getType()!=2){
//      throw new CommonException(ResultCode.FAIL,"无权删除此条数据");
//    }
//    if(!permission.getRoles().isEmpty()){
//      throw new CommonException(ResultCode.VALIDATE_ERROR,"请先变更该菜单下的角色");
//    }
//    if(!permission.getChildPermissions().isEmpty()){
//      throw new CommonException(ResultCode.VALIDATE_ERROR,"请先变更该菜单下的子菜单");
//    }
//    permissionService.deleteById(id);
//  }
//
//  /**
//   * 修改
//   * @param po
//   */
//  @PutMapping("/update")
//  public void update(@Validated @RequestBody PermissionUpdatePo po) {
//    Permission data = permissionService.selectByKey(po.getId());
//    if(data.getType()!=2){
//      throw new CommonException(ResultCode.FAIL,"无权修改此条数据");
//    }
//    Permission entity = new Permission();
//    BeanUtils.copyProperties(po,entity);
//    if(po.getPermissionId()!=null){
//      Permission permission = permissionService.selectByKey(po.getPermissionId());
//      entity.setParentPermission(permission);
//      entity.setSystemName(permission.getSystemName());
//      entity.setModuleName(permission.getModuleName());
//    }
//    permissionService.update(entity,entity.getId());
//  }

//  /**
//   * 通过id查询
//   * @param id
//   * @return
//   */
//  @GetMapping("/id")
//  public PermissionVo findById(@RequestParam Long id) {
//    return data2vo(permissionService.selectByKey(id));
//  }
//
//  /**
//   * 查询全部
//   * @return
//   */
//  @GetMapping("/all")
//  public List<PermissionVo> findAll() {
//    return data2vo(permissionService.selectAll());
//  }
//
//  /**
//   * 分页查询
//   * @param str
//   * @param pageNum
//   * @param pageSize
//   * @return
//   */
//  @GetMapping("/search")
//  public PageResponse<PermissionVo> search(@RequestParam(required = false) String str,
//      @Min(value = 1,message = "请输入≥1的页码值") @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//    return data2vo(permissionService.findBySearch(str,pageNum,pageSize));
//  }

  /**
   * 获取权限树状结构
   * @return
   */
  @GetMapping("/tree")
  public List<FunctionTreeVo> tree() {
    return functionService.tree();
  }

  private FunctionVo data2vo(Permission data){
    FunctionVo vo = new FunctionVo();
    BeanUtils.copyProperties(data,vo);
    return vo;
  }

  private List<FunctionVo> data2vo(List<Permission> data){
    ArrayList<FunctionVo> list = new ArrayList<>();
    for (Permission permission : data) {
      list.add(data2vo(permission));
    }
    return list;
  }

  private PageResponse<FunctionVo> data2vo(PageResponse<Permission> page){
    ArrayList<FunctionVo> list = new ArrayList<>();
    List<Permission> data = page.getContent();
    for (Permission permission : data) {
      list.add(data2vo(permission));
    }
    return new PageResponse<>(page,list);
  }

}
