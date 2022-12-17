package com.microservices.demo.elastic.query.service.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

/**
 * extending class is responsible to generate hatoes links
 * for get this functionality we need to make assembler
 * here we created that called ElasticQueryServiceResponseModelAssembler class
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ElasticQueryServiceResponseModel extends RepresentationModel<ElasticQueryServiceResponseModel> {

    private String id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;

}
