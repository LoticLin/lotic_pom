package lotic.lin.core.webglobal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lotic.lin.utils.LogUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	public static final String KEY = "request.interceptor.class";

	// 请求开始，会在Controller的方法执行前会被调用
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		RequestHandler handle = new RequestHandler();
		handle.setHandler(handler);
		handle.setRequest(request);
		handle.setResponse(response);
		return preRequest(handle);
	}

	public boolean preRequest(RequestHandler handler) throws Exception {
		LogUtils.printLog("Request Start:" + handler.getRequest().getRequestURL());
		return super.preHandle(handler.getRequest(), handler.getResponse(),
				handler.getHandler());
	}

}
