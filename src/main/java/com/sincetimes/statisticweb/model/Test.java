package com.sincetimes.statisticweb.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Test {

    private String name;

    Test(String name) {
        this.name = name;
    }
    Test(){}

    public String toString(){
        return "This is a test object with name:" + this.name;
    }
}
