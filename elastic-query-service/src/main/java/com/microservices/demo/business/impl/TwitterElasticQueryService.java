package com.microservices.demo.business.impl;

import com.microservices.demo.business.ElasticQueryService;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.service.ElasticQueryClient;
import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.model.assembler.ElasticQueryServiceResponseModelAssembler;
import com.microservices.demo.transformer.ElasticToResponseModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticQueryService implements ElasticQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);

    private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;

    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(ElasticQueryServiceResponseModelAssembler assembler,
                                      ElasticQueryClient<TwitterIndexModel> queryClient) {
        this.elasticQueryServiceResponseModelAssembler = assembler;
        this.elasticQueryClient = queryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        LOG.info("Querying elasticsearch by id {}", id);
        return elasticQueryServiceResponseModelAssembler.toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentsByText(String text) {
        LOG.info("Querying elasticsearch by text {}", text);
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        LOG.info("Querying all documents in elasticsearch");
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getAllIndexModels());
    }

}
