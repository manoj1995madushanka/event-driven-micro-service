package com.microservices.demo.reactive.elastic.query.web.client.service;

import com.microservices.demo.elastic.query.web.client.common.model.EQWCRequestModel;
import com.microservices.demo.elastic.query.web.client.common.model.EQWCResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryWebClient {

    Flux<EQWCResponseModel> getDataByText(EQWCRequestModel request);
}
