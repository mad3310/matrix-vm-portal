package com.letv.portal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(SessionFilter.class);


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if(session.getAttribute("loginName") == null) {
        	
        	if(req.getServletPath().contains("/account/login")) {
        		chain.doFilter(req, res);
        	}
        	logger.debug("please login==》");
        	res.sendRedirect("/account/login");
        }
        chain.doFilter(req, res);
    } 
    public void destroy() { 
    }
} 
