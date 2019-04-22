package com.cjxz.annotationtest;

import com.alibaba.fastjson.JSON;
import com.cjxz.baseData.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-21
 * @Version: 1.0
 */
public class TestAnnotationBeanConfig {
    public static void main(String[] args) {
        /**
         * 在spring3.x之后提供的@Configuration用来替代XML配置文件
         * 但是使用@Configuration注解的配置类又由谁来加载呢？
         * 这是spring提供了AnnotationConfigApplicationContext来装配
         * 在构造函数上面有如果使用AnnotationConfigApplicationContext(java.lang.Class[])
         * 的方式那么Class对象上面是可以不添加@Configuration注解
         * 如果使用basePackage的方式那么必须添加@Configuration注解
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.cjxz.annotationtest");
//        context.register(BeansConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(JSON.toJSONString(userService));
    }
}
