<%-- 
    Document   : profile
    Created on : 15/08/2012, 15:22:44
    Author     : snk
--%>
<!DOCTYPE html>

<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" %>
<%@page import="users.*" %>
<%@page import="java.sql.*" %>

<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString()); %>
<% UserDao _user = new UserDao(); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> <% String nome = _user.getNomeDao(user_id); out.print(nome);%>   </title>
    </head>
    <body>
        <h1> Bom Dia <% out.print(nome); %> </h1>
        <h2> Meus Amigos: </h2>
        
        <%
        ResultSet _fresult = _user.getAmigosDao(user_id);
        while (_fresult.next()){
            out.println("<h1>" + _user.getNomeDao(_fresult.getInt("ffriend_id")) + "</h1>" );
        }
        %>


        <hr>
        <h2> Aceitar Amigos: </h2>
        
        <%
        ResultSet _presult = _user.getPendenciasDao(user_id);
        while (_presult.next()){
            out.print( _user.getNomeCompletoDao(_presult.getInt("fuser_id")) + "</b>");
            out.println( "<a href=doAcceptFriend> Aceitar </a> " + "- - -" +
                         "<a href=doRejectFriend> Rejeitar </a> ");
        }
        %>
        
        <hr>
        <a href="doLogout">Logout</a>

    </body>
</html>