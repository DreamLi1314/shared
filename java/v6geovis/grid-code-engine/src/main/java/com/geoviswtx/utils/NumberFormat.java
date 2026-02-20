package com.geoviswtx.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * NumberFormat
 *
 * @author yuandenglang
 * @since 2025/3/21 20:37
 */
public class NumberFormat {
    public static double format(double number,int scale) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
