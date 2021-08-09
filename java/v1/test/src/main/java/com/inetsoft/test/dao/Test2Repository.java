package com.inetsoft.test.dao;

import com.inetsoft.test.domain.Test2Entity;
import com.inetsoft.test.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jack Li
 * @date 2021/7/31 10:57 上午
 * @description
 */
public interface Test2Repository extends JpaRepository<Test2Entity, Long>,
   JpaSpecificationExecutor<Test2Entity> {
}
