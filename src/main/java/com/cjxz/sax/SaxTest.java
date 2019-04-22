package com.cjxz.sax;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-15
 * @Version: 1.0
 */
public class SaxTest {
    public static void main(String[] args) throws Exception{
        //使用spring提供的Resource获得资源
        Resource resource = new ClassPathResource("/sax/Myconfig-demo.xml");
        InputStream in = resource.getInputStream();

        //jaxp 基本套路
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
//        documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage","http://www.w3.org/2001/XMLSchema");

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        EntityResolver entityResolver = new MyEntityResolver();
        documentBuilder.setEntityResolver(entityResolver);
        Document document = documentBuilder.parse(in);

        //遍历节点
        foreachDocument(document);
    }

    public static void foreachDocument(Document document){
        Element element = document.getDocumentElement();
        Node node = element;
        System.out.println(element.getNodeName());
        System.out.println(node.getNamespaceURI());
    }
}
