<%-- 
    Document   : publicProfile
    Created on : 19/08/2012, 17:22:56
    Author     : snk
--%>

<%@page import="friends.FriendDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="users.Users"%>
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
        
         <%
            String queryString = request.getQueryString();
            // TODO: enviar doPublicProfile?id=3 .. tentar pegar pela variavel
            int _uid = Integer.parseInt(queryString);
            UserDao _userDao = new UserDao();
            out.println("<h1>" + _userDao.getNomeCompletoDao(_uid) + "</h1>");
            //TODO: criar o uma pagina que coloque todos os dados do usuario
        %>
        
        <%
            FriendDao _friendDao = new FriendDao();
            if( !(_friendDao.VerificarAmizade(user_id, _uid)) ){
                      out.print( "<form name=input method=post action=doRequestFriend>"
                     + "<input type=hidden name=requisitar value=" + _uid + ">"
                     + "<input type=submit value=Adicionar></form>");
            }
        %>
        
        

        <h2>Amigos desse Sonolento:</h2>
        <%
            ResultSet _fresult = _friendDao.getAmigosDao(_uid);
            int _ffriend_id;
            
            while (_fresult.next()) {
                _ffriend_id = _fresult.getInt("ffriend_id");
                out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                        + _userDao.getNomeCompletoDao(_ffriend_id) + "</a><br>");
            }
        %>
        
        

    </body>
</html>
