package xyz.txcplus.elasticsearch.service;

import lombok.Data;

import java.io.Serializable;

/**
 * ES经纬度实体
 *
 * @version 1.0.0
 * @author wenhai
 * @since JDK 1.8
 */
@Data
public class GeoPoint implements Serializable {

    /**
     * 纬度
     */
    private Double lat;
    /**
     * 经度
     */
    private Double lon;

    public GeoPoint() {
    }

    public GeoPoint(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
