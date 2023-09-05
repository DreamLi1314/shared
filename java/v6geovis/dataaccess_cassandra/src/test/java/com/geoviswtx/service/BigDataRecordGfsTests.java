package com.geoviswtx.service;

import com.geoviswtx.dao.pg.DatasetRepository;
import com.geoviswtx.dao.cassandra.GridDataRepository;
import com.geoviswtx.dao.pg.GridMetaRepository;
import com.geoviswtx.entity.cassandra.GridDataEntity;
import com.geoviswtx.entity.cassandra.GridDataKey;
import com.geoviswtx.entity.pg.DatasetEntity;
import com.geoviswtx.entity.pg.GridMetaEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ucar.ma2.DataType;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class BigDataRecordGfsTests {

    @Autowired
    private DatasetRepository repository;

    @Autowired
    private GridMetaRepository metaRepository;

    @Autowired
    private GridDataRepository dataRepository;

    @Test
    void testContext() {
        Assertions.assertNotNull(repository);
    }

    /**
     * gfs 一个时次、一个要素 208 条数据, 一条数据 2500 chuck 存 416条 gridData 记录
     *      1）一个时次、15个要素：15*250 = 3750， 1560000 条 gridData 记录
     *      2）每天两个时次，15个要素：2*3750 = 7500， 31200000 条 gridData 记录
     *      3）存一个月: 31*7500 = 232500， 96720000 条 gridData 记录
     *
     * gfs 一个时次、一个要素 208 条数据, 一条数据 2500 chuck 存 416条 gridData 记录
     *      1）一个时次、10个要素：10*208 = 2080， 865280 条 gridData 记录
     *      2）每天两个时次，10个要素：2*2080 = 4160， 1730560 条 gridData 记录
     *      3）存10天20个时次: 10*4160 = 41600， 17305600 条 gridData 记录
     * @param dsId 数据集 id
     * @throws Exception exc
     */
    @Rollback(value = false)
//    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 1)
    void recordGfsTestData(long dsId) throws Exception {
        int fileCount = 1619;
        ZoneId zoneId = TimeZone.getTimeZone("GMT+08:00").toZoneId();
        DatasetEntity dataset = repository.findById(dsId).orElse(null);

        Assertions.assertNotNull(dataset, "Dataset id is null");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

        File nc = new File("dat/gfs.t12z.pgrb2.0p25.f000");
        NetcdfFile netcdfFile = NetcdfFile.openInMemory(nc.getAbsolutePath());
        Variable tem = netcdfFile.findVariable("Temperature_surface");
        float[] array = (float[]) tem.read().copyTo1DJavaArray();

        Date baseTime = dateFormat.parse("2020-01-01 08");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);

        int hourOffset = 845;

        if(hourOffset > 0) {
            calendar.add(Calendar.HOUR_OF_DAY, hourOffset);
        }

        for (; hourOffset < fileCount; hourOffset++) {
            log.info("Process offset {} nc", hourOffset);

            GridMetaEntity gridMetaEntity = metaRepository.save(
               GridMetaEntity.builder()
                  .dsId(dataset.getId())
                  .elem("tem")
                  .z(0D)
                  .baseTime(baseTime)
                  .forecastTime(calendar.getTime())
                  .build());

            calendar.add(Calendar.HOUR_OF_DAY, 1);

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

                    List<Double> ds = new ArrayList<>(size);

                    for (int j = 0; j < size; j++) {
                        ds.add((double) array[i * chuckNumber + j] - 273.15);
                    }

                    GridDataEntity gridData = GridDataEntity.builder()
                       .gridDataKey(GridDataKey.builder()
                          .metaId(gridMetaEntity.getId())
                          .chunkId(i)
                          .build())
                       .ds(ds)
                       .build();

                    gridDatas.add(gridData);
                }

                log.info("Grid data size：{}", gridDatas.size());

                gridDatas = dataRepository.saveAll(gridDatas);

                TimeUnit.SECONDS.sleep(1);

                log.info("Saving grid data size：{}", gridDatas.size());
            }

            log.info("{} file process completed!", nc.getName());
        }

        netcdfFile.close();
        log.info("all file process completed!");
    }

}
