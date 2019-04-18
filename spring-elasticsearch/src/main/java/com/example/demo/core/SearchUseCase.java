package com.example.demo.core;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SearchUseCase {

    private final ElasticsearchDetails elasticsearchDetails;

    public SearchUseCase(ElasticsearchDetails elasticsearchDetails) {
        this.elasticsearchDetails = elasticsearchDetails;
    }

    public Mono<Void> insertContent(Content content){
        return elasticsearchDetails.insertContent(content);
    }

    public Mono<Void> deleteContent(Integer id){
        return elasticsearchDetails.deleteContent(id);
    }

    public Flux<Content> searchExactContentBySyno(String query){
        return elasticsearchDetails.searchExactContentBySyno(query);
    }

}
