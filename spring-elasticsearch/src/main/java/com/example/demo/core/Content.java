package com.example.demo.core;

import java.util.List;

//TODO:Lombok
public class Content {

    private Integer id;
    private String name;
    private List<String> syno;
    private String description;

    public Content(){
    }

    public Content(String name, List<String> syno, String description){
        this.name = name;
        this.syno = syno;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSyno() {
        return syno;
    }

    public void setSyno(List<String> syno) {
        this.syno = syno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
