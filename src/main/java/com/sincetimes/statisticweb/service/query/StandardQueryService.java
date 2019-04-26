package com.sincetimes.statisticweb.service.query;

import com.alibaba.fastjson.JSON;
import com.sincetimes.statisticweb.model.JqgridData;
import com.sincetimes.statisticweb.model.StandardQueryRequest;



public interface StandardQueryService {
    // query returns result
    public JqgridData getResult(StandardQueryRequest standardQueryRequest);
}
