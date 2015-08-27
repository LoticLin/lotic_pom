package lotic.lin.core.webconfig;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import lotic.lin.core.webglobal.SystemConstants;
import lotic.lin.core.webglobal.WebContainerInitializerMarker;
import lotic.lin.utils.LogUtils;
import lotic.lin.utils.PropertiesUtils;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * web -xml 配置
 * 
 * @author Lotic.lin
 *
 */
public class WebServletContainerInitializer implements
		WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		servletContext.addListener(SingleSignOutHttpSessionListener.class);

		FilterRegistration.Dynamic filterRegistration1 = servletContext
				.addFilter("singleSignOutFilter", new SingleSignOutFilter());
		filterRegistration1.addMappingForUrlPatterns(EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE), false, "/*");
		FilterRegistration.Dynamic filterRegistration2 = servletContext
				.addFilter("shiroFilter", new DelegatingFilterProxy());
		filterRegistration2.addMappingForUrlPatterns(EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE), false, "/*");
		String clazz = PropertiesUtils.getValue(
				WebContainerInitializerMarker.KEY, SystemConstants.CONFIG_FILE_APPLICATION);

		if (clazz != null) {
			try {
				Object obj = Class.forName(clazz).newInstance();
				if (obj instanceof WebContainerInitializerMarker) {
					WebContainerInitializerMarker init = (WebContainerInitializerMarker) obj;
					init.onStartup(servletContext);
				} else {
					LogUtils.printLog(obj.getClass().getName()
							+ "Not WebContainerInitializerMarker Type");
				}
			} catch (Exception e) {
				LogUtils.printLog(e.getMessage());
			}
		}
	}

}
