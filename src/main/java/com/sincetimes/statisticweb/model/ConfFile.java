package com.sincetimes.statisticweb.model;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;

/**
 * @author MoonKuma
 * @since 2019/4/23
 */

@Data
public class ConfFile {
    private HashMap<String, HashMap<String,String>> confMap = new HashMap<String, HashMap<String,String>>();
    private HashMap<Integer,String> headerMap = new HashMap<Integer, String>();
    private String delimiter = ",";
    private int headline = 1;
    private ClassPathResource resource;

    public String toString(){
        return "[Report] Your ConfFile now have a confMap with length: " +
                confMap.keySet().size();
    }

}
