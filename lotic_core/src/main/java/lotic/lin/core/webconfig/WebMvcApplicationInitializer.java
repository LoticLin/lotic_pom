package lotic.lin.core.webconfig;
import lotic.lin.core.webglobal.SystemConstants;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebMvcApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class, ShiroCasConfig.class,
				RepositoriesConfig.class };
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { DispatcherConfig.class };
	}

	protected String[] getServletMappings() {
		return new String[] { "*" + SystemConstants.REQUEST_SUFFIX };
	}

	@Override
	protected Filter[] getServletFilters() {

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter,
				new OpenEntityManagerInViewFilter() 
		};
	}

}
