package com.cjxz.resource;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sun.misc.IOUtils;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-15
 * @Version: 1.0
 */
public class TestResource {

    public static void main(String[] args) throws Exception{
        //使用Spring的Resource获得输入流
        Resource resource = new ClassPathResource("config.properties");
        InputStream in = resource.getInputStream();
        Properties p = new Properties();
        p.load(in);
        System.out.println(p.get("name"));
        System.out.println(p.get("id"));
    }
}
