package com.dreamli.crud.controller;

import com.dreamli.crud.dao.DepartmentDao;
import com.dreamli.crud.dao.EmployeeDao;
import com.dreamli.crud.entities.Department;
import com.dreamli.crud.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    private final DepartmentDao departmentDao;
    private final EmployeeDao employeeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
}
