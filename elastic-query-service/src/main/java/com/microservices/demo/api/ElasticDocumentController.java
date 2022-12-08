package com.microservices.demo.api;

import com.microservices.demo.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/documents")
public class ElasticDocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

    @GetMapping("/")
    //@ResponseBody used to serialize java response object to json, but it is not required when we add @RestController
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> response = new ArrayList<>();
        LOG.info("Elasticsearch returned {} of documents.", response.size());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable String id) {

        ElasticQueryServiceResponseModel response = ElasticQueryServiceResponseModel.builder()
                .id(id)
                .build();
        LOG.info("Elasticsearch returned document with id {}.", id);

        return ResponseEntity.ok(response);
    }

    // @RequestBody annotation used to deserialize json into java object
    @PostMapping("/get-document-by-text")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
            @RequestBody ElasticQueryServiceRequestModel requestModel
    ) {
        List<ElasticQueryServiceResponseModel> response = new ArrayList<>();
        ElasticQueryServiceResponseModel responseModel = ElasticQueryServiceResponseModel.builder()
                .text(requestModel.getText())
                .build();
        response.add(responseModel);

        LOG.info("Elastic search returned {} of documents", response.size());
        return ResponseEntity.ok(response);

    }
}
