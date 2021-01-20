package xyz.txcplus.elasticsearch.service;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

/**
 * @author wenhai
 * @version 1.0.0
 * @since JDK 1.8
 */

@Document(indexName = "stu", type = "_doc", shards = 3, replicas = 0)
@Data
public class Stu {

    /**
     * 数据id
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 年龄
     */
    @Field(type = FieldType.Integer)
    private Integer age;

    /**
     * 钱
     */
    @Field(type = FieldType.Float)
    private Float money;

    /**
     * 标志
     */
    @Field(type = FieldType.Text)
    private String sign;

    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", fielddata = true)
    private String description;

    /**
     * 经纬度
     */
    @GeoPointField
    private GeoPoint position = new GeoPoint();

    public void setLongitude(Double longitude) {
        this.position.setLon(longitude);
    }

    public void setLatitude(Double latitude) {
        this.position.setLat(latitude);
    }
}

