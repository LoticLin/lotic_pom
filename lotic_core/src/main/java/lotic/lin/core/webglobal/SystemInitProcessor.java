package lotic.lin.core.webglobal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 容器启动后 执行
 *
 * @author Lotic.lin
 */
public class SystemInitProcessor implements
        ApplicationListener<ContextRefreshedEvent> {
    public static final String KEY = "system.init.processor.class";
    private static Logger LOG = LoggerFactory.getLogger(SystemInitProcessor.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {

            AfterMvcContainer(event.getApplicationContext());

        } else {

            AfterContainer(event.getApplicationContext());
        }

    }

    /**
     * spring mvc 容器启动后初始化
     */
    public void AfterMvcContainer(ApplicationContext context) {
        LOG.debug("spring mvc 容器初始化完毕");

    }

    /**
     * spring root 容器启动后初始化
     */
    public void AfterContainer(ApplicationContext context) {
        LOG.debug("spring 容器初始化完毕");
    }
}
