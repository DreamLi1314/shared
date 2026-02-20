package com.geoviswtx.dao.pg;

import com.geoviswtx.entity.pg.GridMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.*;

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
     * @return List
     */
    List<GridMetaEntity> findListByBaseTimeAndDsIdAndElemAndZOrderByForecastTime(
       Date baseTime, Long dsId, String elem, double z);

    /**
     * 根据数据源 id 、要素名、高度层查询数据 meta 信息
     *
     * @param baseTime 起报时间
     * @param dsId     数据源id
     * @param elem     要素名
     * @param z        高度层
     * @param start    开始时间
     * @param end      结束时间
     * @return List
     */
    List<GridMetaEntity> findListByBaseTimeAndDsIdAndElemAndZAndForecastTimeAfterAndForecastTimeBeforeOrderByForecastTime(
       Date baseTime, Long dsId, String elem, double z, Date start, Date end);
}

