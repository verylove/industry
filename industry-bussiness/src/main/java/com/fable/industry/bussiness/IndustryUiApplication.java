package com.fable.industry.bussiness;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.mapping.Environment;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
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

	/**
	 * 数据源配置
	 */
	@ComponentScan
	@Configuration
	public class ApplicationConfig {

		@Bean(name = "dataSource")
		@ConfigurationProperties(prefix = "spring.datasource")
		@Primary
		public DataSource dataSource() {
			return DataSourceBuilder.create().build();
		}

		@Bean(name = "txManager")
		public PlatformTransactionManager txManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}

	}
}
