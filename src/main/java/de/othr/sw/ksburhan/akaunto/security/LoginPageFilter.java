package de.othr.sw.ksburhan.akaunto.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class LoginPageFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((((HttpServletRequest)request).getRequestURI().equals("/login"))
                || (((HttpServletRequest)request).getRequestURI().equals("/register"))
                || (((HttpServletRequest)request).getRequestURI().equals("/")))) {
            ((HttpServletResponse)response).sendRedirect("/home");
        }
        chain.doFilter(request, response);
    }

}