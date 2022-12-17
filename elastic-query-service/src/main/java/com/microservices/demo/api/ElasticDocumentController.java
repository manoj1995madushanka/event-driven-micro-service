package com.microservices.demo.api;

import com.microservices.demo.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
    @Value("${server.port}")
    private String port;

    public ElasticDocumentController(ElasticQueryService elasticQueryService) {
        this.elasticQueryService = elasticQueryService;
    }

    @Operation(summary = "Get all elastic documents.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response.", content = {
                    @Content(mediaType = "application/vnd.api.v1+json",
                            schema = @Schema(implementation = ElasticQueryServiceResponseModel.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
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

    @GetMapping(value = "/{id}", produces = "application/vnd.api.v2+json")
    public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentByIdV2(@PathVariable @NotEmpty String id) {

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

        LOG.info("Elastic search returned {} of documents on port {}", response.size(),port);
        return ResponseEntity.ok(response);

    }
}
