<%-- 
    Document   : index
    Created on : 13/08/2012, 12:05:41
    Author     : snk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page language="java" %>
<%@page import="users.*" %>
<%@page import="database.*" %>
<%@page import="java.sql.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Sonekando </title>
    </head>
    <body>
        <h1> Sonekando - Soneke voce tambem </h1>
        
        <form name="input" method="post" action="Cadastro">
            Nome: <input type="text" name="name" /> <br />
            Sexo: <input type="radio" name="sex" value="m" /> Masculino
            <input type="radio" name="sex" value="f" /> Feminino <br />
            Email: <input type="text" name="email" /> <br />
            Senha: <input type="text" name="pass" /> <br />
            <input type="submit" value="Cadastrar" />
            <input type="reset" value="Cancelar" /> 
        </form>
        
        <br><br>
        <h1> Login</h1>
        <form name="input" method="post" action="Login">
            Email: <input type="text" name="emaillogin" /><br />
            Senha: <input type="text" name="passlogin"/><br />
            <input type="submit" value="Logar" />
            <input type="reset" value="Cancelar" />
        </form>

    </body>
</html>
