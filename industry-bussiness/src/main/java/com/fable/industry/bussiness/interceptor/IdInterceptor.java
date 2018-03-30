package com.fable.industry.bussiness.interceptor;

import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory;
import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory.FieldGenerator;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.ReflectionUtils;

public class IdInterceptor{
	public void dynamicSetId(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object param : arguments){
        	IDGeneratorFactory.getInstance().getFieldGenerator(param.getClass(),param);
/*        	if(fieldGen != null){
        		fieldGen.field.setAccessible(true);
        		ReflectionUtils.setField(fieldGen.field, param, fieldGen.gen.generate());
        	}*/
        }
	}
}
