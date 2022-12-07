package com.microservices.demo.elastic.query.service.impl;

import com.microservices.demo.common.util.CollectionsUtil;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.exception.ElasticQueryClientException;
import com.microservices.demo.elastic.query.repository.TwitterElasticSearchQueryRepository;
import com.microservices.demo.elastic.query.service.ElasticQueryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryQueryClient.class);

    private final TwitterElasticSearchQueryRepository twitterQueryRepository;

    public TwitterElasticRepositoryQueryClient(TwitterElasticSearchQueryRepository twitterQueryRepository) {
        this.twitterQueryRepository = twitterQueryRepository;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> searchResult = twitterQueryRepository.findById(id);
        LOG.info("Document with id {} retrieved successfully", searchResult.orElseThrow(() ->
                new ElasticQueryClientException("No document found at elasticsearch with id " + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResult = twitterQueryRepository.findByText(text);
        LOG.info("{} of documents with text {} retrieved", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> searchResults = CollectionsUtil.getInstance().getListFromIterable(twitterQueryRepository.findAll());
        LOG.info("{} number of documents retrieved successfully", searchResults.size());
        return searchResults;
    }
}
