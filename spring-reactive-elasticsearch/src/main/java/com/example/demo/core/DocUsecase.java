package com.example.demo.core;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface DocUsecase {
    Mono<Void> insertDocs(List<Doc> docs);
}