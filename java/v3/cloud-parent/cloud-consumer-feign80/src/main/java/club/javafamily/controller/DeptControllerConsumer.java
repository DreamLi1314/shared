package club.javafamily.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import club.javafamily.entity.Dept;
import club.javafamily.service.DeptFeignClientService;

@RestController
public class DeptControllerConsumer {

	@Autowired
	private DeptFeignClientService deptService;
	
	@GetMapping("/consumer/dept")
	public boolean add(Dept dept) {
		return this.deptService.add(dept);
	}
	
	@GetMapping("/consumer/dept/{id}")
	public Dept get(@PathVariable("id") Long id) {
		return this.deptService.get(id);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/dept/list")
	public List<Dept> list() {
		return this.deptService.list();
	}
	
}
