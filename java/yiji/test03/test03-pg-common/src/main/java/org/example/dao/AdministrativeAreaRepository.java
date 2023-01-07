package org.example.dao;

import org.example.domain.AdministrativeAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jack Li
 * @date 2021/11/18 3:16 下午
 * @description
 */
public interface AdministrativeAreaRepository extends JpaRepository<AdministrativeAreaEntity, Long>,
   JpaSpecificationExecutor<AdministrativeAreaEntity>
{

}
