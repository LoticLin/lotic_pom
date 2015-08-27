package lotic.lin.core.webconfig;

import lotic.lin.core.webglobal.SystemConstants;
import lotic.lin.core.webglobal.SystemInitProcessor;
import lotic.lin.utils.LogUtils;
import lotic.lin.utils.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@PropertySource({ "classpath:"+SystemConstants.CONFIG_FILE_APPLICATION })
@EnableScheduling

@EnableAspectJAutoProxy(proxyTargetClass=true)  
@ComponentScan(basePackages = { SystemConstants.SCAN_COMPONENT_SERVICE_BASEPATH})
public class AppConfig extends WebMvcConfigurationSupport implements EnvironmentAware {
	private Environment env;
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	
  
  

	/**
	 * spring 启动后执行一些初始化工作
	 * 
	 * @return
	 */
	@Bean
	public SystemInitProcessor beanFactoryPostProcessor() {
		String processor = env.getProperty(SystemInitProcessor.KEY);
		if(StringUtils.isNoneBlank(processor)){
			try {
				Object obj = Class.forName(processor).newInstance();
				if(obj instanceof SystemInitProcessor) {
					LogUtils.printLog("Set SystemInitProcessor:" + obj.getClass().getName());
					return (SystemInitProcessor)obj;
				} else {
					LogUtils.printLog(processor+":Not SystemInitProcessor Type! ");
				}
			} catch (Exception e) {				
				LogUtils.printLog(processor, e);
			}
		}
		LogUtils.printLog("Set Default SystemInitProcessor");
		return new SystemInitProcessor();
		
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {

		RequestMappingHandlerMapping handlerMapping = super
				.requestMappingHandlerMapping();

		handlerMapping.setUseSuffixPatternMatch(false);

		handlerMapping.setUseTrailingSlashMatch(false);

		return handlerMapping;

	}
	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.env = environment;
		
	}
}