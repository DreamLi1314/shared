package com.geoviswtx.service;

import com.geoviswtx.dao.pg.DatasetRepository;
import com.geoviswtx.dao.cassandra.GridDataRepository;
import com.geoviswtx.entity.cassandra.GridDataEntity;
import com.geoviswtx.entity.cassandra.GridDataKey;
import com.geoviswtx.entity.pg.DatasetEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
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
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class DataQueryTests {

    @Autowired
    private DatasetRepository repository;

//    @Autowired
//    private GridMetaRepository metaRepository;

    @Autowired
    private GridDataRepository dataRepository;

    @Test
    void testContext() {
        Assertions.assertNotNull(repository);
    }

    @Test
    void initGfs() {
        DatasetEntity dataset = DatasetEntity.builder()
                .id(1L)
                .type("gfs")
                .chuckNumber(2500)
                .startX(0D)
                .endX(360D)
                .startY(90D)
                .endY(-90D)
                .incrementX(0.25)
                .incrementY(0.25)
                .countX(1440)
                .countY(721)
                .build();

        dataset = repository.save(dataset);

        System.out.println(dataset.getId());
    }

    @Rollback(value = false)
    @Transactional
    @ParameterizedTest
    @ValueSource(longs = { 1 })
    void recordData(long dsId) throws Exception {
        ZoneId zoneId = TimeZone.getTimeZone("GMT+08:00").toZoneId();
        DatasetEntity dataset = repository.findById(dsId).orElse(null);

        Assertions.assertNotNull(dataset, "Dataset id is null");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        File file = new File("dat");

        Date baseTime = dateFormat.parse("2023-08-27 20");
        File[] files = file.listFiles();
        Pattern pattern = Pattern.compile("^gfs.t12z.pgrb2.0p25.f(\\d+)$");

        for (File nc : files) {
            Matcher matcher = pattern.matcher(nc.getName());
            if(!matcher.matches()) {
                log.info("{} not match nc！", nc.getName());
                continue;
            }

            int hourOffset = Integer.parseInt(matcher.group(1));

            log.info("Process offset {} nc", hourOffset);

            NetcdfFile netcdfFile = NetcdfFile.openInMemory(nc.getAbsolutePath());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(baseTime);
            calendar.add(Calendar.HOUR_OF_DAY, hourOffset);

            Variable tem = netcdfFile.findVariable("Temperature_surface");

            if(DataType.FLOAT == tem.getDataType()) {
                float[] array = (float[]) tem.read().copyTo1DJavaArray();
                List<Float> ds = Arrays.asList(ArrayUtils.toObject(array));

                GridDataEntity gridData = GridDataEntity.builder()
                   .gridDataKey(GridDataKey.builder()
                      .dsId(dataset.getId())
                      .elem("tem")
                      .z(0D)
                      .baseTime(baseTime
                         .toInstant()
                         .atZone(zoneId)
                         .toLocalDateTime())
                      .forecastTime(calendar.getTime()
                         .toInstant()
                         .atZone(zoneId)
                         .toLocalDateTime())
                      .build())
                   .ds(ds)
                   .build();

                gridData = dataRepository.save(gridData);

                log.info("Saving grid data size：{}", calendar.getTime().toLocaleString());
            }

            log.info("{} file process completed!", nc.getName());
        }

        log.info("all file process completed!");
    }

}
