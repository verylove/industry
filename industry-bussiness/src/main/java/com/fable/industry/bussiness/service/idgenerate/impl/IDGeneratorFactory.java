package com.fable.industry.bussiness.service.idgenerate.impl;

import com.fable.industry.bussiness.config.BeanLoader;
import com.fable.industry.bussiness.service.idgenerate.IdentifierGenerator;
import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import com.fable.industry.bussiness.service.idgenerate.StringGenerator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IDGeneratorFactory {

	private Map<Class, FieldGenerator> idGenCache = new ConcurrentHashMap<>();

	private Map<Class, FieldGenerator> idGenCacheStr = new ConcurrentHashMap<>();

	private static IDGeneratorFactory idGeneratorFactory;

	private DataSource dataSource = (DataSource) BeanLoader
			.getBean("dataSource");

	private PlatformTransactionManager txManager = (PlatformTransactionManager) BeanLoader
			.getBean("txManager");

	/** 产生序列化的表名 */
	private String sequenceTable ;

	private IDGeneratorFactory() {
	}

	public static IDGeneratorFactory getInstance() {
		if (idGeneratorFactory == null) {
			idGeneratorFactory = new IDGeneratorFactory();
		}
		return idGeneratorFactory;
	}

	public void getFieldGenerator(Class clazz, Object param) {
		//this.sequenceTable = table	;
		getGenerator(clazz,param);


	}

	public void getGenerator(Class clazz, Object param) {
		FieldGenerator fieldGenerator = idGenCache.get(clazz);
		FieldGenerator fieldGeneratorStr = idGenCacheStr.get(clazz);
		if(fieldGenerator != null && fieldGenerator.gen != null && fieldGeneratorStr == null){
			fieldGenerator.field.setAccessible(true);
			ReflectionUtils.setField(fieldGenerator.field, param, fieldGenerator.gen.generate());
			return;
		}else if (fieldGeneratorStr != null && fieldGeneratorStr.gen != null && fieldGenerator == null) {
			fieldGeneratorStr.field.setAccessible(true);
			ReflectionUtils.setField(fieldGeneratorStr.field, param, fieldGeneratorStr.gen.generate());
			return;
		}else if (fieldGeneratorStr != null && fieldGeneratorStr.gen != null && fieldGenerator.gen != null){
			fieldGenerator.field.setAccessible(true);
			ReflectionUtils.setField(fieldGenerator.field, param, fieldGenerator.gen.generate());
			fieldGeneratorStr.field.setAccessible(true);
			ReflectionUtils.setField(fieldGeneratorStr.field, param, fieldGeneratorStr.gen.generate());
			return;
		}
		
		IdentifierGenerator gen = null;
		IdentifierGenerator genStr = null;
		for (Field field : clazz.getDeclaredFields()) {
			Annotation annotation = field
					.getAnnotation(NumberGenerator.class);
			if (annotation != null ) {
				this.sequenceTable = "syskeygenerator";
				gen = new NumberGeneratorImpl(annotation, dataSource,
						txManager, sequenceTable);
				idGenCache.put(clazz, new FieldGenerator(field, gen));
				field.setAccessible(true);
				ReflectionUtils.setField(field, param, gen.generate());
				//tableCache.put(table,"1");
			} else {
				annotation = field.getAnnotation(StringGenerator.class);
				if (annotation != null) {
					this.sequenceTable = "sysgeneratorstr";
					genStr = new StringGeneratorImpl(annotation, dataSource,
							txManager, sequenceTable);
					idGenCacheStr.put(clazz, new FieldGenerator(field, genStr));
					field.setAccessible(true);
					ReflectionUtils.setField(field, param, genStr.generate());
					//tableCache.put(table,"1");
				}
			}
		}
	}

	public class FieldGenerator {
		public Field field;
		public IdentifierGenerator gen;
		
		public FieldGenerator(Field field, IdentifierGenerator gen) {
			this.field = field;
			this.gen = gen;
		}
	}

}
