package com.test.servlet;

import java.io.*;

import com.test.mapper.UserMapper;
import com.test.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {
        factory = new SqlSessionFactoryBuilder().
                build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=UTF-8");
        try (SqlSession sqlSession = factory.openSession(true)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.login(username, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("find.html");
            } else {
                resp.getWriter().write("<h1>登录失败，用户名或密码错误</h1>");
                resp.getWriter().write("<br><a href=\"index.html\">返回</a>");
            }
        }
    }
}