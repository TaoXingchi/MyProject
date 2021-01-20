package xyz.txcplus.elasticsearch.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wenhai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Repository
public interface EsStuRepository extends ElasticsearchRepository<Stu, Long> {
}
