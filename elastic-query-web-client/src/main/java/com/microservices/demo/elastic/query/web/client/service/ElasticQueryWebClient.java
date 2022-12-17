package com.microservices.demo.elastic.query.web.client.service;


import com.microservices.demo.elastic.query.web.client.common.model.EQWCRequestModel;
import com.microservices.demo.elastic.query.web.client.common.model.EQWCResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<EQWCResponseModel> getDataByText(EQWCRequestModel requestModel);
}
