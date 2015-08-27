package lotic.lin.core.webconfig;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
@PropertySource({"classpath:shirocas.properties"})
public class ShiroCasConfig implements EnvironmentAware {
    private Environment env;

    public void setEnvironment(Environment arg0) {
        // TODO Auto-generated method stub
        this.env = arg0;

    }

    @Bean(name = {"shiroFilter"})
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean
                .setLoginUrl(env.getProperty("shiro.login.url"));
        shiroFilterFactoryBean.setUnauthorizedUrl(env.getProperty("unauthorized.url"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("/shiro-cas", "casFilter");
        map.put("/*/**", "authc");
        map.put("/", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("casFilter", casFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // defaultWebSecurityManager.setRealm(myRealm());
        defaultWebSecurityManager.setCacheManager(shiroCacheManager());
//		defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setRealm(casRealm());
        defaultWebSecurityManager.setSubjectFactory(casSubjectFactory());
        return defaultWebSecurityManager;
    }


//	@Bean
//	public SessionManager sessionManager(){
//		DefaultSessionManager dsm = new DefaultSessionManager();
//		return dsm;
//	}

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(
                "ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheCacheManager efb = new EhCacheCacheManager();
        efb.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return efb;
    }


    @Bean(name = "shiroCacheManager")
    public EhCacheManager shiroCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return cacheManager;
    }

//	@Bean(name="shiroEhCacheManagerFactoryBean")  
//    public EhCacheManagerFactoryBean shiroEhCacheManagerFactoryBean() {  
//        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();  
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(  
//                "ehcache.xml"));  
//        return ehCacheManagerFactoryBean;  
//    }  


    @Bean
    public CasSubjectFactory casSubjectFactory() {
        CasSubjectFactory csf = new CasSubjectFactory();
        return csf;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean mif = new MethodInvokingFactoryBean();
        mif.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        mif.setArguments(new Object[]{securityManager()});
        return mif;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public CasFilter casFilter() {
        CasFilter cf = new CasFilter();
        cf.setFailureUrl(env.getProperty("cas.filter.failure.url"));
        return cf;
    }

    @Bean
    public CasRealm casRealm() {
        CustomCasRealm cr = new CustomCasRealm();
        cr.setDefaultRoles("ROLE_USER");
        cr.setCasServerUrlPrefix(env.getProperty("cas.server.url.prifix"));
        cr.setCasService(env.getProperty("cas.server.url.callback"));
        return cr;

    }

    class CustomCasRealm extends CasRealm {
        /**
         * shiro 授权数据
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            String username = (String) principals.getPrimaryPrincipal();
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//        authorizationInfo.setRoles(userService.findRoles(username));
//        authorizationInfo.setStringPermissions(userService.findPermissions(username));
            return authorizationInfo;
        }

        /**
         * 认证
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(
                AuthenticationToken token) throws AuthenticationException {
            // TODO Auto-generated method stub

            return super.doGetAuthenticationInfo(token);
        }
    }


}
