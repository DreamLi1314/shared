package com.inetsoft.test.service;

import com.inetsoft.test.dao.TestRepository;
import com.inetsoft.test.domain.TestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestService {

   private final TestRepository repository;

   public TestService(TestRepository repository) {
      this.repository = repository;
   }

   @Transactional(transactionManager = "mainTransactionManager")
   public void saveAll(List<TestEntity> entities) {
      repository.saveAll(entities);
   }
}

