package com.cjxz.conditionaltest;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-22
 * @Version: 1.0
 */
public class MyConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String,Object> data = metadata.getAnnotationAttributes("com.cjxz.conditionaltest.MyInfo");
        if(data != null){
            System.out.println(JSON.toJSONString(data));
            return true;
        }else{
            return false;
        }
    }
}
