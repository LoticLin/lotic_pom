package lotic.lin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lotic.lin on 8/27/2015.
 */
public class LogUtils {
    private static Logger LOG = LoggerFactory.getLogger(LogUtils.class);

    /**
     * 获得日志对象
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz) {
        return  LoggerFactory.getLogger(clazz);
    }

    /**
     * 打印日志
     * @param log
     */
    public static void printLog(String log) {
        printLog(log,null);
    }

    /**
     * 打印日志,和堆栈
     * @param message
     * @param t
     */
    public static void printLog(String message, Throwable t) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(message, t);
            return;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info(message, t);
            return;
        }
        if (LOG.isWarnEnabled()) {
            LOG.warn(message, t);
            return;
        }

        if (LOG.isErrorEnabled()) {
            LOG.error(message, t);
            return;
        }
        System.out.println(message);
        if (t != null) {
            t.printStackTrace();
        }
    }
}
