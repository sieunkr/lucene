package com.example.demo.web;

import com.example.demo.core.Content;
import com.example.demo.core.SearchUseCase;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final SearchUseCase searchUseCase;

    public HomeController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @PostMapping("/{index}/{id}")
    public Mono<Void> insert(@PathVariable(name = "index") String name,
                             @PathVariable(name = "id") Integer id,
                             @RequestBody Content content){

        //TODO: id equal content.getId()

        return searchUseCase.insertContent(content);
    }

    @DeleteMapping("/{index}/{id}")
    public Mono<Void> delete(@PathVariable(name = "index") String name,
                             @PathVariable(name = "id") Integer id){

        return searchUseCase.deleteContent(id);
    }

    @GetMapping("/{index}/_search")
    public Flux<Content> searchExactBySyno(@RequestParam(name = "q") String query){
        return searchUseCase.searchExactContentBySyno(query);
    }
}
