package com.ezee.server.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "EzeeGwtCacheControlFilterNew", urlPatterns = "/*", asyncSupported = true)
public class EzeeGwtCacheControlFilterNew implements Filter {

	public static final int YEAR_IN_MINUTES = 365 * 24 * 60 * 60;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final String requestURI = httpRequest.getRequestURI();
		if (requestURI.contains(".nocache.")) {
			final Date now = new Date();
			httpResponse.setDateHeader("Date", now.getTime());
			httpResponse.setDateHeader("Last-Modified", now.getTime());
			httpResponse.setDateHeader("Expires", 0);
			httpResponse.setHeader("Pragma", "no-cache");
			httpResponse.setHeader("Cache-control", "no-cache, must-revalidate, pre-check=0, post-check=0");
		} else if (requestURI.contains(".cache.")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, 1);
			httpResponse.setDateHeader("Expires", calendar.getTime().getTime());
			httpResponse.setHeader("Cache-control", "max-age=" + YEAR_IN_MINUTES + ", public");
			httpResponse.setHeader("Pragma", "");
		}

		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		/* do nothing */

	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		/* do nothing */
	}
}