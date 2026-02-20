package com.geoviswtx.dao;

import com.geoviswtx.entity.GridDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface GridDataRepository extends JpaRepository<GridDataEntity, UUID>,
        JpaSpecificationExecutor<GridDataEntity>
{
    /**
     * 根据 meta 及 分区编号查询数据
     * @param metaId meta id
     * @param chuckIdx 分区编号
     * @return GridDataEntity
     */
    GridDataEntity findByMetaIdAndChuckIdx(UUID metaId, Integer chuckIdx);
}

