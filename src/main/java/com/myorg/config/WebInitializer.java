package com.myorg.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		/*return new Class<?>[] { SpringSecurity.class };*/
		return new Class<?>[] {  };
		/*return null;*/
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfiguration.class, WebSecurityConfig.class, HibernateConfig.class };
		/*return null;*/
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
