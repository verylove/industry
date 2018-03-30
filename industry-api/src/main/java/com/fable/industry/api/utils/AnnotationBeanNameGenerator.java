package com.fable.industry.api.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class AnnotationBeanNameGenerator extends
		org.springframework.context.annotation.AnnotationBeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		String beanName = super.generateBeanName(definition, registry);
		return getFullBeanName(definition.getBeanClassName(), beanName);
	}
	
	public static String getFullBeanName(String className, String beanName){
		String[] fields = className.split("\\.");
		beanName = fields[3] + "." + beanName;
		return beanName;
	}
}
