package com.geoviswtx.dao;

import com.geoviswtx.entity.DatasetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DatasetRepository extends JpaRepository<DatasetEntity, UUID>,
        JpaSpecificationExecutor<DatasetEntity>
{

    /**
     * 根据类型查询数据集
     * @param type 类型唯一标识
     * @return DatasetEntity
     */
    DatasetEntity findByType(String type);

}
