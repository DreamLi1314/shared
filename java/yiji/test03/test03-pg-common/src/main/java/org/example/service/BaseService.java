package org.example.service;

import com.mlog.utils.common.*;
import com.mlog.utils.enums.QueryMatchType;
import org.example.vo.PageRequestVo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Jack Li
 * @date 2022/5/17 下午4:10
 * @description
 */
public class BaseService<ENTITY, ID> {

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
      S entity, PageRequestVo pageVo)
   {
      return _repository.findAll(Example.of(entity),
         PageRequest.of(pageVo.getPage(), pageVo.getSize()));
   }

   public JpaSpecificationExecutor<ENTITY> getSpecificationExecutor() {
      return (JpaSpecificationExecutor<ENTITY>) _repository;
   }

   public <S extends ENTITY> Page<S> findAllWithFilterPage(
      S entity, PageRequestVo pageVo)
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
      Specification<ENTITY> specification = (Specification<ENTITY>) (root, criteriaQuery, cb) ->
      {
         //增加筛选条件
         Predicate predicate = cb.conjunction();
         final List<Expression<Boolean>> expressions = predicate.getExpressions();

         if(queryRequest.getFilter() != null
            && !ObjectUtils.isEmpty(queryRequest.getFilter().getItems()))
         {
            for (FilterInfo.RequestFilterItemVo item
               : queryRequest.getFilter().getItems())
            {
               if(item.getMatchType() == QueryMatchType.FUZZY) {
                  // 模糊查询只支持模糊查询一个字段
                  expressions.add(cb.like(
                     root.get(item.getElem()), item.getValues()[0] + "%"));
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
                     expressions.add(cb.equal(root.get(item.getElem()),
                        item.getValues()[0]));
                  }
               }
            }
         }

         if(appendQueryFilter != null) {
            appendQueryFilter.accept(root, cb);
         }

         return predicate;
      };

      PageRequest pageRequest = null;

      if(queryRequest.getPage() != null && queryRequest.getSort() != null) {
         final PageQueryInfo pageInfo = queryRequest.getPage();
         final SortInfo sorInfo = queryRequest.getSort();

         pageRequest = PageRequest.of(
            pageInfo.getPage(), pageInfo.getSize(),
            Sort.by(Sort.Direction.fromString(
               sorInfo.getSortOption().name()), sorInfo.getSortCol()));
      }
      else if(queryRequest.getPage() != null) {
         final PageQueryInfo pageInfo = queryRequest.getPage();
         pageRequest = PageRequest.of(
            pageInfo.getPage(), pageInfo.getSize());
      }

      if(pageRequest != null) {
         return getSpecificationExecutor().findAll(
            specification, pageRequest);
      }

      return new PageImpl<>(getSpecificationExecutor()
         .findAll(specification));
   }
}
