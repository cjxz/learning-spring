package com.cjxz.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-24
 * @Version: 1.0
 */
@Aspect
public class OrderAspect {
    @Before("execution(* createOrder(..))")
    public void beforeCreateOrder(){
        System.out.println("开启事务");
    }
}
