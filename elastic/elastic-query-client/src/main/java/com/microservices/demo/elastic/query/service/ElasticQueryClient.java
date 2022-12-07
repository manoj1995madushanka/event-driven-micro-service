package com.microservices.demo.elastic.query.service;

import com.microservices.demo.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticQueryClient <T extends IndexModel>{

    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModels();
}
