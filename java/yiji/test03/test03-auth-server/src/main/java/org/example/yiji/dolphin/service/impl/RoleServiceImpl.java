package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.org.examplemon.PageStaticField;
import org.example.yiji.dolphin.dao.primary.RoleRepository;
import org.example.yiji.dolphin.model.primary.Role;
import org.example.yiji.dolphin.service.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }

  public PageResponse<Role> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<Role> result;
    if(str==null||"".equals(str)){
      result = roleRepository.findAll(pageable);
    }else {
      result = roleRepository.findAll(new Specification<Role>() {

        @Override
        public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> cq,
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
