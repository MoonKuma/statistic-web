package com.sincetimes.statisticweb.service.query;

import com.alibaba.fastjson.JSONObject;
import com.sincetimes.statisticweb.dao.mongo.TestMongoDao;
import com.sincetimes.statisticweb.model.TestRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MoonKuma
 * @since 2019/5/6
 *
 * Test cache data here as Redis input and output
 */

@CacheConfig(cacheNames = "test")
@Service
public class TestCacheService {

    private static final Logger logger = LoggerFactory.getLogger(TestCacheService.class);

    private final RedisTemplate redisTemplate;

    private final TestMongoDao testMongoDao;

    @Autowired
    public TestCacheService(RedisTemplate redisTemplate, TestMongoDao testMongoDao) {
        this.redisTemplate = redisTemplate;
        this.testMongoDao = testMongoDao;
    }

    @Cacheable(key = "#testId.toString()", condition = "#testId > 0", unless = "#result == null")
    public TestRedis getTestById(Long testId) {
        logger.debug("Mongo查询testId：" + testId);
        return testMongoDao.findTestById(testId);
    }

    @CachePut(key = "#testRedis.getId().toString()")
    public TestRedis saveTestInfo(TestRedis testRedis) {
        long delNum = testMongoDao.removeTest(testRedis.getId());
        if (logger.isDebugEnabled()) {
            logger.debug("removeTestInfo res:{}", delNum);
        }
        testMongoDao.insertTest(testRedis);
        return testRedis;
    }

    public TestRedis saveWithoutCache(TestRedis testRedis) {
        long delNum = testMongoDao.removeTest(testRedis.getId());
        if (logger.isDebugEnabled()) {
            logger.debug("removeTestInfo res:{}", delNum);
        }
        testMongoDao.insertTest(testRedis);
        return testRedis;
    }

    public TestRedis getTestByIdWithoutCache(Long testId) {
        logger.debug("Mongo查询testId：" + testId);
        return testMongoDao.findTestById(testId);
    }
}
