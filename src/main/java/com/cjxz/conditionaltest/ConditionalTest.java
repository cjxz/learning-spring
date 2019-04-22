package com.cjxz.conditionaltest;

import com.alibaba.fastjson.JSON;
import com.cjxz.baseData.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-22
 * @Version: 1.0
 */
public class ConditionalTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.cjxz.conditionaltest");
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(JSON.toJSONString(userService));
    }
}
