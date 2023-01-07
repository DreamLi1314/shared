package org.example.yiji.dolphin.controller;

import org.example.mlog.utils.tree.TreeNodeModel;
import org.example.yiji.dolphin.org.examplemon.*;
import org.example.yiji.dolphin.model.PO.DepartmentAddPo;
import org.example.yiji.dolphin.model.PO.DepartmentUpdatePo;
import org.example.yiji.dolphin.model.VO.DepartmentVo;
import org.example.yiji.dolphin.model.primary.Department;
import org.example.yiji.dolphin.model.primary.User;
import org.example.yiji.dolphin.service.DepartmentService;
import org.example.yiji.dolphin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.*;

/**
 * 部门
 */
@RestController
@RequestMapping("/department")
@Validated
public class DepartmentController {

  private final DepartmentService departmentService;
  private final UserService userService;

  public DepartmentController(UserService userService,
                              DepartmentService departmentService)
  {
    this.userService = userService;
    this.departmentService = departmentService;
  }

  /**
   * 新增
   * @param po
   */
  @PostMapping("/add")
  public void add(@Validated @RequestBody DepartmentAddPo po) {
    Department entity = new Department();
    BeanUtils.copyProperties(po,entity);
    if(po.getDepartmentId()!=null){
      Department department = departmentService.selectByKey(po.getDepartmentId());
      entity.setParentDepartment(department);
    }
    departmentService.save(entity);
  }

  /**
   * 通过id删除
   * @param id
   */
  @DeleteMapping("/delete")
  public void delete(@RequestParam Long id) {
    Department department = departmentService.selectByKey(id);
    if(!department.getUsers().isEmpty()){
      throw new CommonException(ResultCode.VALIDATE_ERROR,"请先变更该部门下的用户");
    }
    if(!department.getChildDepartments().isEmpty()){
      throw new CommonException(ResultCode.VALIDATE_ERROR,"请先变更该部门下的子部门");
    }
    departmentService.deleteById(id);
  }

  /**
   * 修改
   * @param po
   */
  @PutMapping("/update")
  public void update(@Validated @RequestBody DepartmentUpdatePo po) {
    Department entity = new Department();
    BeanUtils.copyProperties(po,entity);
    if(po.getDepartmentId()!=null){
      Department department = departmentService.selectByKey(po.getDepartmentId());
      entity.setParentDepartment(department);
    }
    departmentService.update(entity,entity.getId());
  }

  /**
   * 通过id查询
   * @param id
   * @return
   */
  @GetMapping("/id")
  public DepartmentVo findById(@RequestParam Long id) {
    return data2vo(departmentService.selectByKey(id));
  }

  /**
   * 查询全部
   * @return
   */
  @GetMapping(value = "/all")
  public List<DepartmentVo> getAll() {
    return data2vo(departmentService.selectAll());
  }

  @GetMapping(value = "/with-user/tree")
  public TreeNodeModel getTreeWithUser() {
    return TreeNodeModel.builder()
       .label("Root")
       .expanded(true)
       .leaf(false)
       .path("/")
       .children(getDeptNodes())
       .build();
  }

  private List<TreeNodeModel> getDeptNodes() {
    final List<DepartmentVo> departmentVos = getAll();
    List<TreeNodeModel> nodes = new ArrayList<>();

    for (DepartmentVo vo : departmentVos) {
      final List<User> users = userService.findAllByDept(vo.getId());
      final List<TreeNodeModel> userNodes = getUserNodes(users);

      nodes.add(TreeNodeModel.builder()
         .label(vo.getName())
         .value(vo.getId() + "")
         .leaf(CollectionUtils.isEmpty(userNodes))
         .type("dept")
         .expanded(true)
         .children(userNodes)
         .build());
    }

    // 添加没有部门的用户
    final List<User> noDeptUsers = userService.findAllByDept(null);

    if(!CollectionUtils.isEmpty(noDeptUsers)) {
      final List<TreeNodeModel> userNodes = getUserNodes(noDeptUsers);

      nodes.add(TreeNodeModel.builder()
         .label("其他")
         .value(null)
         .leaf(CollectionUtils.isEmpty(userNodes))
         .type("dept")
         .expanded(true)
         .children(userNodes)
         .build());
    }

    return nodes;
  }

  private List<TreeNodeModel> getUserNodes(List<User> users) {
    List<TreeNodeModel> nodes = new ArrayList<>();

    if(CollectionUtils.isEmpty(users)) {
      return nodes;
    }

    for (User user : users) {
      nodes.add(TreeNodeModel.builder()
         .label(user.getNickName() + "(" + user.getUsername() + ")")
         .value(user.getId() + "")
         .type("user")
         .leaf(true)
         .build());
    }

    return nodes;
  }


  /**
   * 分页查询
   * @param str
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/search")
  public PageResponse<DepartmentVo> search(@RequestParam(required = false) String str,
      @Min(value = 1,message = "请输入≥1的页码值") @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    return data2vo(departmentService.findBySearch(str,pageNum,pageSize));
  }

  private DepartmentVo data2vo(Department data){
    DepartmentVo vo = new DepartmentVo();
    BeanUtils.copyProperties(data,vo);
    if(data.getParentDepartment()!=null){
      vo.setDepartmentName(data.getParentDepartment().getName());
    }
    return vo;
  }

  private List<DepartmentVo> data2vo(List<Department> data){
    ArrayList<DepartmentVo> list = new ArrayList<>();
    for (Department department : data) {
      list.add(data2vo(department));
    }
    return list;
  }

  private PageResponse<DepartmentVo> data2vo(PageResponse<Department> page){
    ArrayList<DepartmentVo> list = new ArrayList<>();
    List<Department> data = page.getContent();
    for (Department department : data) {
      list.add(data2vo(department));
    }
    return new PageResponse<>(page,list);
  }

}
