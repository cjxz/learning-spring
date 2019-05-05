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
//        testTransactionByAnnotation();
        String code = String.format("%08d",123);
        System.out.println(code);
    }

    public static void testTransaction(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/services_jdbc.xml");
        TransactionTest test = context.getBean("transactionTest",TransactionTest.class);
        test.createData();
    }

    public static void testTransactionByAnnotation(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/services_annotation_jdbc.xml");
        TransactionByAnnotationTest test = context.getBean("transactionByAnnotationTest",TransactionByAnnotationTest.class);
        test.createData();
    }

    public static void testJdbc(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/services_jdbc.xml");
        JdbcTest test = context.getBean("jdbcTest",JdbcTest.class);
        test.test();
    }
}
