package com.microservices.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ElasticQueryServiceResponseModel {

    private String id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;

}
