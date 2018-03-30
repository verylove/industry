package com.fable.industry.bussiness.config;

import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author jiangmingjun
 * @create 2018/3/30
 * @description 动态id及动态编目切面类
 */
@Aspect
@Component
public class DynamicSetAspect {

    @Pointcut("execution(* com.fable.industry.bussiness.mapper..*.insert*(..))")
    public void dynamincSet() {

    }

    @Before("dynamincSet()")
    public void dobefore(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object param : arguments){
            IDGeneratorFactory.getInstance().getFieldGenerator(param.getClass(),param);
        }
    }
}
