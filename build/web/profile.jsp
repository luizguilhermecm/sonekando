<%-- 
    Document   : profile
    Created on : 15/08/2012, 15:22:44
    Author     : snk
--%>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> <% String nome = _user.getNomeDao(user_id); out.print(nome);%>   </title>
    </head>
    <body>
        <h1> Bom Dia <% out.print(nome); %> </h1>
        <form name="input" method="post" action="StringSearch.jsp">
            <input type="text" name="string_search" size="30" />
            <input type="submit" value="Buscar" />
        </form>
        <h2> Meus Amigos: </h2>
        <%
            ResultSet _fresult = _user.getAmigosDao(user_id);
            int _ffriend_id;
            int _fuser_id;
            while (_fresult.next()) {
                _ffriend_id = _fresult.getInt("ffriend_id");
                _fuser_id = _fresult.getInt("fuser_id");
                if (_ffriend_id != user_id)
                out.println("<a href=publicProfile.jsp?" + _ffriend_id + "> "
                        + _user.getNomeCompletoDao(_ffriend_id) + "</a><br>");
                else 
                    out.println("<a href=publicProfile.jsp?" + _fuser_id + "> "
                        + _user.getNomeCompletoDao(_fuser_id) + "</a><br>");
            }
        %>
        
        <hr>
        
        <h2> Aceitar Amigos: </h2>
        <%
        ResultSet _presult = _user.getPendenciasDao(user_id);
        int uid_pendente;
        while (_presult.next()){
            uid_pendente = _presult.getInt("fid");
            _fuser_id = _presult.getInt("fuser_id");
            out.print( "<a href=doPublicProfile?" + _fuser_id + "> " 
                    + _user.getNomeCompletoDao(_fuser_id) + "</a><br>");
            out.print( "<form name=input method=post action=doAcceptFriend>"
                     + "<input type=hidden name=aceitou value=" + uid_pendente + ">"
                     + "<input type=submit value=Aceitar></form>");
            out.print( "<form name=input method=post action=doRejectFriend>"
                     + "<input type=hidden name=recusou value=" + uid_pendente + ">"
                     + "<input type=submit value=Recusar></form>");
        }
        %>
        
        <hr>
        <a href="doLogout">Logout</a>
    </body>
</html>
