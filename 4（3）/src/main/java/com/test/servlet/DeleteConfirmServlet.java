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

@WebServlet("/delete2")
public class DeleteConfirmServlet extends HttpServlet {

    static Long id;

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
        String confirm = null;
        if(req.getParameter("confirm") != null) {
            confirm = req.getParameter("confirm");
        }
        if(confirm != null) {
            HttpSession session = req.getSession();
            session.setAttribute("id", id);
            resp.sendRedirect("delete");
        }
        if(req.getParameter("id") != null) {
            id = Long.valueOf(req.getParameter("id"));
        }
        Writer writer = resp.getWriter();

        Student student = null;
        try (SqlSession sqlSession = factory.openSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            student = mapper.queryStudentById(id);
        }
        if(student==null){
            writer.write("没有这个学号噢");
            writer.write("<a href=\"find.html\">返回</a>");
        }
        else {
            writer.write("<h3>确定删除吗？</h3><br><h4>信息</h4>");
            writer.write("<table>");
            writer.write("<tr>\n" +
                    "<td width='50px'>学号</td>\n" +
                    "<td width='80px'>姓名</td>\n" +
                    "<td width='50px'>性别</td>\n" +
                    "<td width='150px'>班级</td>\n" +
                    "<td width='200px'>电话</td>\n" +
                    "<td width='100px'>邮箱</td>\n" +
                    "</tr>");
            writer.write(student.string());
            writer.write("</table>");
            writer.write("<br><br><form action=\"delete2\">\n" +
                    "    <label>\n" +
                    "        <input type=\"submit\" name=\"confirm\" value=\"确定\">\n" +
                    "    </label>\n" +
                    "</form><br>");
            writer.write("<a href=\"find.html\">返回</a>");
        }

    }
}
