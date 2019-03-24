package com.example.demo.provider;

import com.example.demo.core.Doc;
import com.example.demo.core.DocUsecase;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Component
public class DocProvider implements DocUsecase {

    private final RestHighLevelClient restHighLevelClient;

    public DocProvider(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Mono<Void> insertDocs(List<Doc> docs) {

        long startTime = System.currentTimeMillis();


        try {
            for (Doc doc : docs) {
                IndexRequest request = new IndexRequest("test_chosung", "doc");
                request.id(String.valueOf(doc.getId()));
                String jsonString = "{" +
                        "\"title\":\"" + doc.getTitle() + "\"" +
                        "}";
                request.source(jsonString, XContentType.JSON);
                restHighLevelClient.index(request);
                //System.out.println("Indexing : " + doc.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("took " + estimatedTime + " ms");


        return Mono.empty();
    }
}
