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
<% int user_id = 0;
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("index.jsp");
    } else {
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
    }%>
<% UserDao _user = new UserDao();%>
<% FriendDao _friendDao = new FriendDao();%>
<% estatisticaDao _estatisticaDao = new estatisticaDao();%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estatísticas</title>
    </head>
    <% java.sql.Date startDate = (java.sql.Date) request.getAttribute("startDate");
        java.sql.Date endDate = (java.sql.Date) request.getAttribute("endDate");
    %>
    <body>
        <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>

        <h1>Estatísticas</h1>
        <h2><% out.println("De: " + startDate + " Até: " + endDate);%></h2>

        <h2>Top 3 postadores por grupo: </h3>
        <%
            ResultSet grupos = _friendDao.getGroups(user_id);
            ResultSet morePerGroup = null;
            while (grupos.next()) {
                try {
                    out.println("<h3>" + grupos.getString("gname") + "</h3>");
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

        <h2>Amigos mais curtidos/comentados (em ordem de pontuação <a href="saibamais.jsp">saiba mais</a>)</h2>
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

        <h2>Usuários mais curtidos/comentados (em ordem de pontuação <a href="saibamais.jsp">saiba mais</a>)</h2>
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

        <h2>Top 3 posts por dia mais curtidos/comentados de amigos</h2>
        <%
            ResultSet _datesBetween = null;
            ResultSet _threePerDay = null;
            try {
                _datesBetween = _estatisticaDao.datesBetween(startDate, endDate, user_id);

                while (_datesBetween.next()) {
                    out.println("<h4>" + _datesBetween.getDate("datas") + "</h4>");
                    _threePerDay = _estatisticaDao.threePerDay(_datesBetween.getDate("datas"), user_id);
                    while (_threePerDay.next()) {
                        if (_threePerDay.getInt("total") > 0) {
                            int _uid = _threePerDay.getInt("post_user_id");
                            out.println(_threePerDay.getInt("total") + " pontos - ");
                            out.println("<a href=publicProfile.jsp?" + _uid + "> "
                                    + _user.getNomeCompletoDao(_uid) + "</a>");
                            out.println(_threePerDay.getString("post_content") + "<br>");
                        }
                    }
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        %>

        <h2> Meus posts </h2>
        <%
            ResultSet _myPosts = null;
            try {
                _myPosts = _estatisticaDao.myPosts(startDate, endDate, user_id);
                while (_myPosts.next()) {
                    out.println(_myPosts.getInt("total") + " pontos - ");
                    out.println(_myPosts.getString("post_content") + "<br>");
                }

                _myPosts = _estatisticaDao.allTimeAvg(user_id);
                while (_myPosts.next()) {
                    out.println("<br>Média de pontos de todos meus posts: " + _myPosts.getInt("media_geral") + "<br>");
                }
                _myPosts = _estatisticaDao.periodAvg(startDate, endDate, user_id);
                while (_myPosts.next()) {
                    out.println("<br>Média de pontos dos meus posts no período: " + _myPosts.getInt("media_periodo") + "<br>");
                }

            } catch (Exception e) {
                out.println(e.getMessage());
            }
        %>

        <h2> Amigo selecionado </h2>
        Selecionado: 
        <%
            int _amigo = Integer.parseInt(request.getParameter("amigo"));
            if (_amigo == 0){
                out.println("nenhum amigo selecionado :) (provavelmente você está solteiro :P)");
            }
                       else {
            out.println("<a href=publicProfile.jsp?" + _amigo + "> "
                    + _user.getNomeCompletoDao(_amigo) + "</a>");

            try {
                ResultSet _datesBetweenForAll = null;
                _datesBetweenForAll = _estatisticaDao.datesBetweenForAll(startDate, endDate, _amigo);

                ResultSet _postsThatDay = null;
                ResultSet _likesThatDay = null;
                ResultSet _likesToMeThatDay = null;
                ResultSet _commentsThatDay = null;
                ResultSet _commentsToMeThatDay = null;
                   
                while (_datesBetweenForAll.next()) {
                    java.sql.Date dia = _datesBetweenForAll.getDate("datas");
                    out.println("<h4>" + dia + "</h4>");
                    
                    _postsThatDay = _estatisticaDao.postsThatDay(dia, _amigo  );
                    _likesThatDay = _estatisticaDao.likesThatDay(dia, _amigo  );
                    _likesToMeThatDay = _estatisticaDao.likesToMeThatDay(dia, _amigo, user_id);
                    _commentsThatDay = _estatisticaDao.commentsThatDay(dia, _amigo  );
                    _commentsToMeThatDay = _estatisticaDao.commentsToMeThatDay(dia, _amigo, user_id);

                    if(_postsThatDay.next()){
                        out.println(_postsThatDay.getInt("total_posts") + " posts" + "<br>");
                    }
                    if(_likesThatDay.next()){
                        out.println(_likesThatDay.getInt("total_likes") + " curtir" + "<br>");
                    }
                    if(_commentsThatDay.next()){
                        out.println(_commentsThatDay.getInt("total_comments") + " comentário" + "<br>");
                    }
                    if(_likesToMeThatDay.next()){
                        out.println("<br>" + _likesToMeThatDay.getInt("likes_me") + " curtir em meus posts" + "<br>");
                    }
                    if(_commentsToMeThatDay.next()){
                        out.println(_commentsToMeThatDay.getInt("comments_me") + " comentário em meus posts" + "<br>");
                    }
                  
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
                       }


        %>


    </body>
</html>
