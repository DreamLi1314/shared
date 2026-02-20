package club.javafamily.service;

import java.util.List;

import org.springframework.stereotype.Component;

import club.javafamily.entity.Dept;
import feign.hystrix.FallbackFactory;

@Component
public class DeptFeignClientServiceFallbackFactory implements FallbackFactory<DeptFeignClientService> {

	@Override
	public DeptFeignClientService create(Throwable arg0) {
		return new DeptFeignClientService() {

			@Override
			public Dept get(Long id) {
				return new Dept().setDeptno(id)
						.setDname(id + " 不存在, 或服务已关闭, 请稍后再试.")
						.setDb_source("数据库中不存在对应信息.");
			}
			
			@Override
			public boolean add(Dept dept) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public List<Dept> list() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

}
