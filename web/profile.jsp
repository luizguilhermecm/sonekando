<%-- 
    Document   : profile
    Created on : 15/08/2012, 15:22:44
    Author     : snk
--%>
<%@page import="posts.PostDao"%>
<%@page import="friends.FriendDao"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" %>
<%@page import="users.*" %>
<%@page import="java.sql.*" %>

<%-- FIX: Se usuário não estiver logado redirecionar para index. Ainda não sei como fazer --%>
<%-- TODO: ver como alguem fez para verificar se sessão é invalida e adaptar aqui --%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString()); %>
<% UserDao _user = new UserDao(); %>
<% FriendDao _friendDao = new FriendDao(); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> <% String nome = _user.getNomeDao(user_id); out.print(nome);%>   </title>
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
</style>
    </head>
    <body>
        <div id="profile">
            <h1> Bom Dia <% out.print(nome);%></h1>
        <a href="editProfile.jsp">Editar</a>
        <br>
        <img src="doDownloadProfileImage?<%out.print(user_id);%>" style="width: 200px">
        <form name="input" enctype="multipart/form-data" method="post" action="doUploadProfileImage">
            <input name="file" type="file" /><br />
            <input type="submit" value="Enviar Arquivo">
        </form>
        <br>
        <form name="input" method="post" action="StringSearch.jsp">
            <input type="text" name="string_search" size="30" />
            <input type="submit" value="Buscar" />
        </form>
        
        <br>
        <a href="estatisticas.jsp">Estatisticas</a>
        <br>
        <h2> Meus Amigos: </h2>
        <a href="friendsManager.jsp"> Gerenciar Amigos </a> <br><br>
        <%
            ResultSet _fresult = _friendDao.getAmigosDao(user_id);
            int _ffriend_id;
            while (_fresult.next()) {
                _ffriend_id = _fresult.getInt("ffriend_id");
                
                out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                     + _user.getNomeCompletoDao(_ffriend_id) + "</a>" + "<br>");
            }
        %>
        
        
        <h2> Aceitar Amigos: </h2>
        <%
        ResultSet _presult = _user.getPendenciasDao(user_id);
        int uid_pendente;
        int _fuser_id;

        while (_presult.next()){
            uid_pendente = _presult.getInt("fid");
            _fuser_id = _presult.getInt("fuser_id");
            out.print( "<a href=publicProfile.jsp?" + _fuser_id + "> " 
                    + _user.getNomeCompletoDao(_fuser_id) + "</a><br>");
            out.print( "<form name=input method=post action=doAcceptFriend>"
                     + "<input type=hidden name=aceitou value=" + uid_pendente + ">"
                     + "<input type=submit value=Aceitar></form>");
            out.print( "<form name=input method=post action=doRejectFriend>"
                     + "<input type=hidden name=recusou value=" + uid_pendente + ">"
                     + "<input type=submit value=Recusar></form>");
        }
        %>
        
        <h2> Amigos Recomendados: </h2>
        
        <%
        ResultSet _recomends = _friendDao.RecomendsFriends(user_id);
        int _friend_id;
        int _count;
        while(_recomends.next()){
            _friend_id = _recomends.getInt("ffriend_id");
            _count = _recomends.getByte("count");
            out.print( "<a href=publicProfile.jsp?" + _friend_id + "> " 
                    + _user.getNomeCompletoDao(_friend_id) + "</a>(" +_count + ")<br>");
         
        }
        %>
        
        <br><br>
        <a href="doLogout">Logout</a>
        </div>
        
        <div id="content">
            <% PostDao _postDao = new PostDao(); %>
            
            <h2>Posts</h2>
            <form name="input" method="post" action="doNewPost">
                <textarea name="content" cols="70%" rows=3 /></textarea><br>
                <input type="submit" value="Postar" />
            </form>
            
        <form name="input" enctype="multipart/form-data" method="post" action="doUploadProfileImage">
            <input name="file" type="file" /><br />
            <input type="submit" value="Enviar Arquivo">
        </form>

        <%
            ResultSet _rsPost = _postDao.SelectPosts(user_id);
            ResultSet _rsComment = null;
            while (_rsPost.next()) {
                out.println(_user.getNomeCompletoDao(_rsPost.getInt("post_user_id")) + ":<br>");
                out.println("<div id=posts>");
                out.println(_rsPost.getString("post_content"));
         %>
         <form name="input" method="post" action="doLikePost">
                <input type=hidden name=post_id_liked value=<%out.println(_rsPost.getInt("post_id"));%> >
                <input type="submit" value="Zzz" />
         </form>
                
         <% 
            if(_rsPost.getInt("post_user_id") == user_id){ // if de editar e excluir se for post proprio
         %>
         
         <form name="input" method="post" action="doEditPost">
                <textarea name="edited_content" cols="50%" rows=1 /></textarea>
                <input type=hidden name=post_id_edited value=<%out.println(_rsPost.getInt("post_id"));%> >
                <input type="submit" value="editar" />
         </form>
         <form name="input" method="post" action="doDeletePost">
                <input type=hidden name=post_id_deleted value=<%out.println(_rsPost.getInt("post_id"));%> >
                <input type="submit" value="deletar" />
         </form>
                
         <%
            } //fecha if de editar e excluir
         %>
         
            <%
                _rsComment = _postDao.SelectComments(_rsPost.getInt("post_id"));
                
                while (_rsComment.next()) {
                    out.println("<div id=posts>");                  
                    out.println(_user.getNomeCompletoDao(_rsComment.getInt("comment_user_id")) + ":<br>");
                    out.println(_rsComment.getString("comment_content"));
                    out.println("</div>");

                }
                out.println("</div>");
            
        %>            
            <form name="input" method="post" action="doNewComment">
                <textarea name="comment" cols="70%" rows=2 /></textarea><br>
                <input type=hidden name=post_id value=<%out.println(_rsPost.getInt("post_id"));%> />
                <input type="submit" value="comentar" />
            </form>
            
        <%
                   }
        %>
        </div>

    </body>
</html>
