package club.javafamily.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import club.javafamily.entity.Dept;

@Mapper
public interface DeptDao {
	boolean add(Dept dept);
	
	Dept findById(Long deptno);
	
	List<Dept> findAll();
}
