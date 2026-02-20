package com.geoviswtx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geoviswtx.utils.NumberFormat;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * BasicFlightInfoEntity
 *
 * @author yuandenglang
 * @since 2025/3/20 22:03
 */
@Data
public class BasicFlightInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    // @ApiModelProperty("海拔")
    @JsonProperty("altitude")
    private Double altitude;

    // @ApiModelProperty("坐标类型，1：代表WGS-84，2：代表CGCS2000，3：代表GLONASS-PZ90")
    @JsonProperty("coordinate")
    private Integer coordinate;

    @JsonProperty("course")
    private Double course;

    // @ApiModelProperty("飞行状态")
    @JsonProperty("flightStatus")
    private String flightStatus;

    // @ApiModelProperty("水平飞行速度")
    @JsonProperty("gS")
    private Double gs;

    // @ApiModelProperty("垂直飞行速度")
    @JsonProperty("vS")
    private Double vs;

    // @ApiModelProperty("绝对高度")
    @JsonProperty("height")
    private Double height;

    // @ApiModelProperty("高度类型标识，0：真高为相对起飞点高差，高度为标准海平面气压高度（QNE）" +
    //        "1：真高为相对起飞点高差，高度为修正海平面气压高度（QNH），真高为相对当前位置地面高差，高度为标准海平面气压高度（QNE）" +
    //        "3：真高为相对当前位置地面高差，高度为修正海平面气压高度（QNH）")
    @JsonProperty("heightAltitype")
    private Integer heightAltiType;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    // @ApiModelProperty("纬度")
    private Double lat;

    // @ApiModelProperty("经度")
    private Double lon;

    // @ApiModelProperty("制造商唯一标识")
    @JsonProperty("manufacturerID")
    private String manufacturerID;

    // @ApiModelProperty("订单编号")
    @JsonProperty("orderID")
    private String orderID;

    // @ApiModelProperty("设备序列号")
    @JsonProperty("sn")
    private String sn;

    @JsonProperty("timeStamp")
    /**
     * 时间，格式为yyyyMMddHHmmss
     */
    private String timeStamp;

    private LocalDateTime time;

    // @ApiModelProperty("无人机标识")
    @JsonProperty("uasID")
    private String uasID;

    // @ApiModelProperty("无人机机型")
    @JsonProperty("uasModel")
    private String uasModel;

    // @ApiModelProperty("无人机id")
    private Long uavId;

    private Date createTime;

    private Date updateTime;

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        if (!StringUtils.isEmpty(longitude)){
            long aLong = Long.parseLong(longitude);
            this.lon = NumberFormat.format(aLong/Math.pow(10,7),7);
        }
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        if (!StringUtils.isEmpty(latitude)){
            long aLong = Long.parseLong(latitude);
            this.lat = NumberFormat.format(aLong/Math.pow(10,7),7);
        }
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        if (!StringUtils.isEmpty(timeStamp)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            this.time = LocalDateTime.parse(timeStamp,formatter);
        }
    }

    public void setHeight(Double height) {
        if (height != null){
            this.height = NumberFormat.format(height/10,1);
        }
    }
    public void setAltitude(Double altitude) {
        if (altitude != null){
            this.altitude = NumberFormat.format(altitude/10,1);
        }
    }

    public void setGs(Double gs) {
        if (gs != null){
            if (gs == -999){
                this.gs = null;
            }else {
                this.gs = NumberFormat.format(gs/10,1);
            }
        }
    }

    public void setVs(Double vs) {
        if (vs != null){
            if (vs == -999){
                this.vs = null;
            }else {
                this.vs = NumberFormat.format(vs/10,1);
            }
        }
    }

    public void setCourse(Double course) {
        if (course != null){
            if (course == 999.0){
                this.course = null;
            }else {
                this.course = NumberFormat.format(course/10,1);
            }
        }
    }

    public void setHeightAltitype(Integer heightAltiType) {
        if (heightAltiType != null){
            if (heightAltiType == -999){
                this.heightAltiType = null;
            }else {
                this.heightAltiType = heightAltiType;
            }
        }
    }
}
