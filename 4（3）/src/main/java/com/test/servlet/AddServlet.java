package com.test.servlet;

import com.test.mapper.StudentMapper;
import com.test.mapper.UserMapper;
import com.test.pojo.Student;
import com.test.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Writer;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {
        factory = new SqlSessionFactoryBuilder().
                build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        if(gender.equals("0"))
            gender="男";
        else
            gender="女";
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String className=req.getParameter("className");
        Writer writer = resp.getWriter();

        Student student = new Student(null, name, gender, className,phone, email);
        try (SqlSession sqlSession = factory.openSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            mapper.addStudent(student);
        }

        writer.write("<h4>添加成功</h4><br>");
        writer.write("<a href=\"find.html\">返回</a>");
    }
}
