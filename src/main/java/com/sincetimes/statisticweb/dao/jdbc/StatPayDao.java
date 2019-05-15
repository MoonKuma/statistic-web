package com.sincetimes.statisticweb.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author MoonKuma
 * @since 2019/4/25
 */

@Repository
public class StatPayDao extends SqlDao {

    @Autowired
    StatPayDao(@Qualifier("statPayNamedParameterJdbcTemplate") NamedParameterJdbcTemplate statBaseNamedParameterJdbcTemplate){
        super.setNamedParameterJdbcTemplate(statBaseNamedParameterJdbcTemplate);
    }
}
