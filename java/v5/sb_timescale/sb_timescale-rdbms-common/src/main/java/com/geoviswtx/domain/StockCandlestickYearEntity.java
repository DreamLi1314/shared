package com.geoviswtx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * StockCandlestickDailyEntity
 *
 * @Author JackLi 2024/11/5 10:05
 */
@Data
@Immutable
@Table(name = "stock_candlestick_year")
@Accessors(chain = true)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCandlestickYearEntity {

    @Id
    @ApiModelProperty(value = "时间")
    private Date yt;

    @ApiModelProperty(value = "类别")
    private String symbol;

    private Double high;
    private Double low;
    private Double avgPrice;
}
