package com.sincetimes.statisticweb.service.query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sincetimes.statisticweb.dao.db.StatBaseDao;
import com.sincetimes.statisticweb.model.JqgridData;
import com.sincetimes.statisticweb.model.StandardQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/4/25
 */

@Service
public class TestService implements StandardQueryService {

    private final StatBaseDao statBaseDao;
    private JSONObject rowJsonObject;

    @Autowired
    public TestService(StatBaseDao statBaseDao) {
        this.statBaseDao = statBaseDao;
    }

    @Override
    public JqgridData getResult(StandardQueryRequest standardQueryRequest) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        JqgridData jqgridData = new JqgridData();
        String sql = "select date,item,value from test_table";
        sql += standardQueryRequest.getWhereClause(parameters);
        List<Map<String,Object>> sqlResults = statBaseDao.select(sql, parameters);
        for (Map<String,Object> sqlResult:sqlResults){
            rowJsonObject = new JSONObject();
            rowJsonObject.putAll(sqlResult);
            jqgridData.addJsonRow(rowJsonObject);
        }
        return jqgridData;
    }
}
