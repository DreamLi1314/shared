package com.dreamli.crud.controller;

import com.dreamli.crud.dao.DepartmentDao;
import com.dreamli.crud.dao.EmployeeDao;
import com.dreamli.crud.entities.Department;
import com.dreamli.crud.entities.Employee;
import com.dreamli.crud.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    public EmployeeController(EmployeeDao employeeDao,
                              DepartmentDao departmentDao) {
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
    }

    /**
     * 获取所有的用户, 并跳转到 Customers page 显示所有 User 信息
     * @return target page uri
     */
    @GetMapping("/emps")
    public String getUsers(Map<String, Object> params) {
        Collection<Employee> emps = employeeDao.getAll();

        params.put("emps", emps);

        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Model model) {
        Collection<Department> departments =
                departmentDao.getDepartments();
        // 将所有的部门信息放入到域对象中
        model.addAttribute("depts", departments);

        // 跳转到添加员工页面
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee emp) {
        LOGGER.debug("add employee: " + emp);
        // 校验数据, 添加员工
        if(!StringUtils.isEmpty(emp.getLastName()) && !StringUtils.isEmpty(emp.getDepartment())) {
            employeeDao.save(emp);
        }

        // 跳转到员工列表页面显示所有员工
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String getEditEmp(@PathVariable Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("editEmp", employee);

        return toAddPage(model);
    }

    /**
     * 更新员工信息
     * @param emp 封装了最新员工信息的 Employee 对象
     * @return 返回员工列表页面
     * @throws Exception 如果没有指定员工 ID, 则抛出 UserNotFoundException 异常
     */
    @PutMapping("/emp")
    public String edit(Employee emp) throws Exception {
        if(emp.getId() != null) {
            employeeDao.save(emp);
        }
        else {
            throw new UserNotFoundException("you edit employee is not found.");
        }

        return "redirect:/emps";
    }

    /**
     * 删除指定 ID 的员工信息.
     * @param id 员工 ID
     * @return 返回员工列表页面
     */
    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable Integer id) {
        employeeDao.delete(id);

        return "redirect:/emps";
    }

    private final DepartmentDao departmentDao;
    private final EmployeeDao employeeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
}
