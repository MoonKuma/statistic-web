package com.sincetimes.statisticweb.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Test implements Serializable{

    private String name;

    Test(String name) {
        this.name = name;
    }
    Test(){}

    public String toString(){
        return "This is a test object with name:" + this.name;
    }
}

