package club.javafamily.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import club.javafamily.entity.Dept;
import club.javafamily.service.DeptService;

@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;
	
	@GetMapping("/dept/{id}")
	@HystrixCommand(fallbackMethod = "processHystrixGet")
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = deptService.get(id);
		
		if(dept == null) {
			throw new RuntimeException(id + " 不存在.");
		}
		
		return dept;
	}
	
	@SuppressWarnings("unused")
	private Dept processHystrixGet(Long id) {
		Dept dept = new Dept().setDeptno(id)
				.setDname(id + " 没有对应的信息.")
				.setDb_source(id + " isn't exist in database");
		
		return dept;
	}
	
	@GetMapping("/dept/list")
	public List<Dept> list() {
		return deptService.list();
	}
	
}
