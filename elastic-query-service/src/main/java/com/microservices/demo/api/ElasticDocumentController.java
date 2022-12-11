package com.microservices.demo.api;

import com.microservices.demo.business.ElasticQueryService;
import com.microservices.demo.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.model.ElasticQueryServiceResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RestController
//URL versioning
//@RequestMapping(path = "/documents/v1")
//media type versioning (applicationHeader="application/vnd.api.v1+json" in postman)
// @RequestMapping(path = "/documents/v1" , produces = "application/vnd.api.v1+json")
@RequestMapping(path = "/documents/v1")
public class ElasticDocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);
    private final ElasticQueryService elasticQueryService;

    public ElasticDocumentController(ElasticQueryService elasticQueryService) {
        this.elasticQueryService = elasticQueryService;
    }

    @GetMapping("/")
    //@ResponseBody used to serialize java response object to json, but it is not required when we add @RestController
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
        LOG.info("Elasticsearch returned {} of documents.", response.size());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id) {

        ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
        LOG.info("Elasticsearch returned document with id {}.", id);

        return ResponseEntity.ok(response);
    }

    // @RequestBody annotation used to deserialize json into java object
    @PostMapping("/get-document-by-text")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
            @RequestBody @Valid ElasticQueryServiceRequestModel requestModel
    ) {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentsByText(requestModel.getText());

        LOG.info("Elastic search returned {} of documents", response.size());
        return ResponseEntity.ok(response);

    }
}
