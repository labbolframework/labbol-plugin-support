/**
 * 
 */
package com.labbol.core.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.yelong.core.model.service.SqlModelService;

import com.labbol.core.LabbolPluginSupport;
import com.labbol.core.filesc.FileUploadSCIPInterceptor;
import com.labbol.core.log.LogFilter;
import com.labbol.core.log.LogInterceptor;
import com.labbol.core.rights.RightsHandlerInterceptor;
import com.labbol.core.security.SecurityFilter;
import com.labbol.core.xss.XSSFilter;

/**
 * @since 2.0
 */
public class LabbolPluginSupportConfiguration {

	// ===========================拦截器===========================

	/**
	 * @return 开发人员开发时的模式。取消登录验证等
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "rights", havingValue = "true", matchIfMissing = false)
	// 在登录拦截器之下
	@Order(10001)
	public RightsHandlerInterceptor rightsHandlerInterceptor() {
		return new RightsHandlerInterceptor();
	}

	/**
	 * @return 日志拦截器
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "logRecord", havingValue = "true", matchIfMissing = false)
	@ConditionalOnMissingBean(LogInterceptor.class)
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	/**
	 * @return 涉密网文件上传拦截器
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "fileUploadSCIP", havingValue = "true", matchIfMissing = false)
	// 在权限拦截器之后
	@Order(10003)
	public FileUploadSCIPInterceptor fileUploadSCIPInterceptor() {
		return new FileUploadSCIPInterceptor();
	}

	// =============================Filter===============================

	/**
	 * @return xxs注入过滤器
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "xss", havingValue = "true", matchIfMissing = false)
	@ConditionalOnMissingBean(XSSFilter.class)
	public FilterRegistrationBean<XSSFilter> xssFilter() {
		FilterRegistrationBean<XSSFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		XSSFilter xssFilter = new XSSFilter();
		filterRegistrationBean.setFilter(xssFilter);
		filterRegistrationBean.addUrlPatterns("*");// 配置过滤规则
//        filterRegistrationBean.addInitParameter("name","hahahhhaa");//设置init参数
		filterRegistrationBean.setName("xssFilter");// 设置过滤器名称
		filterRegistrationBean.setOrder(12000);// 执行次序
		return filterRegistrationBean;
	}

	/**
	 * @return 安全过滤器(加解密)
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "security", havingValue = "true", matchIfMissing = false)
	@ConditionalOnMissingBean(SecurityFilter.class)
	public FilterRegistrationBean<SecurityFilter> securityFilter() {
		FilterRegistrationBean<SecurityFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		SecurityFilter securityFilter = new SecurityFilter();
		filterRegistrationBean.setFilter(securityFilter);
		filterRegistrationBean.addUrlPatterns("*");// 配置过滤规则
//        filterRegistrationBean.addInitParameter("name","hahahhhaa");//设置init参数
		filterRegistrationBean.setName("securityFilter");// 设置过滤器名称
		filterRegistrationBean.setOrder(10000);// 执行次序
		return filterRegistrationBean;
	}

	/**
	 * @param modelService modelService
	 * @return 日志过滤器
	 */
	@Bean
	@ConditionalOnProperty(prefix = LabbolPluginSupport.PROPERTIES_PREFIX, name = "logRecord", havingValue = "true", matchIfMissing = false)
	@ConditionalOnMissingBean(LogFilter.class)
	public FilterRegistrationBean<LogFilter> logFilter(SqlModelService modelService) {
		FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		LogFilter xssFilter = new LogFilter(modelService);
		filterRegistrationBean.setFilter(xssFilter);
		filterRegistrationBean.addUrlPatterns("*");// 配置过滤规则
//        filterRegistrationBean.addInitParameter("name","hahahhhaa");//设置init参数
		filterRegistrationBean.setName("logFilter");// 设置过滤器名称
		filterRegistrationBean.setOrder(11000);// 执行次序
		return filterRegistrationBean;
	}

}
