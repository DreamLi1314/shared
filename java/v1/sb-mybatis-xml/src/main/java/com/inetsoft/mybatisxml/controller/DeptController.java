package com.inetsoft.mybatisxml.controller;

import com.inetsoft.mybatisxml.domain.Department;
import com.inetsoft.mybatisxml.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Autowired
    private DepartmentMapper mapper;

    @GetMapping("dept/{id}")
    public Department getDepartment(@PathVariable Integer id) {
        return mapper.selectDepartment(id);
    }


}
