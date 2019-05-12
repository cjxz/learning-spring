package com.cjxz.resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import sun.misc.IOUtils;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-15
 * @Version: 1.0
 */
public class TestResource {

    public static void main(String[] args) throws Exception{


        testPathMatchResource();
    }

    public static void testFileUrlResource() throws Exception {
//        URL url = new URL("https://www.springframework.org/schema/beans/spring-beans.xsd");
        FileUrlResource resource = new FileUrlResource("/Users/zhuchao/learning-spring/src/main/resources/sax/Myconfig-demo.xml");
        InputStream in = resource.getInputStream();

        byte[] bytes = IOUtils.readFully(in,-1,true);
        String s = new String(bytes,"utf-8");
        System.out.println(s);
    }

    public static void testClassPathResource()throws Exception{
        //使用Spring的Resource获得输入流
        Resource resource = new ClassPathResource("config.properties");
        InputStream in = resource.getInputStream();
        Properties p = new Properties();
        p.load(in);
        System.out.println(p.get("name"));
        System.out.println(p.get("id"));
    }

    public static void testFileSystemResource()throws Exception{
        FileSystemResource fileSystemResource = new FileSystemResource("/Users/zhuchao/spring-framework/spring-framework/spring-context/src/main/resources/META-INF/spring.schemas");
        String fileName = fileSystemResource.getFilename();
        System.out.println(fileName);

        InputStream in = fileSystemResource.getInputStream();
        byte[] bytes = IOUtils.readFully(in,-1,true);
        String s1 = new String(bytes,"utf-8");
        System.out.println(s1);
    }

    public static void testUrlResource()throws Exception{
        UrlResource urlResource = new UrlResource("https://www.springframework.org/schema/beans/spring-beans.xsd");
        String fileName  = urlResource.getFilename();
        System.out.println("文件名称："+fileName);
        InputStream in = urlResource.getInputStream();

        byte[] bytes = IOUtils.readFully(in,-1,true);
        String s = new String(bytes,"utf-8");
        System.out.println(s);
    }

    public static void testPathMatchResource() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("fileResource/services_*.xml");
        for(Resource resource : resources){
            System.out.println("解析文件开始");
            InputStream in = resource.getInputStream();
            byte[] bytes = IOUtils.readFully(in,-1,true);
            String s = new String(bytes,"utf-8");
            System.out.println(s);
        }
    }

}
