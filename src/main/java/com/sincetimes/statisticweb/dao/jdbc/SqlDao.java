package com.sincetimes.statisticweb.dao.jdbc;



import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/4/23
 */

@CacheConfig(cacheNames = "sql")
public class SqlDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Map<String, Object>> select(String sql, MapSqlParameterSource parameters){
        return namedParameterJdbcTemplate.queryForList(sql, parameters);
    }

    // Adding those result from sql with not parameters into Cache
    // Insert and delete does not work well for their original return is an int value indicate the line number
    // Stupid idea of using KV cache inside a SQL jdbc
    @Cacheable(key = "#sql.toString()", condition = "#parameters.getValues().size() == 0", unless = "#result == null")
    public List<Map<String, Object>> selectCache(String sql, MapSqlParameterSource parameters){
        return this.select(sql, parameters);
    }

    // insert
    // the difference between insert and delete in jdbc level is controlled inside the sql commend
    // eg : insert into table test (DATE,VALUE) values (:date,:value)
    // eg: delete from test where DATE=(:date)
    public int insert(String sql, MapSqlParameterSource parameters){
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    // delete
    public int delete(String sql, MapSqlParameterSource parameters){
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    // batch update
    public int[] batchUpdate(String sql, MapSqlParameterSource[] parametersList){
        return namedParameterJdbcTemplate.batchUpdate(sql, parametersList);
    }


}
