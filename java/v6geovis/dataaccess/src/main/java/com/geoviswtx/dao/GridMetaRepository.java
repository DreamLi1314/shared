package com.geoviswtx.dao;

import com.geoviswtx.entity.GridMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GridMetaRepository extends JpaRepository<GridMetaEntity, UUID>,
        JpaSpecificationExecutor<GridMetaEntity>
{
    /**
     * 根据数据源 id 、要素名、高度层查询数据 meta 信息
     *
     * @param baseTime 起报时间
     * @param dsId     数据源id
     * @param elem     要素名
     * @param z        高度层
     * @return GridMetaEntity
     */
    List<GridMetaEntity> findListByQbsjAndDatasetIdAndFeatureAndZOrderByYbsk(
            Date baseTime, UUID dsId, String elem, double z);
}

