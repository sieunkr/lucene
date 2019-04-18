package com.example.demo.core;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SearchUseCase {

    private final ElasticsearchDetails elasticsearchDetails;

    public SearchUseCase(ElasticsearchDetails elasticsearchDetails) {
        this.elasticsearchDetails = elasticsearchDetails;
    }

    public Mono<Void> insertContent(Content content){
        elasticsearchDetails.insertContent(content);

        //TODO:Mono.empty() ?? or Mono.create..
        return null;
    }

}
