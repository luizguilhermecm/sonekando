<%-- 
    Document   : friedsGroup
    Created on : 04/09/2012, 12:53:49
    Author     : snk
--%>

<%@page import="users.UserDao"%>
<%@page import="friends.FriendDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int user_id = 0;
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("index.jsp");
    } else {
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
    }%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grupos</title>
    </head>
    <body>
        <p style="text-align: right"><a href="friendsManager.jsp"> Gerenciar Amigos </a></p>

        
        <%
            String queryString = request.getQueryString();
            int _gid = Integer.parseInt(queryString);
            
            FriendDao _friendDao = new FriendDao();
            UserDao _user = new UserDao();
        
            out.println("<h2>" + _friendDao.getGroupName(_gid) + "</h2>");
            
            ResultSet _fresult = _friendDao.getAllUsersInGroup(_gid);
            int _uid;
            while (_fresult.next()) {
                _uid = _fresult.getInt("uid");
                out.println("<a href=publicProfile.jsp?" + _uid + "> "
                     + _user.getNomeCompletoDao(_uid) + "</a><br />" 
                     + "<form name=input method=post action=doGoOutOfGroup>"
                     + "<input type=hidden name=fid value=" + _friendDao.getFID(user_id, _uid) + ">"
                     + "<input type=hidden name=gid value=" + _gid + ">" 
                     + "<input type=submit value=Desagrupar></form>" + "<br>");
            }
         %> 
    </body>
</html>
