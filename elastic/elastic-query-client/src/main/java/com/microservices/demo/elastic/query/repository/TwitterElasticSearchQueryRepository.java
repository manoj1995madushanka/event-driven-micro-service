package com.microservices.demo.elastic.query.repository;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ElasticSearchRepository is a high level client that offers predefined
 * methods to query against the fields on the elasticsearch.
 * but it cannot be used to send complex quires to elasticsearch
 */
@Repository
public interface TwitterElasticSearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {

    List<TwitterIndexModel> findByText(String text);
}
