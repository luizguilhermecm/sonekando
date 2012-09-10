<%-- 
    Document   : friendsManager
    Created on : 03/09/2012, 13:28:39
    Author     : snk
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="friends.FriendDao"%>
<%@page import="users.UserDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString()); %>
<% UserDao _user = new UserDao(); %>
<% FriendDao _friendDao = new FriendDao(); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Friendship Manager</title>
    </head>
    <body>
        <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>
        <h1>Gerenciamento de Amigos</h1>
        <h2> Meus Grupos </h2>
        <%
            ResultSet _groups = _friendDao.getGroups(user_id);
            while (_groups.next()){
                out.println("<a href=friendsGroup.jsp?" + _groups.getInt("gid") 
                        + ">" +  _groups.getString("gname") + "</a> <br>" );
                 out.println("<form name=input method=post action=doDeleteGroup>"
                   + "<input type=hidden name=deletouGrupo value=" + _groups.getInt("gid") + ">"
                   + "<input type=submit value=Excluir-Grupo></form>" + "<br>");
            }
        %>
        <h2> Criar Grupo </h2>
        <form name="input" method="post" action="doNewGroup">
            <input type="text" name="newgroup" rows="1" cols="30" /> 
            <input type="submit" value="Adicionar-Grupo" />
        </form>
        

        
        <h2> Meus Amigos </h2>
        <%
            ResultSet _fresult = _friendDao.getAmigosDao(user_id);
            int _ffriend_id;
            int _fid;
            
            while (_fresult.next()) {
                _fid = _fresult.getInt("fid");
                _ffriend_id = _fresult.getInt("ffriend_id");
                _groups = _friendDao.getGroupsForUser(user_id, _fid);
                
                out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                     + _user.getNomeCompletoDao(_ffriend_id) + "</a>" );
         %>       
        <form name="input" method="post" action="doToGroup">
            <select name="groups">
        <%    
             while (_groups.next())
                out.println( "<option value=" + _groups.getInt("gid") +  ">" + _groups.getString("gname") + "</option>");

            _groups.beforeFirst();
        %>          
            </select>
            <% out.println( "<input type=hidden name=friendship value=" + _fid + " />"); %>
            <input type="submit" value="Adicionar" />
        </form>  
                
        <%  
                  out.println("<form name=input method=post action=doDeleteFriend>"
                   + "<input type=hidden name=deletou value=" + _ffriend_id + ">"
                   + "<input type=submit value=Excluir-Amigo></form>" + "<br>");
            }
        %>
    </body>
</html>
