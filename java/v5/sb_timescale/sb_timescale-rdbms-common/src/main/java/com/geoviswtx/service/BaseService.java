package com.geoviswtx.service;

import com.geoviswtx.common.common.*;
import com.geoviswtx.common.date.DateUtil;
import com.geoviswtx.common.date.DateUtils;
import com.geoviswtx.common.enums.QueryMatchType;
import com.geoviswtx.domain.BaseEntity;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Jack Li
 * @date 2022/5/17 下午4:10
 * @description
 */
public class BaseService<ENTITY extends BaseEntity, ID> {

   protected final JpaRepository<ENTITY, ID> _repository;

   public BaseService(JpaRepository<ENTITY, ID> _repository) {
      this._repository = _repository;
   }

   public List<ENTITY> findAll() {
      return _repository.findAll();
   }

   public<S extends ENTITY> List<S> findAll(Example<S> example) {
      return _repository.findAll(example);
   }

   public ENTITY findById(ID id) {
      return _repository.findById(id).orElse(null);
   }

   public ENTITY findByIdElseThrow(ID id) {
      return _repository.findById(id)
         .orElseThrow(() -> new MessageException("实体不存在, ID: " + id));
   }

   public List<ENTITY> findAllByIds(Iterable<ID> ids) {
      return _repository.findAllById(ids);
   }

   public <S extends ENTITY> S save(S entity) {
      return _repository.save(entity);
   }

   public long count() {
      return _repository.count();
   }

   public <S extends ENTITY> List<S> saveAll(Iterable<S> entities) {
      return _repository.saveAll(entities);
   }

   public <S extends ENTITY> void deleteAll(Iterable<S> entities) {
      _repository.deleteAll(entities);
   }

   public void deleteAllInBatch() {
      _repository.deleteAllInBatch();
   }

   public void delete(ENTITY entity) {
      _repository.delete(entity);
   }

   public void deleteById(ID id) {
      _repository.deleteById(id);
   }

   public <S extends ENTITY> Page<S> findAll(
      Example<S> example, Pageable pageable)
   {
      return _repository.findAll(example, pageable);
   }

   public <S extends ENTITY> Page<S> findAllWithPage(
      S entity, PageQueryInfo pageVo)
   {
      return _repository.findAll(Example.of(entity),
         PageRequest.of(pageVo.getPage(), pageVo.getSize()));
   }

   public JpaSpecificationExecutor<ENTITY> getSpecificationExecutor() {
      return (JpaSpecificationExecutor<ENTITY>) _repository;
   }

   public <S extends ENTITY> Page<S> findAllWithFilterPage(
      S entity, PageQueryInfo pageVo)
   {
      return _repository.findAll(Example.of(entity),
         PageRequest.of(pageVo.getPage(), pageVo.getSize()));
   }

   public Page<ENTITY> findAllWithQueryRequest(@NonNull QueryRequest queryRequest)
   {
      return findAllWithQueryRequest(queryRequest, null);
   }

   public Page<ENTITY> findAllWithQueryRequest(
      @NonNull QueryRequest queryRequest,
      BiConsumer<Root<ENTITY>, CriteriaBuilder> appendQueryFilter)
   {
      Specification<ENTITY> specification = (root, criteriaQuery, cb) ->
      {
         //增加筛选条件
         Predicate predicate = cb.conjunction();
         final List<Expression<Boolean>> expressions = predicate.getExpressions();
         Date start = queryRequest.getStart();
         Date end = queryRequest.getEnd();

         if(start != null) {
            expressions.add(cb.greaterThanOrEqualTo(root.get("createTime"), start));
         }

         if(end != null) {
            expressions.add(cb.lessThanOrEqualTo(root.get("createTime"), end));
         }

         if(queryRequest.getFilter() != null
            && !ObjectUtils.isEmpty(queryRequest.getFilter().getItems()))
         {
            for (FilterInfo.RequestFilterItemVo item
               : queryRequest.getFilter().getItems())
            {
               if(item.getMatchType() == QueryMatchType.FUZZY) {
                  if (StringUtils.isBlank(item.getValues()[0].toString())){
                     continue;
                  }
                  // 模糊查询只支持模糊查询一个字段
                  expressions.add(cb.like(
                          cb.lower(root.get(item.getElem())), "%" + item.getValues()[0].toString().toLowerCase() + "%"
                  ));

               }
               else {
                  // 只有多个值才用 in
                  if(item.getValues().length > 1) {
                     final CriteriaBuilder.In<Object> in
                        = cb.in(root.get(item.getElem()));

                     for (Object value : item.getValues()) {
                        in.value(value);
                     }

                     expressions.add(cb.and(in));
                  }
                  else {
                      if (StringUtils.isBlank(item.getValues()[0].toString())){
                         continue;
                      }
                     if(root.get(item.getElem()).getJavaType() == Date.class
                             && item.getValues()[0] instanceof String)
                     {
                        expressions.add(cb.equal(root.get(item.getElem()),
                                DateUtil.parseSystemNormalDateTime((String) item.getValues()[0])));
                     }
                     else if(root.get(item.getElem()).getJavaType().isEnum()
                             && item.getValues()[0] instanceof String)
                     {
                        Class clazz = root.get(item.getElem()).getJavaType();

                        expressions.add(cb.equal(root.get(item.getElem()),
                                EnumUtils.getEnum(clazz, (String) item.getValues()[0])));
                     }
                     else {
                        expressions.add(cb.equal(root.get(item.getElem()),
                                item.getValues()[0]));
                     }
                  }
               }
            }
         }

         if(appendQueryFilter != null) {
            appendQueryFilter.accept(root, cb);
         }
         criteriaQuery.orderBy(cb.desc(root.get("createTime")));
         return predicate;
      };

      PageRequest pageRequest = null;
      final PageQueryInfo pageInfo = queryRequest.getPage();
      int page = pageInfo.getPage() - 1;

      if(queryRequest.getPage() != null && queryRequest.getSort() != null) {
         final SortInfo sorInfo = queryRequest.getSort();

         pageRequest = PageRequest.of(page, pageInfo.getSize(),
            Sort.by(Sort.Direction.fromString(
               sorInfo.getSortOption().name()), sorInfo.getSortCol()));
      }
      else if(queryRequest.getPage() != null) {
         pageRequest = PageRequest.of(page, pageInfo.getSize());
      }

      if(pageRequest != null) {
         return getSpecificationExecutor().findAll(
            specification, pageRequest);
      }

      return new PageImpl<>(getSpecificationExecutor()
         .findAll(specification));
   }

   /**
    * 根据全部数据组织分页数据
    * @param totalContent 全部数据
    * @param pageable 分页信息
    * @param <T> 实体
    * @return page
    */
   public static <T> PageImpl<T> of(List<T> totalContent, Pageable pageable) {
      int startIndex = pageable.getPageNumber() * pageable.getPageSize();
      final int endIndex = startIndex + pageable.getPageSize();

      List<T> content = new ArrayList<>();

      if(startIndex < totalContent.size()) {
         content = totalContent.subList(startIndex,
            Math.min(endIndex, totalContent.size()));
      }

      return new PageImpl<>(content, pageable, totalContent.size());
   }
}
