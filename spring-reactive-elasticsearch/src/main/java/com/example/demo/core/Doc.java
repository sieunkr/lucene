package com.example.demo.core;

import lombok.Data;

@Data
public class Doc {

    private Integer id;
    private String title;


    public Doc(){

    }

    public Doc(Integer id, String title){
        this.id = id;
        this.title = title;
    }
}
