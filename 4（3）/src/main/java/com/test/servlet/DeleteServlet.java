package com.test.servlet;

import com.test.mapper.StudentMapper;
import com.test.pojo.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Writer;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

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
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        Writer writer = resp.getWriter();

        try (SqlSession sqlSession = factory.openSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            mapper.deleteStudent(id);
        }

        writer.write("<h4>删除成功</h4><br>");
        writer.write("<a href=\"find.html\">返回</a>");
    }
}
