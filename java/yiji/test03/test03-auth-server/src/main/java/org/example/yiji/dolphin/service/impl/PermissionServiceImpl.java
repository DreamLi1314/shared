package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.dao.primary.PermissionRepository;
import org.example.yiji.dolphin.model.primary.Permission;
import org.example.yiji.dolphin.service.PermissionService;
import java.util.ArrayList;
import java.util.List;
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
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long> implements PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  public List<Permission> findByMethodName(String methodName) {
    return permissionRepository.findByMethodName(methodName);
  }

  public PageResponse<Permission> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<Permission> result;
    if(str==null||"".equals(str)){
      result = permissionRepository.findAll(pageable);
    }else {
      result = permissionRepository.findAll(new Specification<Permission>() {

        @Override
        public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
          ArrayList<Predicate> list = new ArrayList<>();
          list.add(cb.like(root.get("systemName").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("moduleName").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("functionName").as(String.class),"%"+str+"%"));
          Predicate[] predicate = new Predicate[list.size()];
          return cb.or(list.toArray(predicate));
        }
      },pageable);
    }
    return new PageResponse<>(result);
  }

}
