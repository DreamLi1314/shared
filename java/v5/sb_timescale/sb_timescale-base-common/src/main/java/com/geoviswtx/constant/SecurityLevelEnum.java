package com.geoviswtx.constant;

/**
 * SecurityLevelEnum
 *
 * @author guohr
 * @since 2024/9/14 9:30
 */
public enum SecurityLevelEnum {
    M1("HSL_BACKUP_STRATEGY_PG_KEY_M1", "1级密级"),
    M2("HSL_BACKUP_STRATEGY_PG_KEY_M2", "2级密级"),
    M3("HSL_BACKUP_STRATEGY_PG_KEY_M3", "3级密级"),
    DATA_TYPE("HSL_BACKUP_STRATEGY_PG_KEY_DATA_TYPE", "按数据类型");


    private final String redisKey;

    private final String desc;

    SecurityLevelEnum(String redisKey, String desc) {
        this.redisKey = redisKey;
        this.desc = desc;
    }


    public String getRedisKey() {
        return redisKey;
    }

    public String getDesc() {
        return desc;
    }
}