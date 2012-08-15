<%-- 
    Document   : erroLogin
    Created on : 13/08/2012, 23:45:26
    Author     : snk

Pagina de erro ao logar, ou seja se o infeliz errar a senha ou o usuário.
O submit do form chama a Servlet "Login" assim como a index.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page language="java" %>
<%@page import="users.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sonekando</title>
        <link href="login-box.css" rel="stylesheet" type="text/css" />

    </head>
    <body>
        <%
        String nome = (String) (session.getAttribute("nome"));
        String logado = "soneka.jsp";
        if (nome != null){
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", logado);
        }
        %>
        <h1>Sonekando</h1>
        <h2>Acorda e Tente outra vez.</h2>

        <form name="input" method="post" action="Login">
            Email: <input type="text" name="emaillogin" size="100"/> <br />
            Senha: <input type="text" name="passlogin" size="50"/> <br />
            <input type="submit" value="Login" />
            <input type="reset" value="Cancelar" /> 
        </form>
    </body>
</html>
