package com.Controller.Admin.Filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class FrontEndAuthenticationFilter
 */
@WebFilter("/*")
public class FrontEndAuthenticationFilter  implements Filter {
	private HttpServletRequest httpRequest;



    private static final String[] loginRequiredURLs = {
            "/view_profile", "/edit_profile", "/update_profile"
    };
    
    public FrontEndAuthenticationFilter() {
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
		 httpRequest = (HttpServletRequest) request;

	        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

	        if (path.startsWith("/admin/")) {
	            chain.doFilter(request, response);
	            return;
	        }

	        HttpSession session = httpRequest.getSession(false);

	        boolean isLoggedIn = (session != null && session.getAttribute("customerUser") != null);

	        String loginURI = httpRequest.getContextPath() + "/login";
	        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
	        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

	        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
	            // the user is already logged in and he's trying to login again
	            // then forward to the homepage
	            httpRequest.getRequestDispatcher("/").forward(request, response);

	        } else if (!isLoggedIn && isLoginRequired()) {
	            // the user is not logged in, and the requested page requires
	            // authentication, then forward to the login page
	            String loginPage = "/login.jsp";
	            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
	            dispatcher.forward(request, response);
	        } else {
	            // for other requested pages that do not require authentication
	            // or the user is already logged in, continue to the destination
	            chain.doFilter(request, response);
	        }
	}
	private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();

        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
