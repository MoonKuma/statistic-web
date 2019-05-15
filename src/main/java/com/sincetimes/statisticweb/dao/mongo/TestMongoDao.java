package com.sincetimes.statisticweb.dao.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.sincetimes.statisticweb.model.TestRedis;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/5/13
 *
 * BasicDBObject basicDBObject = mongoTemplate.findOne(qResult, BasicDBObject.class, collectionName);
 *
 * This report a cast error, as it is not able to cast Document into BasicDBObject.class
 *
 * However Document could be cast into other class like Map, or even those home-made class marked by @Data,
 * like TestRedis here
 */

@Repository
public class TestMongoDao {

    private static final String collectionName = "test";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TestMongoDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public TestRedis findTestById(Long testId) {
        Criteria c = Criteria.where("id").is(testId.toString());
        Query qResult = Query.query(c);
        //TestRedis testRedis = mongoTemplate.findOne(qResult, TestRedis.class, collectionName);

        //BasicDBObject basicDBObject = mongoTemplate.findOne(qResult, BasicDBObject.class, collectionName);
        //Document basicDBObject = mongoTemplate.findOne(qResult, Document.class, collectionName);
        //Map basicDBObject = mongoTemplate.findOne(qResult, Map.class, collectionName);
        TestRedis testRedis = mongoTemplate.findOne(qResult, TestRedis.class, collectionName);

        /*
        TestRedis testRedis = new TestRedis();
        if (basicDBObject != null) {
            testRedis.setId(basicDBObject.get("id").toString());
            testRedis.setName(basicDBObject.get("name").toString());
            return testRedis;
        }
        */

        return testRedis;
    }

    public long removeTest(String testId) {
        Criteria c = Criteria.where("id").is(testId);
        return mongoTemplate.remove(Query.query(c), collectionName).getDeletedCount();
    }

    public void insertTest(TestRedis testRedis) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", testRedis.getId());
//        jsonObject.put("name", testRedis.getName());
//        BasicDBObject basicDBObject = new BasicDBObject(jsonObject);
//        mongoTemplate.insert(basicDBObject, collectionName);

        mongoTemplate.insert(testRedis, collectionName);

    }

}
