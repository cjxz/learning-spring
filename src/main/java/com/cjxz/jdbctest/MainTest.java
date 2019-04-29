package com.cjxz.jdbctest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-29
 * @Version: 1.0
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/services_jdbc.xml");
        JdbcTest test = context.getBean("jdbcTest",JdbcTest.class);
        test.test();
    }
}
