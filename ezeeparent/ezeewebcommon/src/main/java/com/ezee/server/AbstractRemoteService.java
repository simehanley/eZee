package com.ezee.server;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author siborg
 *
 */
public class AbstractRemoteService extends RemoteServiceServlet {

	private static final long serialVersionUID = 7695819899353607958L;

	protected <T> T getSpringBean(final Class<T> clazz) {
		return getWebApplicationContext(getServletContext()).getBean(clazz);
	}
}