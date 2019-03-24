package com.inetsoft.mybatis.controllr;

import com.inetsoft.mybatis.domain.Department;
import com.inetsoft.mybatis.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable Integer id) {
        return departmentMapper.selectDepartment(id);
    }

    @GetMapping("/dept")
    public Department addDepartment(Department dept) {
        departmentMapper.insertDepartment(dept);

        return dept;
    }

    @GetMapping("/depts/{name}")
    public List<Department> getDepartmentsByName(@PathVariable String name) {
        return departmentMapper.selectDepartmentByName(name);
    }

}
