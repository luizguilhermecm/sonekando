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
        <!-- TODO: redirecionar para profile se já estiver logado -->
        <div id="cadastro">
            <h1>Sonekando</h1>
            <h2>Cadastre-se já! É pago :)</h2>
            <form name="input" method="post" action="doCadastro">
                Nome: <input type="text" name="first_name" size="60" tabindex="4" /> <br />
                Sobrenome: <input type="text" name="last_name" size="60" tabindex="5" /> <br />
                Sexo: <input type="radio" name="sex" value="m" /> Masculino
                      <input type="radio" name="sex" value="f" /> Feminino <br />
                Cidade: <input type="text" name="city" size="30" tabindex="7" /> <br />
                Email: <input type="text" name="email" size="50" tabindex="8" /> <br />
                Senha: <input type="text" name="pass" size="20"tabinde="9" /> <br />
                <input type="submit" value="Cadastrar" />
                <input type="reset" value="Cancelar" /> 
            </form>
        </div>
        
        <div id="login">
            <form name="input" method="post" action="doLogin">
                <div id="login">
                    <br>
                    <input type="submit" value="Logar" tabinde="3" />
                </div>
                <div id="login">
                    Senha <br>
                    <input type="text" name="pass_login" size="20" tabindex="2" />
                </div>
                <div id="login">
                    Email  <br>
                    <input type="text" name="email_login" size="40" tabindex="1" />
                </div>

            </form>
        </div>
    </body>
</html>