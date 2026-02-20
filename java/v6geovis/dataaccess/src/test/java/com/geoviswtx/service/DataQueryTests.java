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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
public class DataQueryTests {

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

    @Test
    void init() {
        DatasetEntity dataset = DatasetEntity.builder()
                .id(UUID.randomUUID())
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
    @ValueSource(strings = "ecfaace2-ed17-4507-9c10-6a638ce90ebd")
    void recordData(String dsId) throws Exception {
        DatasetEntity dataset = repository.findById(UUID.fromString(dsId)).orElse(null);

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

            Variable tem = netcdfFile.findVariable("Temperature_surface");

            if(DataType.FLOAT == tem.getDataType()) {
                float[] array = (float[]) tem.read().copyTo1DJavaArray();

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
                        dataArray[j] = (double) array[i * chuckNumber + j] - 273.15;
                    }

                    GridDataEntity gridData = GridDataEntity.builder()
                            .id(UUID.randomUUID())
                            .metaId(gridMetaEntity.getId())
                            .chuckIdx(i)
                            .datas(dataArray)
                            .build();

                    gridDatas.add(gridData);
                }

                log.info("Grid data size：{}", gridDatas.size());

                gridDatas = dataRepository.saveAll(gridDatas);

                log.info("Saving grid data size：{}", gridDatas.size());
            }

            log.info("{} file process completed!", nc.getName());
        }

        log.info("all file process completed!");
    }

}
