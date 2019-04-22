package com.cjxz.conditionaltest;

import com.cjxz.baseData.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-22
 * @Version: 1.0
 */
@Configuration
@Conditional(MyConditional.class)
@MyInfo(desc="测试一下注解")
public class ConditionalConfig {
    @Bean(name="userService")
    public UserService getUserService(){
        UserService userService = new UserService();
        userService.setId(1);
        userService.setName("Bean");
        return userService;
    }
}
