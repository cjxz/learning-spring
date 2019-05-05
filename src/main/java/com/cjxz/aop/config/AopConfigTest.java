package com.cjxz.aop.config;

import com.cjxz.aop.aspect.BusinessService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-05-05
 * @Version: 1.0
 */
public class AopConfigTest{
    public static void main(String[] args) {
        testConfig();
    }

    public static void testConfig(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop/services_aop_config.xml");
        BusinessService businessService = (BusinessService)context.getBean("businessService");
        businessService.createOrder();
    }
}
