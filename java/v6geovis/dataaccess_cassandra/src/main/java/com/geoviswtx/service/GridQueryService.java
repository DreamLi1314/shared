package com.geoviswtx.service;

import com.geoviswtx.dao.pg.DatasetRepository;
import com.geoviswtx.dao.cassandra.GridDataRepository;
import com.geoviswtx.dao.pg.GridMetaRepository;
import com.geoviswtx.entity.cassandra.GridDataEntity;
import com.geoviswtx.entity.cassandra.GridDataKey;
import com.geoviswtx.entity.pg.DatasetEntity;
import com.geoviswtx.entity.pg.GridMetaEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class GridQueryService {

    private final GridDataRepository dataRepository;
    private final GridMetaRepository metaRepository;
    private final DatasetRepository datasetRepository;

    public GridQueryService(GridDataRepository dataRepository,
                            GridMetaRepository metaRepository,
                            DatasetRepository datasetRepository)
    {
        this.dataRepository = dataRepository;
        this.metaRepository = metaRepository;
        this.datasetRepository = datasetRepository;
    }

    /**
     * 穿透查询测试
     *
     * @param baseTime    起报时间
     * @param datasetType 数据集名称
     * @param elem        要素名
     * @param lat         纬度
     * @param lon         经度
     * @param z           高度
     * @return
     */
    public List<Double> queryFuture24Hour(Date baseTime,
                                          String datasetType,
                                          String elem,
                                          double lat,
                                          double lon,
                                          double z)
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ZoneId zoneId = TimeZone.getTimeZone("GMT+08:00").toZoneId();

        // 获取 ds
        DatasetEntity dataset = datasetRepository.findByType(datasetType);

        int gridRowIndex = (int) Math.ceil((dataset.getStartY() - lat) / dataset.getIncrementY());
        int gridColumnIndex = (int) Math.floor(lon / dataset.getIncrementX());

        int gridIdx = gridRowIndex * dataset.getCountX() + gridColumnIndex;
        int chuckIdx = (int) Math.floor(gridIdx * 1.0 / dataset.getChuckNumber());
        int dataIdx = gridIdx % dataset.getChuckNumber();

        final Calendar instance = Calendar.getInstance();
        instance.setTime(baseTime);
        instance.add(Calendar.HOUR_OF_DAY, 24);

        // 获取 meta
        List<GridMetaEntity> metaList
                = metaRepository.findListByBaseTimeAndDsIdAndElemAndZAndForecastTimeAfterAndForecastTimeBeforeOrderByForecastTime(
                   baseTime, dataset.getId(), elem, z, baseTime, instance.getTime());

        List<Double> result = new ArrayList<>();

        // 获取数据
        for (GridMetaEntity meta : metaList) {
            GridDataEntity data = dataRepository.findById(
               GridDataKey.builder()
                  .metaId(meta.getId())
                  .chunkId(chuckIdx)
                  .build())
               .orElse(null);

            result.add(data.getDs().get(dataIdx));
        }

        stopWatch.stop();
        System.out.printf("耗时：%d%s.\n", stopWatch.getLastTaskTimeMillis(), "ms");

        return result;
    }

}

