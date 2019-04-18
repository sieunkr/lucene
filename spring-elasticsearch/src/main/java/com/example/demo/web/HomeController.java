package com.example.demo.web;

import com.example.demo.core.Content;
import com.example.demo.core.SearchUseCase;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final SearchUseCase searchUseCase;

    public HomeController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @PostMapping
    @RequestMapping("/{index}/{id}")
    public Mono<Void> insert(@PathVariable(name = "index") String name,
                             @PathVariable(name = "index") String id,
                             @RequestBody Content content){

        return searchUseCase.insertContent(content);
    }
}
