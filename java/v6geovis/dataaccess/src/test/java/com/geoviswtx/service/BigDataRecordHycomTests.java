package com.geoviswtx.service;

import com.geoviswtx.dao.DatasetRepository;
import com.geoviswtx.dao.GridDataRepository;
import com.geoviswtx.dao.GridMetaRepository;
import com.geoviswtx.entity.DatasetEntity;
import com.geoviswtx.entity.GridDataEntity;
import com.geoviswtx.entity.GridMetaEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ucar.ma2.DataType;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@SpringBootTest
public class BigDataRecordHycomTests {

    @Autowired
    private DatasetRepository repository;

    @Autowired
    private GridMetaRepository metaRepository;

    @Autowired
    private GridDataRepository dataRepository;

    @Test
    void init() {
        DatasetEntity dataset = DatasetEntity.builder()
                .id(UUID.randomUUID())
                .type("hycom")
                .chuckNumber(2500)
                .startX(0D)
                .endX(360D)
                .startY(-80D)
                .endY(90D)
                .incrementX(0.08)
                .incrementY(0.04)
                .countX(4500)
                .countY(4251)
                .build();

        dataset = repository.save(dataset);

        System.out.println(dataset.getId());
    }

    /**
     * hycom 一个时次、一个要素按 208 条数据, 一条数据 2500 chuck 存 7652 条 gridData 记录
     *      1）一个时次、10个要素：10*208 = 2080， 1591,6160 条 gridData 记录
     *      2）每天两个时次，10个要素：2*2080 = 4160， 3183,2320 条 gridData 记录
     *      3）存10天20个时次: 10*4160 = 41600， 3,1832,3200 条 gridData 记录
     * @param dsId 数据集 id
     * @throws Exception exc
     */
//    @Rollback(value = false)
    @Transactional
    @ParameterizedTest
    @ValueSource(strings = "062a6027-b715-483d-ae67-92ff0b6a5070")
    void recordGfsTestData(String dsId) throws Exception {
        int fileCount = 41600;
        DatasetEntity dataset = repository.findById(UUID.fromString(dsId)).orElse(null);

        Assertions.assertNotNull(dataset, "Dataset id is null");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

        File nc = new File("dat/runs_GLBy0.nc4");
        NetcdfFile netcdfFile = NetcdfFile.openInMemory(nc.getAbsolutePath());
        Variable tem = netcdfFile.findVariable("sst");
        float[] array = (float[]) tem.read().copyTo1DJavaArray();

        Date baseTime = dateFormat.parse("2000-01-01 20");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);

        int hourOffset = 0;

        if(hourOffset > 0) {
            calendar.add(Calendar.HOUR_OF_DAY, hourOffset);
        }

        for (; hourOffset < fileCount; hourOffset++) {
            log.info("Process offset {} nc", hourOffset);

            calendar.add(Calendar.HOUR_OF_DAY, 1);

            GridMetaEntity gridMetaEntity = GridMetaEntity.builder()
                    .id(UUID.randomUUID())
                    .datasetId(dataset.getId())
                    .feature("tem")
                    .z(0D)
                    .qbsj(baseTime)
                    .ybsk(calendar.getTime())
                    .build();

            gridMetaEntity = metaRepository.save(gridMetaEntity);

            Integer chuckNumber = dataset.getChuckNumber();

            if(DataType.FLOAT == tem.getDataType()) {
                boolean addOne = array.length % chuckNumber != 0;
                int checkRowCount = array.length / chuckNumber + (addOne ? 1 : 0);

                List<GridDataEntity> gridDatas = new ArrayList<>();

                for (int i = 0; i < checkRowCount; i++) {
                    int size = chuckNumber;

                    if((i == (checkRowCount - 1)) && addOne) {
                        size = array.length % chuckNumber;
                    }

                    double[] dataArray = new double[size];

                    for (int j = 0; j < size; j++) {
                        dataArray[j] = array[i * chuckNumber + j];
                    }

                    GridDataEntity gridData = GridDataEntity.builder()
                            .id(UUID.randomUUID())
                            .metaId(gridMetaEntity.getId())
                            .chuckIdx(i)
                            .datas(dataArray)
                            .build();

                    gridDatas.add(gridData);
                }

                log.info("Hycom Grid data size：{}", gridDatas.size());

                gridDatas = dataRepository.saveAll(gridDatas);

                log.info("Hycom Saving grid data size：{}", gridDatas.size());
            }

            log.info("Hycom {} file process completed!", nc.getName());
        }

        log.info("all hycom file process completed!");
    }

}
