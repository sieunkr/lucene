package com.example.demo.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ElasticsearchDetails {

    Flux<Content> searchExactContentBySyno(String query);
    Mono<Void> insertContent(Content content);
    Mono<Void> deleteContent(Integer id);
}
