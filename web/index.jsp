<%-- 
    Document   : index
    Created on : 13/08/2012, 12:05:41
    Author     : snk

Index do Sonekando
Primeiro form eh para cadastro, e chama a Servlet "Cadastro"
Segundo form eh para login, e chama a Servlet "Login"
Existe algum css nessa pagina por isso alguns divs.
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

        <div id="cadastro">
            <h1>Sonekando</h1>
            <h2>Cadastre-se já! É pago :)</h2>
            <form name="input" method="post" action="Cadastro">
                Nome: <input type="text" name="name" size="60"/> <br />
                Sexo: <input type="radio" name="sex" value="m" /> Masculino
                <input type="radio" name="sex" value="f" /> Feminino <br />
                Email: <input type="text" name="email" size="40"/> <br />
                Senha: <input type="text" name="pass" size="20"/> <br />
                <input type="submit" value="Cadastrar" />
                <input type="reset" value="Cancelar" /> 
            </form>
        </div>
        
        <div id="login">
            <form name="input" method="post" action="Login">
                <div id="login">
                    <br>
                    <input type="submit" value="Logar" />
                </div>
                <div id="login">
                    Senha <br>
                    <input type="text" name="passlogin" size="20"/>
                </div>
                <div id="login">
                    Email  <br>
                    <input type="text" name="emaillogin" size="40" />
                </div>

            </form>
        </div>
    </body>
</html>