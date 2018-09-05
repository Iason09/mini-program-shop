package com.iason.product.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Iason on 2018/9/3.
 */
@Slf4j
@Configuration
public class DruidConfig {

	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	@Value("${jdbc.driverClassName}")
	private String driverClassName;

	@Value("${druid.loginUsername}")
	private String loginUsername;
	@Value("${druid.loginPassword}")
	private String loginPassword;
	@Value("${druid.initialSize}")
	private int initialSize;
	@Value("${druid.minIdle}")
	private int minIdle;
	@Value("${druid.maxActive}")
	private int maxActive;
	@Value("${druid.maxWait}")
	private int maxWait;
	@Value("${druid.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${druid.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${druid.validationQuery}")
	private String validationQuery;
	@Value("${druid.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${druid.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${druid.testOnReturn}")
	private boolean testOnReturn;
	@Value("${druid.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${druid.filters}")
	private String filters;
	@Value("${druid.connectionProperties}")
	private String connectionProperties;
	@Value("${druid.useGlobalDataSourceStat}")
	private boolean useGlobalDataSourceStat;

	@Bean
	public FilterRegistrationBean statFilter() {
		//创建过滤器
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		//设置过滤器过滤路径
		filterRegistrationBean.addUrlPatterns("/*");
		//忽略过滤的形式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	@Bean
	public ServletRegistrationBean statViewServlet() {
		//创建servlet注册实体
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		//设置ip白名单
//		servletRegistrationBean.addInitParameter("allow","127.0.0.1");
		//设置ip黑名单，如果allow与deny共同存在时,deny优先于allow
//		servletRegistrationBean.addInitParameter("deny","192.168.0.19");
		//设置控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
		servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
		//是否可以重置数据
//		servletRegistrationBean.addInitParameter("resetEnable","false");
		return servletRegistrationBean;
	}

	@Bean     //声明其为Bean实例
	@Primary  //在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(this.url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		//configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			log.error("druid configuration initialization filter: " + e);
		}
		datasource.setConnectionProperties(connectionProperties);
		return datasource;
	}


}
