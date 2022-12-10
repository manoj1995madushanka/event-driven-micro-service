package com.microservices.demo.transformer;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticToResponseModelTransformer {

    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel twitterIndexModel){
        return ElasticQueryServiceResponseModel.builder()
                .id(twitterIndexModel.getId())
                .text(twitterIndexModel.getText())
                .userId(twitterIndexModel.getUserId())
                .createdAt(twitterIndexModel.getCreatedAt())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModels(List<TwitterIndexModel> twitterIndexModels){
        return twitterIndexModels.stream().map(this::getResponseModel).collect(Collectors.toList());
    }
}
