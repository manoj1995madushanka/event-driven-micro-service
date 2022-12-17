package com.microservices.demo.elastic.query.web.client.service;

import com.microservices.demo.elastic.query.web.client.model.EQWCRequestModel;
import com.microservices.demo.elastic.query.web.client.model.EQWCResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<EQWCResponseModel> getDataByText(EQWCRequestModel requestModel);
}
