<%-- 
    Document   : resultadoEstatistica
    Created on : 14/10/2012, 17:39:52
    Author     : snk
--%>

<%@page import="friends.FriendDao"%>
<%@page import="users.UserDao"%>
<%@page import="estatistica.estatisticaDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString());%>
<% UserDao _user = new UserDao();%>
<% FriendDao _friendDao = new FriendDao();%>
<% estatisticaDao _estatisticaDao = new estatisticaDao();%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <% java.sql.Date startDate = (java.sql.Date) request.getAttribute("startDate");
        java.sql.Date endDate = (java.sql.Date) request.getAttribute("endDate");
    %>
    <body>
        <h1>Estatísticas</h1>
        <h2><% out.println("De: " + startDate + " Até: " + endDate);%></h2>

        <h3>+ Posts por grupo:</h3>
        <%
            ResultSet grupos = _friendDao.getGroups(user_id);
            ResultSet morePerGroup = null;
            while (grupos.next()) {
                try {
                    out.println("<h2>" + grupos.getString("gname") + "</h2>");
                    int _gid = grupos.getInt("gid");
                    morePerGroup = _estatisticaDao.morePerGroup(startDate, endDate, _gid);
                    while (morePerGroup.next()) {
                        int _uid = morePerGroup.getInt("uid");
                        out.println(morePerGroup.getInt("total") + " posts - ");
                        out.println("<a href=publicProfile.jsp?" + _uid + "> "
                                + _user.getNomeCompletoDao(_uid) + "</a>" + "<br>");

                    }
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
            }

        %>

        <h3>Amigos mais curtidos/comentados (em ordem de pontuação <a href="asdf.jsp">saiba mais</a>)</h3>
        <%
            ResultSet moreInfluent = null;
            try {
                moreInfluent = _estatisticaDao.moreInfluent(startDate, endDate, user_id);

                while (moreInfluent.next()) {
                    if (moreInfluent.getInt("total") > 0) {
                        int _uid = moreInfluent.getInt("post_user_id");
                        out.println(moreInfluent.getInt("total") + " pontos - ");
                        out.println("<a href=publicProfile.jsp?" + _uid + "> "
                                + _user.getNomeCompletoDao(_uid) + "</a>" + "<br>");
                    }
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        %>

        <h3>Usuários mais curtidos/comentados (em ordem de pontuação <a href="asdf.jsp">saiba mais</a>)</h3>
        <%
            ResultSet moreInfluentExcept = null;
            try {
                moreInfluentExcept = _estatisticaDao.moreInfluentExcept(startDate, endDate, user_id);

                while (moreInfluentExcept.next()) {
                    if (moreInfluentExcept.getInt("total") > 0) {
                        int _uid = moreInfluentExcept.getInt("post_user_id");
                        out.println(moreInfluentExcept.getInt("total") + " pontos - ");
                        out.println("<a href=publicProfile.jsp?" + _uid + "> "
                                + _user.getNomeCompletoDao(_uid) + "</a>" + "<br>");
                    }
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        %>        
    </body>
</html>
