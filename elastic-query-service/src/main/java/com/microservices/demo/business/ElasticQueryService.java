package com.microservices.demo.business;

import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElasticQueryService {
    ElasticQueryServiceResponseModel getDocumentById(String id);

    List<ElasticQueryServiceResponseModel> getDocumentsByText(String text);

    List<ElasticQueryServiceResponseModel> getAllDocuments();
}
