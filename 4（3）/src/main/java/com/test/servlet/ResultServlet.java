package com.test.servlet;

import com.test.pojo.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    static List<Student> list = null;
    static Integer page;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String next = req.getParameter("next");
        HttpSession session = req.getSession();
        list = (List<Student>) session.getAttribute("list");
        if(req.getParameter("page") != null) {
            page = Integer.valueOf(req.getParameter("page"));
            page--;
        } else if(next != null) {
            if(next.equals("上一页")) {
                page--;
            } else {
                page++;
            }
        } else {
            page = (Integer) session.getAttribute("page");
        }
        System.out.println(page);
        Writer writer = resp.getWriter();
        writer.write("<h3>操作</h3>");
        writer.write("<a href=\"add.html\">新建</a>\n" +
                "&nbsp&nbsp<a href=\"update.html\">编辑</a>\n" +
                "&nbsp&nbsp<a href=\"delete.html\">删除</a>");

        writer.write("<form action=\"result\">\n" +
                "        <label>\n" +
                "            <input type=\"text\" name=\"page\">\n" +
                "        </label>\n" +
                "        <input type=\"submit\" value=\"跳转\">\n" +
                "    </form>");
        writer.write("<form action=\"result\">\n" +
                "        <label>\n" +
                "            <input type=\"submit\" name='next' value=\"上一页\">\n" +
                "            <input type=\"submit\" name='next' value=\"下一页\">\n" +
                "        </label>\n" +
                "    </form>");
        writer.write("<h3>搜索结果</h3>");

        int total = list.size() / 5;
        if(list.size() % 5 != 0) {
            total++;
        }

        if (page >= total) {
            page = total - 1;
        } else if (page < 0) {
            page = 0;
        }

        writer.write("<table>");
        writer.write("<tr>\n" +
                "<td width='50px'>学号</td>\n" +
                "<td width='80px'>姓名</td>\n" +
                "<td width='50px'>性别</td>\n" +
                "<td width='150px'>班级</td>\n" +
                "<td width='200px'>电话</td>\n" +
                "<td width='100px'>邮箱</td>\n" +
                "</tr>");
        int maxIndex = Math.min(page * 5 + 5, list.size());
        for(int i = page * 5; i < maxIndex; i++) {
            writer.write(list.get(i).string());
            //writer.write(list.get(i).toString());
            //writer.write("<br>");
        }
        writer.write("</table>");

        writer.write("<br>");
        writer.write("第" + (page + 1) + "页， 共" + total + "页");
        writer.write("<br>共" + list.size() + "条记录");

        writer.write("<br><br><a href=\"find.html\">返回</a>");

    }
}
