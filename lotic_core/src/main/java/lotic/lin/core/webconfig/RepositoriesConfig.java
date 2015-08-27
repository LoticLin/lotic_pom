package lotic.lin.core.webconfig;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


import lotic.lin.core.webglobal.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource({ "classpath:" + SystemConstants.CONFIG_FILE_DS })
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = SystemConstants.SCAN_COMPONENT_REPOSITORIES_BASEPATH)
public class RepositoriesConfig {
	@Autowired
	private Environment env;

	@Bean
	public PlatformTransactionManager transactionManager()

	{

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;

	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		vendorAdapter.setShowSql(Boolean.parseBoolean(env
				.getProperty("hibernate.show_sql")));
		vendorAdapter.setDatabase(Database.MYSQL);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(SystemConstants.SCAN_REPOSITORIES_ENTITY_BASEPATH);
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		// factory.setPersistenceXmlLocation("classpath:persistence.xml");
		Properties  jpaProperties =new Properties();
	
		jpaProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.provider_configuration", "/ehcache.xml");
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.setProperty("hibernate.cache.use_query_cache", "true");
		factory.setJpaProperties(jpaProperties);
		return factory.getObject();
	}

	@Bean
	public DataSource dataSource() {

		// 内存数据库
		// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		// return builder.setType(EmbeddedDatabaseType.HSQL).build();

		ComboPooledDataSource c3p0 = new ComboPooledDataSource();
		try {
			 c3p0.setDriverClass(env.getProperty("jdbc_driverClass"));
			 c3p0.setJdbcUrl(env.getProperty("jdbc_url"));
			 c3p0.setUser(env.getProperty("jdbc_username"));
			 c3p0.setPassword(env.getProperty("jdbc_password"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return c3p0;
	}

}
