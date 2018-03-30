package com.fable.industry.bussiness;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@SpringBootApplication
@MapperScan("com.fable.industry.bussiness.mapper")
public class IndustryUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndustryUiApplication.class, args);
	}

	/**
	 * mybatis配置
	 */
	@Configuration
	public class MybatisConf {
		@Bean
		public PageHelper pageHelper() {
			PageHelper pageHelper = new PageHelper();
			Properties p = new Properties();
			p.setProperty("offsetAsPageNum", "true");
			p.setProperty("rowBoundsWithCount", "true");
			p.setProperty("reasonable", "true");
			p.setProperty("pageSizeZero","true");
			p.setProperty("params","pageNum=start;pageSize=limit;");
			p.setProperty("supportMethodsArguments","true");
			p.setProperty("returnPageInfo","check");
			pageHelper.setProperties(p);
			return pageHelper;
		}
	}
}
