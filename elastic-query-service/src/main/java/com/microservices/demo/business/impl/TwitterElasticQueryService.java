package com.microservices.demo.business.impl;

import com.microservices.demo.business.ElasticQueryService;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.service.ElasticQueryClient;
import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.transformer.ElasticToResponseModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticQueryService implements ElasticQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);
    private final ElasticToResponseModelTransformer modelTransformer;
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(ElasticToResponseModelTransformer modelTransformer
            , ElasticQueryClient<TwitterIndexModel> elasticQueryClient) {
        this.modelTransformer = modelTransformer;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        LOG.info("Querying elastic search by id {}", id);
        return modelTransformer.getResponseModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentsByText(String text) {
        LOG.info("Querying elastic  search by text {}", text);
        return modelTransformer.getResponseModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        LOG.info("Querying all elastic  search data");
        return modelTransformer.getResponseModels(elasticQueryClient.getAllIndexModels());
    }
}
