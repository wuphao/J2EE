package com.test.servlet;

import com.test.mapper.StudentMapper;
import com.test.mapper.UserMapper;
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
import java.util.List;

@WebServlet("/find")
public class FindServlet extends HttpServlet {

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
        String value = req.getParameter("value");
        Writer writer = resp.getWriter();

        try (SqlSession sqlSession = factory.openSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> list = mapper.queryStudent(value);

            HttpSession session = req.getSession();
            session.setAttribute("list", list);
            session.setAttribute("page", 0);
            resp.sendRedirect("result");

/*            int size = list.size();
            for(int i = 0; i < Math.min(size, 5); i++) {
                writer.write(list.get(i).toString());
                writer.write("<br>");
            }*/
        }

    }
}
