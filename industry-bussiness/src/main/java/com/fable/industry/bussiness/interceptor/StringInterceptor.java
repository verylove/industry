package com.fable.industry.bussiness.interceptor;

import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory;
import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory.FieldGenerator;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.ReflectionUtils;

/**
 * @auther jiangmingjun
 * @create 2018/2/1
 */
public class StringInterceptor {
//    public void dynamicSetAuto(JoinPoint joinPoint){
//        Object[] arguments = joinPoint.getArgs();
//        for(Object param : arguments){
//            FieldGenerator fieldGen = IDGeneratorFactory.getInstance().getFieldGenerator(param.getClass());
//            if(fieldGen != null){
//                fieldGen.field.setAccessible(true);
//                ReflectionUtils.setField(fieldGen.field, param, fieldGen.gen.generate());
//            }
//        }
//    }
}
