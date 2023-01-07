/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 106.75.70.77:23306
 Source Schema         : thunderstorm

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 06/09/2022 16:54:27
*/

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_registry` VALUES (9, 'EXECUTOR', 'thunderstorm-xxl-executor', 'http://192.168.100.252:9007/', '2022-09-06 16:54:06');
COMMIT;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_group` VALUES (4, 'thunderstorm-xxl-executor', '重庆数据同步', 0, 'http://192.168.100.252:9007/', '2022-09-06 16:54:30');
INSERT INTO `xxl_job_group` VALUES (5, 'thunderstorm-companyapi-xxl-executor', '公司数据接口执行器', 0, 'http://192.168.100.252:9008/', '2022-09-14 18:45:01');
COMMIT;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_info` VALUES (7, 4, '云图同步', '2022-08-18 16:56:55', '2022-08-26 17:16:21', 'JackLi', '', 'CRON', '0 5/10 * * * ?', 'DO_NOTHING', 'FIRST', 'syncFy4ByCqApiJobHandler', '60', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-08-18 16:56:55', '', 1, 1662454500000, 1662455100000);
INSERT INTO `xxl_job_info` VALUES (8, 4, '同步雷达图', '2022-08-18 18:44:47', '2022-08-22 11:45:01', 'JackLi', '', 'CRON', '0 0/6 * * * ?', 'DO_NOTHING', 'FIRST', 'syncRadarByCqApiJobHandler', '同步雷达图', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-08-18 18:44:47', '', 1, 1662454440000, 1662454800000);
INSERT INTO `xxl_job_info` VALUES (9, 4, '同步大气电场仪数据', '2022-08-19 17:49:58', '2022-08-26 17:13:59', 'JackLi', '', 'CRON', '0 2/10 * * * ?', 'DO_NOTHING', 'FIRST', 'syncAtmosElectricByCqApiJobHandler', '20', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-08-19 17:49:58', '', 1, 1662454320000, 1662454920000);
INSERT INTO `xxl_job_info` VALUES (10, 4, '同步二维闪电定位仪数据', '2022-08-23 14:43:59', '2022-08-29 15:36:27', 'JackLi', '', 'CRON', '0 0/5 0/1 * * ?', 'DO_NOTHING', 'FIRST', 'syncLightningByCqApiJobHandler', '同步二维闪电定位仪数据', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-08-23 14:43:59', '', 1, 1662454500000, 1662454800000);
INSERT INTO `xxl_job_info` VALUES (11, 4, '计算仓库风险列表', '2022-08-24 18:07:15', '2022-08-29 15:34:09', 'Jack Li', '', 'CRON', '0 0/6 0/1 * * ?', 'DO_NOTHING', 'FIRST', 'calcRealtimeRiskListJobHandler', '计算仓库风险列表，半小时累积计算', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-08-24 18:07:15', '', 1, 1662454440000, 1662454800000);

INSERT INTO `xxl_job_info` VALUES (13, 5, '仓库2h短临风险等级计算', '2022-09-14 18:18:47', '2022-09-14 18:18:47', 'JackLi', '243853974@qq.com', 'CRON', '0 1/10 * * * ?', 'DO_NOTHING', 'FIRST', 'syncLightningJobHandler', '仓库2h短临风险等级计算', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-09-14 18:18:47', '', 0, 0, 0);
COMMIT;
