package lotic.lin.core.webglobal;

import javax.servlet.ServletContext;

public interface WebContainerInitializerMarker {
	public static final String KEY = "web.container.initializer.class";
	public void onStartup(ServletContext servletContext);
}
