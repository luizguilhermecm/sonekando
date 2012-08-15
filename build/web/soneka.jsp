<%-- 
    Document   : soneka
    Created on : 13/08/2012, 21:13:04
    Author     : snk

Pagina que mostra o nome do usuario atribuido na sessão dele quando realizou
login, apenas para testar a funcionalidade de sessão.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            int id = Integer.parseInt(session.getAttribute("user_id").toString());
            response.getWriter().println(id);
        %>
        <h1>Nos vemos depois!</h1>
        <br>
        
        <a href="doLogout">Logout</a>
    </body>
</html>
