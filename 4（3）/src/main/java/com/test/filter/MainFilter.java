package com.test.filter;


import com.test.pojo.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class MainFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String url = req.getRequestURI();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null && !url.endsWith("login") && !url.endsWith("index.html")) {
            res.sendRedirect("index.html");
            return;
        }

        chain.doFilter(req, res);
    }
}
