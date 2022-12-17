package com.microservices.demo.elastic.query.web.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/*
* ElasticQueryWebClientRequestModel
* */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EQWCRequestModel {
    private String id;
    @NotEmpty
    private String text;
}
