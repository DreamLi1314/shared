package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.org.examplemon.PageStaticField;
import org.example.yiji.dolphin.dao.primary.SysLogRepository;
import org.example.yiji.dolphin.model.primary.SysLog;
import org.example.yiji.dolphin.service.SysLogService;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * @author wanglin
 * @date 2020/5/11 19:44
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog,String> implements SysLogService {

  @Autowired
  private SysLogRepository sysLogRepository;

  public PageResponse<SysLog> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<SysLog> result;
    if(str==null||"".equals(str)){
      result = sysLogRepository.findAll(pageable);
    }else {
      result = sysLogRepository.findAll(new Specification<SysLog>() {

        @Override
        public Predicate toPredicate(Root<SysLog> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
          ArrayList<Predicate> list = new ArrayList<>();
          list.add(cb.like(root.get("username").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("url").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("type").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("ip").as(String.class),"%"+str+"%"));
          Predicate[] predicate = new Predicate[list.size()];
          return cb.or(list.toArray(predicate));
        }
      },pageable);
    }
    return new PageResponse<>(result);
  }

}
