package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.org.examplemon.PageStaticField;
import org.example.yiji.dolphin.dao.primary.DepartmentRepository;
import org.example.yiji.dolphin.model.primary.Department;
import org.example.yiji.dolphin.service.DepartmentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department,Long> implements
        DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  public Department findByName(String name) {
    return departmentRepository.findByName(name);
  }

  public PageResponse<Department> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<Department> result;
    if(str==null||"".equals(str)){
      result = departmentRepository.findAll(pageable);
    }else {
      result = departmentRepository.findAll(new Specification<Department>() {

        @Override
        public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
          ArrayList<Predicate> list = new ArrayList<>();
          list.add(cb.like(root.get("name").as(String.class),"%"+str+"%"));
          Predicate[] predicate = new Predicate[list.size()];
          return cb.or(list.toArray(predicate));
        }
      },pageable);
    }
    return new PageResponse<>(result);
  }
}
