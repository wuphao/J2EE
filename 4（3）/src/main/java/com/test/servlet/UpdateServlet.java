package com.test.servlet;

import com.test.mapper.StudentMapper;
import com.test.pojo.Student;
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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

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
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String className=req.getParameter("className");
        Writer writer = resp.getWriter();
        if(gender.equals("0"))
            gender="男";
        else
            gender="女";
        Student student = new Student(id, name, gender,className ,phone, email);
        try (SqlSession sqlSession = factory.openSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            mapper.modifyStudent(student);
        }

        writer.write("<h4>修改成功</h4><br>");
        writer.write("<a href=\"find.html\">返回</a>");
    }
}
