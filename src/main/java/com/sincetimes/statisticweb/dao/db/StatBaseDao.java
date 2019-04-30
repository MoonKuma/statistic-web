package com.sincetimes.statisticweb.dao.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/4/24
 */

@Repository
public class StatBaseDao {

//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Autowired
//    public StatBaseDao(@Qualifier("statBaseNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public StatBaseDao(@Qualifier("statBaseNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    @Autowired
//    StatBaseDao(@Qualifier("statBaseNamedParameterJdbcTemplate") NamedParameterJdbcTemplate statBaseNamedParameterJdbcTemplate){
//        super.setNamedParameterJdbcTemplate(statBaseNamedParameterJdbcTemplate);
//    }

//    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }

    public List<Map<String, Object>> select(String sql, MapSqlParameterSource parameters){
        return namedParameterJdbcTemplate.queryForList(sql, parameters);
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
