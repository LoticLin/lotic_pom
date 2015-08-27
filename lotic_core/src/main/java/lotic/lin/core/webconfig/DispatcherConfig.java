package lotic.lin.core.webconfig;
import lotic.lin.core.webglobal.SystemConstants;
import lotic.lin.core.webglobal.RequestExceptionHandler;
import lotic.lin.core.webglobal.RequestInterceptor;
import lotic.lin.utils.LogUtils;
import lotic.lin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { SystemConstants.SCAN_CONTROLLER_BASEPATH})
public class DispatcherConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private Environment env;
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).favorParameter(true);
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");

	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(new String[] { "/resources/**" })
				.addResourceLocations(new String[] { "/resources/" });
	}

	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();

	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor());
		registry.addInterceptor(localeChangeInterceptor());

	}

	/**
	 * View Resolver
	 * 
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean(name = { "messageSource" })
	public MessageSource configureMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:i18n/messages");

		messageSource.setCacheSeconds(5);

		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		String processor = env.getProperty(RequestInterceptor.KEY);
		if(StringUtils.isNoneBlank(processor)){
			try {
				Object obj = Class.forName(processor).newInstance();
				if(obj instanceof RequestInterceptor) {
					LogUtils.printLog("Set RequestInterceptor:" + obj.getClass().getName());
					return (RequestInterceptor)obj;
				} else {
					LogUtils.printLog(processor+":Not RequestInterceptor Type! ");
				}
			} catch (Exception e) {				
				LogUtils.printLog(processor, e);
			}
		}
		LogUtils.printLog("Set Default RequestInterceptor");
		return new RequestInterceptor();
	}

	@Bean
	public SessionLocaleResolver sessionLocaleResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public RequestExceptionHandler customExceptionHandler(){
		
		String processor = env.getProperty(RequestExceptionHandler.KEY);
		if(StringUtils.isNoneBlank(processor)){
			try {
				Object obj = Class.forName(processor).newInstance();
				if(obj instanceof RequestExceptionHandler) {
					LogUtils.printLog("Set RequestExceptionHandler:" + obj.getClass().getName());
					return (RequestExceptionHandler)obj;
				} else {
					LogUtils.printLog(processor+":Not RequestExceptionHandler Type! ");
				}
			} catch (Exception e) {				
				LogUtils.printLog(processor, e);
			}
		}
		LogUtils.printLog("Set Default RequestExceptionHandler");
		return new RequestExceptionHandler();
	}

	

}
