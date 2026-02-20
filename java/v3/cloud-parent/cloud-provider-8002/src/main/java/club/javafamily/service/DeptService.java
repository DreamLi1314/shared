package club.javafamily.service;

import java.util.List;

import club.javafamily.entity.Dept;

public interface DeptService {
	boolean add(Dept dept);

	Dept get(Long id);

	List<Dept> list();
}
