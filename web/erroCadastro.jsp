<%-- 
    Document   : index
    Created on : 13/08/2012, 12:05:41
    Author     : snk

Essa pagina basicamente será usada quando o infeliz não entrar com todos os campos
ou der erro de insersão no banco, será redirecionada para essa página.
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
        <div id="cadastro">
            <h1>Sonekando</h1>
            <h2>Preencha todos os campos :)</h2>
            <form name="input" method="post" action="doCadastro">
                Nome: <input type="text" name="first_name" size="60" tabindex="4"  /> <br />
                Sobrenome: <input type="text" name="last_name" size="60" tabindex="5"  /> <br />
                Sexo: <input type="radio" name="sex" value="m" checked /> Masculino
                      <input type="radio" name="sex" value="f" /> Feminino <br />
                Cidade: <input type="text" name="city" size="30" tabindex="7"  /> <br />
                Email: <input type="text" name="email" size="50" tabindex="8"  /> <br />
                Senha: <input type="text" name="pass" size="20"tabinde="9"  /> <br />
                <input type="submit" value="Cadastrar" />
                <input type="reset" value="Cancelar" /> 
            </form>
        </div>
        
        </div>
    </body>
</html>