package com.cjxz.baseData;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/12
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        OrderService orderService = context.getBean("orderService",OrderService.class);
        orderService.createOrder();

    }
}
