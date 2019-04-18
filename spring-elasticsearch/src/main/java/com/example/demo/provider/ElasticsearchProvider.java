package com.example.demo.provider;

import com.example.demo.core.Content;
import com.example.demo.core.ElasticsearchDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class ElasticsearchProvider implements ElasticsearchDetails {

    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchProvider(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Flux<Content> searchExactContentBySyno() {
        return null;
    }

    @Override
    public Mono<Void> insertContent(Content content) {

        //TODO : Mono.create

        IndexRequest request = new IndexRequest("content","content");
        request.id(String.valueOf(content.getId()));
        ObjectMapper mapper = new ObjectMapper();
        try {
            request.source(mapper.writeValueAsString(content), XContentType.JSON);
            restHighLevelClient.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Mono<Void> deleteContent(Integer id) {
        return null;
    }
}
