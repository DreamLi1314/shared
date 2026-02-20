package com.geoviswtx.dao.pg;

import com.geoviswtx.entity.pg.DatasetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DatasetRepository extends JpaRepository<DatasetEntity, Long>,
        JpaSpecificationExecutor<DatasetEntity>
{

    /**
     * 根据类型查询数据集
     * @param type 类型唯一标识
     * @return DatasetEntity
     */
    DatasetEntity findByType(String type);

}
