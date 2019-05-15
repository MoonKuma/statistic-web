package com.sincetimes.statisticweb.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author MoonKuma
 * @since 2019/5/6
 *
 *
 * Redis test on saving in object(json)
 *
 * @Field(value="id") prevent mongodb to convert the field id into it's innate variable
 *
 * To make a class serializable, all those class it contains need to be serializable too.
 *
 * Guess what will happen if not?
 *
 * There will be a run time exception like "SerializationFailedException:Failed to serialize object using DefaultSerializer"
 * when trying to add it into cache.
 *
 * Compiler can't detect such error for 'implements Serializable' require nothing inside itself.
 * It's simply a mark to tell which object is allowed to be serialized
 *
 */


@Data
public class TestRedis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(value="id")
    private String id;

    @Field(value="name")
    private String name;

    /*
    private Test test;
    TestRedis(String name, String id){
        this.setId(id);
        this.setName(name);
        this.setTest(new Test(name));
    }
    public void report(){
        System.out.println  ("[Now reporting] " + this.test.toString());
    }
    */
    @Field(value="test")
    private Test test;

    public void reportName(){
        if (name!=null){
            System.out.println  ("[Now reporting name] " + this.name);
        }else{
            System.out.println  ("[Now reporting name] Empty");
        }

    }
}
