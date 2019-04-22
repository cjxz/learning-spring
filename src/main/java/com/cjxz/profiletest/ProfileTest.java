package com.cjxz.profiletest;

import com.alibaba.fastjson.JSON;
import com.cjxz.baseData.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-16
 * @Version: 1.0
 */
public class ProfileTest {
    public static void main(String[] args) {
        String location = "services_profile.xml";
        //实例contex对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        //自定义环境
        StandardEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("test");
        //将自定义的环境设置到context中
        context.setEnvironment(environment);
        //设置要加载的xml文件，并且调用refresh方法开始初始化容器
        context.setConfigLocation(location);
        context.refresh();
        //获取环境变量
        System.out.println(JSON.toJSONString(context.getEnvironment()));
        OrderService orderService = context.getBean("orderService", OrderService.class);
        orderService.createOrder();
    }
}
