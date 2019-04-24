package com.cjxz.aop.aspect;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-24
 * @Version: 1.0
 */
public class BusinessServiceTest {
    public static void main(String[] args) {
        testByConfig();

    }
    //硬编码实现AOP
    public static void testByCoding(){
        BusinessService businessService = new BusinessService();
        //创建代理类的工厂
        AspectJProxyFactory factory = new AspectJProxyFactory();
        //设置目标对象
        factory.setTarget(businessService);
        //设置切面类
        factory.addAspect(OrderAspect.class);
        //从工厂中获得代理类
        BusinessService proxy = factory.getProxy();
        //这个对象已经不是上面new的businessService
        proxy.createOrder();
    }

    public static void testByConfig(){
        //重点代码：
        //org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessBeforeInstantiation
        /**
         * 1.资源定位，配置文件转换成BeanDefinition之后
         * 2.执行org.springframework.context.support.AbstractApplicationContext#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
         * 2.1.在这个方法里面会实例org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
         * 2.2.实例之后因为AnnotationAwareAspectJAutoProxyCreator继承了BeanPostProcessor
         * 2.3.需要实现两个方法postProcessBeforeInitialization和postProcessAfterInitialization
         * 2.4.其中postProcessAfterInitialization就是生成代理类的入口
         * 3.执行org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
         * 3.1.会实例所有单例模式的对象，因为BusinessService是单例模式，所以再这步就要实例对象
         * 3.2.正常实例BusinessService，填充属性值
         * 3.3.填充完属性之后执行这句：exposedObject = initializeBean(beanName, exposedObject, mbd);
         * 3.4.开始生成代理对象，上面这步在Bean的生命周期中还是非常重要的
         * 3.5.在执行中会执行org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#postProcessAfterInitialization(java.lang.Object, java.lang.String)
         * 3.6.也就是前面提到的BeanPostProcessor接口中的方法
         * 3.7.org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#wrapIfNecessary(java.lang.Object, java.lang.String, java.lang.Object)
         * 3.8.上面的方法就是判断是否需要生成代理类的方法，判断依据是当前正在实例的对BusinessService是否满足切面要求
         * 3.9.当调用isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)时判断是切面类，保存到切面类集合中
         * 3.10.满足要求则通过ObjenesisCglibAopProxy来创建代理对象
         * 4.开始调用对象并执行
         * 4.1按要求进入代理类中
         */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop/services_aop.xml");
        BusinessService businessService = (BusinessService)context.getBean("businessService");
        businessService.createOrder();
    }
}
