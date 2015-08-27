package lotic.lin.core.webglobal;

public class SystemConstants {
	/**
	 * mvc 请求拦截后缀
	 */
	public static final String REQUEST_SUFFIX = ".do";
	public static final String USER_ADMIN = "admin";
	public static final String USER_ADMIN_PASSWORD = "password";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_COMMON = "ROLE_COMMON";
	public static final String[] PERMISSION_COMMON = new String[] {
			"/common/changeLocal.do", "/common/error.do", "/common/login.do",
			"/user/home.do" };
	
	public static final String CONFIG_FILE_DS = "ds.properties";
	public static final String CONFIG_FILE_APPLICATION = "application.properties";
	public static final String CONFIG_FILE_SHIRO_CAS = "shirocas.properties";

	/**
	 * 项目根路径 在ServletContext中的KEY
	 */
	public static final String SERVLET_BASE_PATH_KEY = "basePath";
	/**
	 * Spring data repositories scan basepath
	 */
	public static final String SCAN_COMPONENT_REPOSITORIES_BASEPATH = "scan.component.repositories";

	/**
	 * Spring data repositories entity scan basepath
	 */
	public static final String SCAN_REPOSITORIES_ENTITY_BASEPATH = "scan.repositories.entity";
	
	/**
	 * Spring services component scan basepath
	 */
	public static final String SCAN_COMPONENT_SERVICE_BASEPATH = "scan.component.services";	
	
	/**
	 * Spring mvc Controller scan basepath
	 */
	public static final String SCAN_CONTROLLER_BASEPATH = "scan.controller";	
	
	/**
	 * ehcache 分布式集群共享缓存名称
	 */
	public static final String GLOBAL_GROUP_CACHE_NAME = "global-group-cache";
	
	
}
