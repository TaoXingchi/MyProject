package xyz.txcplus.elasticsearch.service;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsStudyDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private EsStuRepository esStuRepository;

    @Test
    public void context() {

    }

    @Test
    public void batchSave() {
        Stu stu1 = new Stu();
        stu1.setId(1L);
        stu1.setName("LOL1");
        stu1.setAge(20);
        stu1.setMoney(1000.1f);
        stu1.setSign("网游");
        stu1.setDescription("LOL是网游");
        stu1.setLatitude(30.101);
        stu1.setLongitude(101.111);

        Stu stu2 = new Stu();
        stu2.setId(2L);
        stu2.setName("LOL2");
        stu2.setAge(21);
        stu2.setMoney(1000.1f);
        stu2.setSign("竞技游戏");
        stu2.setDescription("LOL可以竞技对战");
        stu2.setLatitude(30.201);
        stu2.setLongitude(101.211);

        Stu stu = new Stu();
        stu.setId(3L);
        stu.setName("LOL3");
        stu.setAge(23);
        stu.setMoney(1000.1f);
        stu.setSign("自走棋");
        stu.setDescription("LOL可以玩自走棋");
        stu.setLatitude(30.301);
        stu.setLongitude(101.311);

        List<Stu> stuList = new ArrayList<>();
        stuList.add(stu);
        stuList.add(stu1);
        stuList.add(stu2);
        esStuRepository.saveAll(stuList);
    }

    @Test
    public void save() {
        Stu stu = new Stu();
        stu.setId(1L);
        stu.setName("LOL");
        stu.setAge(20);
        stu.setMoney(1000.1f);
        stu.setSign("竞技游戏");
        stu.setDescription("LOL是网游");
        esStuRepository.save(stu);
    }

    @Test
    public void get() {
        Optional<Stu> optional = esStuRepository.findById(1L);
        optional.ifPresent(System.out::println);
    }

    @Test
    public void search() {
//        Pageable pageable = PageRequest.of(0, 2);

        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("name", "LOL1"))
//                .withQuery(QueryBuilders.termQuery())
//                .withQuery(QueryBuilders.rangeQuery("age"))
//                .withPageable(pageable)
                .build();
        Page<Stu> search = esStuRepository.search(query);
        System.out.println("检索后的总分页数目为：" + search.getTotalPages());
        List<Stu> stuList = search.getContent();
        for (Stu s : stuList) {
            System.out.println(s);
        }
    }

    @Test
    public void  distanceSearch(){
        double lon=101.111;
        double lat=30.101;
        double distance=100;
        GeoDistanceQueryBuilder builder =
                //查询字段
                QueryBuilders.geoDistanceQuery("position")
                        //设置经纬度
                        .point(lat, lon)
                        //设置距离和单位（千米）
                        .distance(distance, DistanceUnit.KILOMETERS)
                        .geoDistance(GeoDistance.ARC);
        GeoDistanceSortBuilder sortBuilder =
                SortBuilders.geoDistanceSort("position", lat, lon)
                        .point(lat, lon)
                        .unit(DistanceUnit.KILOMETERS)
                        //排序方式
                        .order(SortOrder.ASC);
        SearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("description", "LOL是网游"))
                .withFilter(builder)
                .withSort(sortBuilder)
//                .withSort()
                .build();
        Page<Stu> search = esStuRepository.search(query);
        List<Stu> stuList = search.getContent();
        for (Stu s : stuList) {
            System.out.println(s);
        }
    }

    @Test
    public void delete() {
        esStuRepository.deleteById(1L);
    }

    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setId(1005L);
        stu.setName("iron man");
        stu.setAge(54);
        stu.setMoney(1999.8f);
        stu.setSign("I am iron man");
        stu.setDescription("I have a iron army");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void deleteIndexStu() {
        esTemplate.deleteIndex(Stu.class);
    }
}
