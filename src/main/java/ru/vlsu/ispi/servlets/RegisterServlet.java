package ru.vlsu.ispi.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("pass");
        String loginDetails = username + ":" + password;

        Cookie cookie = new Cookie("loginDetails", loginDetails);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

        response.sendRedirect("cookies/index_old.jsp");
    }
}
