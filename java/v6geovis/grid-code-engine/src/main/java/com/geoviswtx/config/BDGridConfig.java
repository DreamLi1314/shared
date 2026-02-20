//package com.geoviswtx.config;
//
//import com.geovis.bdgrid.BDGrid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.annotation.PostConstruct;
//
///**
// * BDGridConfig
// *
// * @author yuandenglang
// * @since 2025/3/28 15:42
// */
//@Slf4j
////@Configuration
//public class BDGridConfig {
//
//    @Value("${grid.code.host}")
//    private String gridCodeHost;
//
//    @PostConstruct
//    public void init() {
//        try {
//            BDGrid.authorize(gridCodeHost);
//        }catch (Exception e){
//            log.error("网格编码授权失败，失败信息是：{}",e.getMessage());
//        }
//    }
//
//}
