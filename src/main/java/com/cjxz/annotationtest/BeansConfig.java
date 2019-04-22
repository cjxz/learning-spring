package com.cjxz.annotationtest;

import com.cjxz.baseData.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-21
 * @Version: 1.0
 */
@Configuration
public class BeansConfig {
    @Bean(name="userService")
    public UserService getUserService(){
        UserService userService = new UserService();
        userService.setId(1);
        userService.setName("Bean");
        return userService;
    }
}
