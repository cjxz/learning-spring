package com.cjxz.jdbctest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-05-04
 * @Version: 1.0
 */
@Component("transactionByAnnotationTest")
@Transactional
public class TransactionByAnnotationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createData(){
        String insertSql = "insert into b_user (name, password, level, des, tel, address) VALUES (\n" +
                "    'aa1','123456',1,'test','13200000000','武汉'\n" +
                ")";
        jdbcTemplate.execute(insertSql);
        System.out.println("插入用户完毕");
        double i = 1/0;
        String insertSql2 = "insert into b_role (id, name) VALUES (2,'admin2')";
        jdbcTemplate.execute(insertSql2);
    }
}
