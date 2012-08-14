<%-- 
    Document   : soneka
    Created on : 13/08/2012, 21:13:04
    Author     : snk
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
            String nome = (String) (session.getAttribute("nome"));
            response.getWriter().println(nome);
            out.println(session.getId());
        %>
        <h1>Nos vemos depois!</h1>
    </body>
</html>
