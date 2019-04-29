package com.cjxz.jdbctest;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-29
 * @Version: 1.0
 */
@Component("jdbcTest")
public class JdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void test(){
        List<Map<String, Object>> data =  jdbcTemplate.queryForList("select * from fp_manufacturer_project WHERE mp_id < 20");
        System.out.println(JSON.toJSONString(data));
    }
}
