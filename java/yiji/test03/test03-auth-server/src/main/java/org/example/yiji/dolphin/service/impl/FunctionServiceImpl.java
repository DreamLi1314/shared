package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.dao.primary.FunctionRepository;
import org.example.yiji.dolphin.model.VO.FunctionTreeVo;
import org.example.yiji.dolphin.model.primary.Function;
import org.example.yiji.dolphin.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function,String> implements FunctionService {

  @Autowired
  private FunctionRepository functionRepository;

  public List<Function> findByName(String name) {
    return functionRepository.findByName(name);
  }

  public PageResponse<Function> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<Function> result;
    if(str==null||"".equals(str)){
      result = functionRepository.findAll(pageable);
    }else {
      result = functionRepository.findAll(new Specification<Function>() {

        @Override
        public Predicate toPredicate(Root<Function> root, CriteriaQuery<?> cq,
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

  @Override
  public List<FunctionTreeVo> tree() {
    List<FunctionTreeVo> data = new ArrayList<>();
    List<Function> permissions = functionRepository.findByHead(true);
    for (Function pms : permissions) {
      data.add(new FunctionTreeVo(pms));
    }
    return data;
  }
}
