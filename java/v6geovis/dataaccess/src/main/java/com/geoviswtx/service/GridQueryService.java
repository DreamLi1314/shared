package com.geoviswtx.service;

import com.geoviswtx.dao.DatasetRepository;
import com.geoviswtx.dao.GridDataRepository;
import com.geoviswtx.dao.GridMetaRepository;
import com.geoviswtx.entity.DatasetEntity;
import com.geoviswtx.entity.GridDataEntity;
import com.geoviswtx.entity.GridMetaEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GridQueryService {

    private final GridDataRepository dataRepository;
    private final GridMetaRepository metaRepository;
    private final DatasetRepository datasetRepository;

    public GridQueryService(GridDataRepository dataRepository,
                            GridMetaRepository metaRepository,
                            DatasetRepository datasetRepository) {
        this.dataRepository = dataRepository;
        this.metaRepository = metaRepository;
        this.datasetRepository = datasetRepository;
    }

    /**
     * 穿透查询测试
     *
     * @param baseTime
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

        // 获取 ds
        DatasetEntity dataset = datasetRepository.findByType(datasetType);

//        int gridRowIndex = (int) Math.floor((lat - dataset.getStartY()) / dataset.getIncrementY());
//        int gridColumnIndex = (int) Math.floor((lon - dataset.getStartX()) / dataset.getIncrementX());

        int gridRowIndex = (int) Math.ceil((dataset.getStartY() - lat) / dataset.getIncrementY());
        int gridColumnIndex = (int) Math.floor(lon / dataset.getIncrementX());

        int gridIdx = gridRowIndex * dataset.getCountX() + gridColumnIndex;
        int chuckIdx = (int) Math.floor(gridIdx * 1.0 / dataset.getChuckNumber());
        int dataIdx = gridIdx % dataset.getChuckNumber();

        // 获取 meta
        List<GridMetaEntity> metaList
                = metaRepository.findListByQbsjAndDatasetIdAndFeatureAndZOrderByYbsk(baseTime, dataset.getId(), elem, z);

        List<Double> result = new ArrayList<>();

        // 获取数据
        for (GridMetaEntity meta : metaList) {
            GridDataEntity data = dataRepository.findByMetaIdAndChuckIdx(meta.getId(), chuckIdx);

            result.add(data.getDatas()[dataIdx]);
        }

        stopWatch.stop();
        System.out.printf("耗时：%d%s.\n", stopWatch.getLastTaskTimeMillis(), "ms");

        return result;
    }

}

