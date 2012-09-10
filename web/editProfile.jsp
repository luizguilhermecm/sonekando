<%-- 
    Document   : editProfile
    Created on : 10/09/2012, 09:13:39
    Author     : snk
--%>

<%@page import="users.Users"%>
<%@page import="users.UserDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString()); %>
<% UserDao _user = new UserDao(); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Informacoes</title>
    </head>
    <body>
         <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>
    <h1>Sonekando</h1>
            <h2>Lembre-se que é pago :)</h2>
            <form name="input" method="post" action="doEditProfile">
                Nome: <input type="text" name="first_name" size="60" tabindex="4" value="<%out.print(_user.getNomeDao(user_id));%>" /> <br />
                Sobrenome: <input type="text" name="last_name" size="60" tabindex="5" value="<%out.print(_user.getSobrenomeDao(user_id));%>" /> <br />
                Sexo: <input type="radio" name="sex" value="m" checked /> Masculino
                      <input type="radio" name="sex" value="f" /> Feminino <br />
                      Cidade: <input type="text" name="city" size="30" tabindex="7" value="<%out.print(_user.getCityDao(user_id));%>" /> <br />
                Email: <input type="text" name="email" size="50" tabindex="8" value="<%out.print(_user.getEmailDao(user_id));%>" /> <br />
                Senha: <input type="password" name="pass" size="20"tabinde="9" value="<%out.print(_user.getPassDao(user_id));%>" /> <br />
                <input type="submit" value="Alterar" />
                <input type="reset" value="Cancelar" /> 
            </form>    </body>
</html>
