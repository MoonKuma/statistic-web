package com.sincetimes.statisticweb.dao.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author MoonKuma
 * @since 2019/4/24
 */

@Repository
public class StatBaseDao extends SqlDao{

    @Autowired
    StatBaseDao(@Qualifier("statBaseNamedParameterJdbcTemplate") NamedParameterJdbcTemplate statBaseNamedParameterJdbcTemplate){
        super.setNamedParameterJdbcTemplate(statBaseNamedParameterJdbcTemplate);
    }

}
