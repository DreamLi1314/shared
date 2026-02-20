package club.javafamily.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import club.javafamily.entity.Dept;

//@FeignClient("CLOUD-DEPT")
@FeignClient(value = "CLOUD-DEPT", fallbackFactory = DeptFeignClientServiceFallbackFactory.class)
public interface DeptFeignClientService {
	
	@PostMapping("/dept")
	boolean add(@RequestBody Dept dept);

	@GetMapping("/dept/{id}")
	Dept get(@PathVariable("id") Long id);

	@GetMapping("/dept/list")
	List<Dept> list();
}
