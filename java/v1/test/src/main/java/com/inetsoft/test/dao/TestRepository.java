package com.inetsoft.test.dao;

import com.inetsoft.test.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestRepository extends JpaRepository<TestEntity, Long>,
   JpaSpecificationExecutor<TestEntity>
{
}
