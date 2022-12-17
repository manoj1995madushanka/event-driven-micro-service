package com.microservices.demo.elastic.query.web.client.api;

import com.microservices.demo.elastic.query.web.client.model.EQWCRequestModel;
import com.microservices.demo.elastic.query.web.client.model.EQWCResponseModel;
import com.microservices.demo.elastic.query.web.client.service.ElasticQueryWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// we can not use rest controller here because we are using this endpoint in themeleaf
// we use Model class to communicate with thymeleaf template
@Controller
public class QueryController {

    private static final Logger LOG = LoggerFactory.getLogger(QueryController.class);

    // this is the object we communicate with the tymeleaf template
    private final ElasticQueryWebClient elasticQueryWebClient;


    public QueryController(ElasticQueryWebClient elasticQueryWebClient) {
        this.elasticQueryWebClient = elasticQueryWebClient;
    }

    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("eQWCRequestModel", EQWCRequestModel.builder().build());
        return "home";
    }

    @PostMapping("/query-by-text")
    public String queryByText(@Valid EQWCRequestModel requestModel,
                              Model model) {
        LOG.info("Querying with text {}", requestModel.getText());
        List<EQWCResponseModel> responseModels =elasticQueryWebClient.getDataByText(requestModel);
//        responseModels.add(EQWCResponseModel.builder()
//                .id("1")
//                .text(requestModel.getText())
//                .build());
        model.addAttribute("elasticQueryWebClientResponseModels", responseModels);
        model.addAttribute("searchText", requestModel.getText());
        model.addAttribute("elasticQueryWebClientRequestModel",
                EQWCRequestModel.builder().build());
        return "home";
    }

}
