package com.sincetimes.statisticweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sincetimes.statisticweb.model.StandardQueryRequest;
import com.sincetimes.statisticweb.model.TestRedis;
import com.sincetimes.statisticweb.service.query.DaySummaryService;
import com.sincetimes.statisticweb.service.query.TestCacheService;
import com.sincetimes.statisticweb.service.query.TestSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QueryController implements WebMvcConfigurer {

    @GetMapping("/query")
    @ResponseBody
    public String basics() {
        // return all basic query type
        return "list of basic query here";
    }

    @Autowired
    DaySummaryService daySummary;

    @GetMapping("/query/day_summary")
    @ResponseBody
    public List<JSONObject> daySummary(@RequestParam(required = false) StandardQueryRequest standardQueryRequest
            ) {
        // return all basic query type
        return daySummary.getResult(standardQueryRequest).getData();
    }

    @Autowired
    TestSqlService testSqlService;

    @RequestMapping(path="/query/test_query")
    @ResponseBody
    public JSONObject testQuery(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      @RequestParam(required = false) String checkDate,
                                      @RequestParam(required = false) String[] channel,
                                      @RequestParam(required = false) String[] zoneid,
                                      HttpServletRequest request){

        String date = request.getParameter("checkDate");
        StandardQueryRequest standardQueryRequest = new StandardQueryRequest();
        standardQueryRequest.setChannels(channel);
        standardQueryRequest.setCheckDate(checkDate);
        standardQueryRequest.setStartDate(startDate);
        standardQueryRequest.setEndDate(endDate);
        standardQueryRequest.setZoneids(zoneid);
        return testSqlService.getResult(standardQueryRequest).getJsonData();
    }

    @Autowired
    TestCacheService testCacheService;

    @RequestMapping(path="/query/test_cache")
    @ResponseBody
    public TestRedis testQueryCache(@RequestParam(required = false) Long id){
        return testCacheService.getTestById(id);
    }

    @PostMapping(value = "/insert_cache")
    @ResponseBody
    public TestRedis insertTest(@RequestBody TestRedis testRedis) {
        return testCacheService.saveTestInfo(testRedis);
    }

}
