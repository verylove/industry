package com.fable.industry.bussiness.interceptor;

import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory;
import com.fable.industry.bussiness.service.idgenerate.impl.IDGeneratorFactory.FieldGenerator;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.ReflectionUtils;

import java.util.List;

public class BatchIdsInterceptor {
	@SuppressWarnings("rawtypes")
	public void dynamicSetIds(JoinPoint joinPoint) {
		Object[] arguments = joinPoint.getArgs();
		for (Object param : arguments) {
			if (param instanceof List) {
				for (Object entity : (List) param) {
					setIdentifer(entity);
				}
			} else {
				setIdentifer(param);
			}
		}
	}
	private void setIdentifer(Object entity) {
		IDGeneratorFactory.getInstance().getFieldGenerator(entity.getClass(),entity);
//		if (fieldGen != null) {
//			fieldGen.field.setAccessible(true);
//			ReflectionUtils.setField(fieldGen.field, entity, fieldGen.gen.generate());
//		}
	}
}
