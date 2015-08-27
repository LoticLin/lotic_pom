package lotic.lin.core.webglobal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lotic.lin.utils.LogUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 所有请求发生异常的处理器。在此记录操作出错日志。
 * 
 * @author Lotic.lin
 *
 */
public class RequestExceptionHandler implements HandlerExceptionResolver {
	public static final String KEY = "request.exception.handler.class";
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {

		RequestHandler handle = new RequestHandler();
		handle.setException(exception);
		handle.setHandler(handler);
		handle.setRequest(request);
		handle.setResponse(response);
		return processException(handle);
	}

	public ModelAndView processException(RequestHandler handle) {
		LogUtils.printLog(handle.getRequest().getRequestURL().toString(),
				handle.getException());
		return new ModelAndView("error").addObject("exception", handle.getException());
	}

}
