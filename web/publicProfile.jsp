<%-- 
    Document   : publicProfile
    Created on : 19/08/2012, 17:22:56
    Author     : snk
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="users.Users"%>
<%@page import="users.UserDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String queryString = request.getQueryString();
            // TODO: enviar doPublicProfile?id=3 .. tentar pegar pela variavel
            int _uid = Integer.parseInt(queryString);
            UserDao _userDao = new UserDao();
            out.println("<h1>" + _userDao.getNomeCompletoDao(_uid) + "</h1>");
            //TODO: criar o uma pagina que coloque todos os dados do usuario
        %>
        <h2>Amigos desse Sonolento:</h2>
        <%
            ResultSet _fresult = _userDao.getAmigosDao(_uid);
            int _ffriend_id;
            int _fuser_id;
            while (_fresult.next()) {
                _ffriend_id = _fresult.getInt("ffriend_id");
                _fuser_id = _fresult.getInt("fuser_id");
                 if (_ffriend_id != _uid)
                out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                        + _userDao.getNomeCompletoDao(_ffriend_id) + "</a><br>");
                else 
                    out.println("<a href=publicProfile.jsp?" + _fuser_id + "> "
                        + _userDao.getNomeCompletoDao(_fuser_id) + "</a><br>");
            }
        %>

    </body>
</html>
