<%-- 
    Document   : StringSearch
    Created on : 20/08/2012, 18:23:02
    Author     : snk
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="users.UserDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString()); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>

        <h1>Encontramos isso:</h1>
        <%
            UserDao _userDao = new UserDao();
            int _user_id;
            ResultSet rs = _userDao.SearchUsers(request.getParameter("string_search"));
            while (rs.next()) {
                _user_id = rs.getInt("uid");
                    if ( _user_id != user_id) {
                        out.println("<a href=publicProfile.jsp?" + _user_id + "> "
                                + _userDao.getNomeCompletoDao(_user_id) + "</a><br>");
                        out.println("<br>");
                    }
            }
         %>
    </body>
</html>
