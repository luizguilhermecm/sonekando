<%-- 
    Document   : publicProfile
    Created on : 19/08/2012, 17:22:56
    Author     : snk
--%>

<%@page import="estatistica.estatisticaDao"%>
<%@page import="posts.PostDao"%>
<%@page import="friends.FriendDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="users.Users"%>
<%@page import="users.UserDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% int user_id = 0;
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("index.jsp");
    } else {
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
    }%>
<% PostDao _postDao = new PostDao();%>
<% estatisticaDao _estatisticaDao = new estatisticaDao();%>
<% UserDao _userDao = new UserDao();%>
<% FriendDao _friendDao = new FriendDao();%>
<%  String queryString = request.getQueryString();
    int _profile_id = Integer.parseInt(queryString);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><% String nome = _userDao.getNomeDao(_profile_id); out.print(nome);%></title>
        <style type="text/css">
            #profile
            {
                max-width:50%;
                float:left;
            }
            #content
            {
                width:50%;
                float:right;
            }

            #posts
            {
                margin-bottom: 10px;
                margin-top: 10px;
                border-style: solid;
                border-width: thin;
                max-width: 90%;
            }
            #inside
            {
                margin-bottom: 10px;
                margin-top: 10px;
                border-style:  groove;
                border-width: thin;
                max-width: 90%;
            }
        </style>

    </head>
    <body>
        <div id="profile">



            <%
                out.println("<h1>" + _userDao.getNomeCompletoDao(_profile_id) + "</h1>");
                //TODO: criar o uma pagina que coloque todos os dados do usuario
            %>
            <br>
            <img src="doDownloadProfileImage?<%out.print(_profile_id);%>" style="width: 200px">
            <%
                if (!(_friendDao.VerificarAmizade(user_id, _profile_id))) {
                    out.print("<form name=input method=post action=doRequestFriend>"
                            + "<input type=hidden name=requisitar value=" + _profile_id + ">"
                            + "<input type=submit value=Adicionar></form>");
                }
            %>



            <h2>Amigos desse Sonolento:</h2>
            <%
                ResultSet _fresult = _friendDao.getAmigosDao(_profile_id);
                int _ffriend_id;

                while (_fresult.next()) {
                    _ffriend_id = _fresult.getInt("ffriend_id");
                    out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                            + _userDao.getNomeCompletoDao(_ffriend_id) + "</a><br>");
                }
            %>

        </div>

        <div id="content">
            <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>
            <h2>Posts</h2>


            <%
                ResultSet _rsPost = _postDao.SelectPostsPublicProfile(_profile_id);
                ResultSet _rsComment = null;
                ResultSet _images = null;

                while (_rsPost.next()) {
                    out.println("<div id=posts>");
                    out.println(_userDao.getNomeCompletoDao(_rsPost.getInt("post_user_id")) + ":<br>");
                    out.println("<div id=inside>");
                    out.println(_rsPost.getString("post_content") + "<br><br>");
                    _images = _postDao.listImages(_rsPost.getInt("post_id"));
                    while (_images.next()) {
            %>
            <img src="doDownloadImagePost?<%out.print(_images.getInt("image_id"));%>" style="width: 400px">
            <%
                }

            %>
            <% if (_friendDao.VerificarAmizade(_profile_id, user_id)) {%>
            <form name="input" method="post" action="doLikePublicProfile">
                <input type=hidden name=post_id_liked value=<%out.println(_rsPost.getInt("post_id"));%> />
                <input type=hidden name=profile_id value=<%out.println(_profile_id);%> />
                <input type="submit" value="Zzz" />
            </form>
            <% } //if de amizade %>

            <%
                _rsComment = _postDao.SelectComments(_rsPost.getInt("post_id"));

                while (_rsComment.next()) {
                    out.println("<div id=posts>");
                    out.println(_userDao.getNomeCompletoDao(_rsComment.getInt("comment_user_id")) + ":<br>");
                    out.println(_rsComment.getString("comment_content"));
                    out.println("</div>");

                }
                out.println("</div>");

            %>            
            <% if (_friendDao.VerificarAmizade(_profile_id, user_id)) {%>
            <form name="input" method="post" action="doNewCommentPublicProfile">
                <textarea name="comment" cols="70%" rows=2 /></textarea><br>
                <input type=hidden name=post_id value=<%out.println(_rsPost.getInt("post_id"));%> />
                <input type=hidden name=profile_id value=<%out.println(_profile_id);%> />
                <input type="submit" value="comentar" />
            </form>
            <% } //if de amizade %>
            <%
                    out.println("</div>");

                }

            %>
        </div>


    </body>
</html>
