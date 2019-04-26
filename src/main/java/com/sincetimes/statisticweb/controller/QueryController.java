package com.sincetimes.statisticweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sincetimes.statisticweb.model.JqgridData;
import com.sincetimes.statisticweb.model.StandardQueryRequest;
import com.sincetimes.statisticweb.service.query.DaySummaryService;
import com.sincetimes.statisticweb.service.query.TestService;
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


    @GetMapping("/query/day_summary")
    @ResponseBody
    public List<JSONObject> daySummary(@RequestParam(required = false) StandardQueryRequest standardQueryRequest
            , @Autowired DaySummaryService daySummary) {
        // return all basic query type
        return daySummary.getResult(standardQueryRequest).getData();
    }



    @RequestMapping(path="/query/test_query")
    @ResponseBody
    public List<JSONObject> testQuery(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      @RequestParam(required = false) String checkDate,
                                      @RequestParam(required = false) String[] channel,
                                      @RequestParam(required = false) String[] zoneid,
                                      @Autowired TestService testService,
                                      HttpServletRequest request){
        String date = request.getParameter("checkDate");
        StandardQueryRequest standardQueryRequest = new StandardQueryRequest();
        standardQueryRequest.setChannels(channel);
        standardQueryRequest.setCheckDate(checkDate);
        standardQueryRequest.setStartDate(startDate);
        standardQueryRequest.setEndDate(endDate);
        standardQueryRequest.setZoneids(zoneid);
        return testService.getResult(standardQueryRequest).getData();
    }


}
