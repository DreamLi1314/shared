package club.javafamily.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import club.javafamily.entity.Dept;

@RestController
public class DeptControllerConsumer {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URL_PREFIX = "http://localhost:8001";

	@GetMapping("/consumer/dept")
	public boolean add(Dept dept) {
		return this.restTemplate.postForObject(URL_PREFIX + "/dept", dept, Boolean.class);
	}
	
	@GetMapping("/consumer/dept/{id}")
	public Dept get(@PathVariable("id") Long id) {
		return this.restTemplate.getForObject(URL_PREFIX + "/dept/" + id, Dept.class);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/dept/list")
	public List<Dept> list() {
		return this.restTemplate.getForObject(URL_PREFIX + "/dept/list", List.class);
	}
	
	@GetMapping("/consumer/dept/discovery")
	public Object getDiscovery() {
		return this.restTemplate.getForObject(URL_PREFIX + "/dept/discovery", Object.class);
	}
	
}
