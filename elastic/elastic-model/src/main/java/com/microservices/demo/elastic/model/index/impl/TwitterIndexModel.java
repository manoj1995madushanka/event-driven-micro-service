package com.microservices.demo.elastic.model.index.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.demo.elastic.model.index.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * Document annotation indicates this class is a candidate for mapping to elasticsearch
 * */
@Data
@Builder
@Document(indexName="#{elasticConfigData.indexName}")
public class TwitterIndexModel implements IndexModel {

    @JsonProperty
    private String id;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String text;

    // field annotation used to convert java date to elastic date
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="uuuu-MM-dd'T'HH:mm:ssZZ" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonProperty
    private LocalDateTime createdAt;

}
