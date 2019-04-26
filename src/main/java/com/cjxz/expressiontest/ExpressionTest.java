package com.cjxz.expressiontest;

import java.util.List;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-25
 * @Version: 1.0
 */
public class ExpressionTest {
    public static void main(String[] args) {
        ExpressionByObject();
//        ExpressionByMethod();
//        ExpressionByCollection();
        ExpressionByAnnotation();
    }

    public static void simpleDemo(){
        //类似eval函数
        ExpressionParser parser = new SpelExpressionParser();

        Expression exp = parser.parseExpression("'Hello'+'world'");

        String msg = (String)exp.getValue();

        System.out.println(msg);

        exp = parser.parseExpression("'hello world'.split(' ')");
        String[] arr = (String[])exp.getValue();

        exp = parser.parseExpression("com.cjxz.expressiontest.ExpressionTest.test()");
        msg = exp.getValue(String.class);
        System.out.println(msg);

        System.out.println(arr);
    }

    /**
     * 使用Expression获得对象里面的属性
     */
    public static void ExpressionByObject(){
        //创建ExpressionParser用来处理spEL
        ExpressionParser parser = new SpelExpressionParser();

        //执行spEL的上下文环境
        UserInfos userInfos = new UserInfos();
        userInfos.setUid(1);
        userInfos.setUsername("zhuchao");
        EvaluationContext context = new StandardEvaluationContext(userInfos);

        //这里使用InternalSpelExpressionParser(this.configuration).doParseExpression(expressionString, context);
        //返回的是SpelExpression
        Expression exp = parser.parseExpression("username");
        String userName = exp.getValue(context,String.class);
        System.out.println(userName);
    }

    /**
     * 执行对象的方法
     */
    public static void ExpressionByMethod(){
        //创建ExpressionParser用来处理spEL
        ExpressionParser parser = new SpelExpressionParser();

        //执行spEL的上下文环境
        UserInfos userInfos = new UserInfos();
        userInfos.setUid(1);
        userInfos.setUsername("zhuchao");
        EvaluationContext context = new StandardEvaluationContext(userInfos);

        Expression exp = parser.parseExpression("getInfo()");
        String userName = exp.getValue(context,String.class);
        System.out.println(userName);


    }

    /**
     * 执行集合对象
     */
    public static void ExpressionByCollection(){
        //创建ExpressionParser用来处理spEL
        ExpressionParser parser = new SpelExpressionParser();

        //执行spEL的上下文环境
        UserInfos userInfos = new UserInfos();
        EvaluationContext context = new StandardEvaluationContext(userInfos);

        Expression exp = parser.parseExpression("getList()");
        List<UserInfos> userName = exp.getValue(context, List.class);
        for(UserInfos temp : userName){
            System.out.println(temp.getInfo());
        }
    }

    public static void ExpressionByConfig(){
        ApplicationContext context = new ClassPathXmlApplicationContext("expression/services_expression.xml");
        UserInfos userInfos = context.getBean("user",UserInfos.class);
        System.out.println(userInfos.getInfo());
    }

    public static void ExpressionByAnnotation(){
        ApplicationContext context = new ClassPathXmlApplicationContext("expression/services_expression.xml");
        CopyUserInfos userInfos = context.getBean("user1",CopyUserInfos.class);
        System.out.println(userInfos.getInfo());
    }



    public static String test(){
        System.out.println(123);
        return "123";
    }
}
