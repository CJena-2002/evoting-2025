package com.Controller.Admin.Filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class AdminAuthenticationFilter
 */
@WebFilter("/Admin")
public class AdminAuthenticationFilter  implements Filter {

    /**
     * Default constructor. 
     */
    public AdminAuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("uname") != null);

        String loginURI = httpRequest.getContextPath() + "/Admin/AdminLogin";

        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

        boolean isLoginPage = httpRequest.getRequestURI().endsWith("AdminLogin.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the admin is already logged in and he's trying to login again
            // then forwards to the admin's homepage
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/");
            dispatcher.forward(request, response);

        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);

        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminLogin.jsp");
            dispatcher.forward(request, response);

        }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
