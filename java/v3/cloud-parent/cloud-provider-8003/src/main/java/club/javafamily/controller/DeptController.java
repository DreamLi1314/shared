package club.javafamily.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import club.javafamily.entity.Dept;
import club.javafamily.service.DeptService;

@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;
	
	@Autowired
	private DiscoveryClient client;
	
	@PostMapping("/dept")
	public boolean add(@RequestBody Dept dept) {
		return deptService.add(dept);
	}
	
	@GetMapping("/dept/{id}")
	public Dept get(@PathVariable("id") Long id) {
		return deptService.get(id);
	}
	
	@GetMapping("/dept/list")
	public List<Dept> list() {
		return deptService.list();
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/dept/discovery")
	public DiscoveryClient discovery() {
		List<String> services = client.getServices();
		
		System.out.println("=========services========" + services);
		
		List<ServiceInstance> instances = client.getInstances("cloud-dept");
		
		for (ServiceInstance serviceInstance : instances) {
			System.out.println("===" + serviceInstance.getServiceId() + "==getHost==" + serviceInstance.getHost()
				+ "====getPort=========" + serviceInstance.getPort()
				+ "====getUri===" + serviceInstance.getUri()
			);
		}
		
		return client;
	}
	
}
