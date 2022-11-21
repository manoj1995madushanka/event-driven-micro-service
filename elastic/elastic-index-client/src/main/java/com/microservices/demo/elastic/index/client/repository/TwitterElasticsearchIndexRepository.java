package com.microservices.demo.elastic.index.client.repository;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/*
* ElasticSearchRepository provides convenient methods like save,find etc.
* ElasticSearchOperations gives chance of running low level queries like elasticsearch bool, must etc.
* ElasticSearchOperations requires to convert the input object to query objects
* */
@Repository
public interface TwitterElasticsearchIndexRepository extends ElasticsearchRepository<TwitterIndexModel,String> {
}
