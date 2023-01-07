/*
 Navicat Premium Data Transfer

 Source Server         : pg
 Source Server Type    : PostgreSQL
 Source Server Version : 130005
 Source Host           : 106.75.70.77:25432
 Source Catalog        : thunderstorm
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130005
 File Encoding         : 65001

 Date: 06/09/2022 10:46:19
*/

-- ----------------------------
-- Table structure for t_threshold_manage
-- ----------------------------
CREATE TABLE IF NOT EXISTS "public"."t_threshold_manage" (
    "id" int8 NOT NULL,
    "create_date" timestamp(6),
    "update_date" timestamp(6),
    "type" int4,
    "unit" varchar(255) COLLATE "pg_catalog"."default",
    "value" float8,
    CONSTRAINT "t_threshold_manage_pkey" PRIMARY KEY ("id")
)
;

-- ----------------------------
-- Table structure for t_atmoele_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS "public"."t_atmoele_info" (
    "id" int8 NOT NULL,
    "create_date" timestamp(6),
    "update_date" timestamp(6),
    "height" float8,
    "lat" float8,
    "lon" float8,
    "probe_id" varchar(255) COLLATE "pg_catalog"."default",
    "probe_name" varchar(255) COLLATE "pg_catalog"."default",
    "warning_km" float8,
    CONSTRAINT "t_atmoele_info_pkey" PRIMARY KEY ("id")
)
;
-- ALTER TABLE "public"."t_atmoele_info" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_dict_chemical_type
-- ----------------------------
CREATE TABLE IF NOT EXISTS "public"."t_dict_chemical_type" (
     "id" int8 NOT NULL,
     "create_date" timestamp(6),
     "update_date" timestamp(6),
     "code" varchar(255) COLLATE "pg_catalog"."default",
     "name" varchar(255) COLLATE "pg_catalog"."default",
     "parent_id" int4,
     "type_code" varchar(255) COLLATE "pg_catalog"."default",
     "value" varchar(255) COLLATE "pg_catalog"."default",
     CONSTRAINT "t_dict_chemical_type_pkey" PRIMARY KEY ("id")
)
;
-- ALTER TABLE "public"."t_dict_chemical_type" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_dict_box
-- ----------------------------
CREATE TABLE IF NOT EXISTS "public"."t_dict_box" (
    "id" int8 NOT NULL,
    "create_date" timestamp(6),
    "update_date" timestamp(6),
    "code" varchar(255) COLLATE "pg_catalog"."default",
    "end_lat" float4,
    "end_lon" float4,
    "lat_interval" float4,
    "lon_interval" float4,
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "start_lat" float4,
    "start_lon" float4,
    "border" "public"."geometry",
    CONSTRAINT "t_dict_box_pkey" PRIMARY KEY ("id")
)
;
-- ALTER TABLE "public"."t_dict_box" OWNER TO "postgres";

-- ----------------------------
-- Table structure for t_administrative
-- ----------------------------
CREATE TABLE IF NOT EXISTS "public"."t_administrative" (
    "id" int8 NOT NULL,
    "create_date" timestamp(6),
    "update_date" timestamp(6),
    "code" varchar(255) COLLATE "pg_catalog"."default",
    "geometry" "public"."geometry",
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "parent_code" varchar(255) COLLATE "pg_catalog"."default",
    CONSTRAINT "t_administrative_pkey" PRIMARY KEY ("id")
)
;
-- ALTER TABLE "public"."t_administrative" OWNER TO "postgres";
