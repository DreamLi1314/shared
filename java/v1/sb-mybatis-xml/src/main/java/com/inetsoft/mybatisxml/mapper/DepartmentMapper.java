package com.inetsoft.mybatisxml.mapper;

import com.inetsoft.mybatisxml.domain.Department;
import org.apache.ibatis.annotations.*;

public interface DepartmentMapper {
    /**
     * 插入一个 Department
     * @Options 添加一些额外的配置
     *  useGeneratedKeys: 指定启用主键返回
     *  keyProperty: 指定主键返回到 domain 对象的那个属性
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO t_department(department_name) VALUE(#{departmentName})")
    void insertDepartment(Department department);

    /**
     * 通过 id 查询部门信息.
     * @Results 和 @Result 定义一个指定 id 的 ResultMap 将返回结果按照指定规则进行映射.
     * @Param 注解处理参数名和引用名不一致的问题
     * @param id 部门 id
     * @return 指定部门 id 的部门信息
     */
    @Results(id="deptMap", value = {
            @Result(column = "department_name", property = "departmentName", javaType = String.class)
    })
    @Select("SELECT * FROM t_department where id=#{deptId}")
    Department selectDepartment(@Param("deptId") int id);
}
