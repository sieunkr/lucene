package com.example.demo.provider;

import com.example.demo.core.Content;
import com.example.demo.core.ElasticsearchDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
    public Flux<Content> searchExactContentBySyno(String query) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("syno.keyword",query));

        return getContentFlux("content", searchSourceBuilder);
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

        try {
            DeleteRequest request = new DeleteRequest("content","content",String.valueOf(id));
            restHighLevelClient.delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Mono.empty();
    }

    private Flux<Content> getContentFlux(String index, SearchSourceBuilder searchSourceBuilder) {

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        return Flux.create(sink -> {
            ActionListener<SearchResponse> actionListener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse searchResponse) {

                    for (SearchHit hit : searchResponse.getHits()) {

                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            Content content = objectMapper.readValue(hit.getSourceAsString(), Content.class);
                            sink.next(content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    sink.complete();
                }

                @Override
                public void onFailure(Exception e) {
                }
            };

            restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, actionListener);
        });
    }
}
