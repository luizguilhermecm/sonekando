<%-- 
    Document   : offensiveContent
    Created on : 20/10/2012, 20:05:43
    Author     : snk
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% int user_id = 0;
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("index.jsp");
    } else {
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
    }%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Conteúdo Ofensivo</title>
    </head>
    <body>
        <p style="text-align: right"><a href="profile.jsp"> Minha Cama </a></p>

        <h1>Conteúdo Ofensivo você só encontra aqui e no Facebook :)</h1>

        <form name=input method=post action=doOffensive>
            De:

            <select name="deDia">
                <option value="1" selected>01</option>
                <option value="2">02</option>
                <option value="3">03</option>
                <option value="4">04</option>
                <option value="5">05</option>
                <option value="6">06</option>
                <option value="7">07</option>
                <option value="8">08</option>
                <option value="9">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
                <option value="17">17</option>
                <option value="18">18</option>
                <option value="19">19</option>
                <option value="20">20</option>
                <option value="21">21</option>
                <option value="22">22</option>
                <option value="23">23</option>
                <option value="24">24</option>
                <option value="25">25</option>
                <option value="26">26</option>
                <option value="27">27</option>
                <option value="28">28</option>
                <option value="29">29</option>
                <option value="30">30</option>
                <option value="31">31</option>
            </select>

            <select name="deMes">
                <option value="1" selected>Janeiro</option>
                <option value="2">Fevereiro</option>
                <option value="3">Março</option>
                <option value="4">Abril</option>
                <option value="5">Maio</option>
                <option value="6">Junho</option>
                <option value="7">Julho</option>
                <option value="8">Agosto</option>
                <option value="9">Setembro</option>
                <option value="10">Outubro</option>
                <option value="11">Novembro</option>
                <option value="12">Dezembro</option>
            </select>


            <select name="deAno">
                <option value="2011">2011</option>
                <option value="2012"selected>2012</option>
                <option value="2013">2013</option>
            </select>

            Até:

            <select name="ateDia">
                <option value="1" selected>01</option>
                <option value="2">02</option>
                <option value="3">03</option>
                <option value="4">04</option>
                <option value="5">05</option>
                <option value="6">06</option>
                <option value="7">07</option>
                <option value="8">08</option>
                <option value="9">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
                <option value="17">17</option>
                <option value="18">18</option>
                <option value="19">19</option>
                <option value="20">20</option>
                <option value="21">21</option>
                <option value="22">22</option>
                <option value="23">23</option>
                <option value="24">24</option>
                <option value="25">25</option>
                <option value="26">26</option>
                <option value="27">27</option>
                <option value="28">28</option>
                <option value="29">29</option>
                <option value="30">30</option>
                <option value="31">31</option>
            </select>

            <select name="ateMes">
                <option value="1" selected>Janeiro</option>
                <option value="2">Fevereiro</option>
                <option value="3">Março</option>
                <option value="4">Abril</option>
                <option value="5">Maio</option>
                <option value="6">Junho</option>
                <option value="7">Julho</option>
                <option value="8">Agosto</option>
                <option value="9">Setembro</option>
                <option value="10">Outubro</option>
                <option value="11">Novembro</option>
                <option value="12">Dezembro</option>
            </select>

            <select name="ateAno">
                <option value="2011">2011</option>
                <option value="2012"selected>2012</option>
                <option value="2013">2013</option>
            </select>
            <br>
            <input type="text" name="offensiveWord" size="30" />

            <input type=submit value=Gerar>
        </form>
        
        <form name=input method=post action=doGetAllOffensive>
            <input type="submit" value="Todos os conteúdos ofensivos"></input>
        </form>
        
        <%-- FIX: resultadoEstatistica.jsp quando nao seleciona nenhuma amigo --%>
        
        <table border="1">
        <%
            ResultSet _offensive = (ResultSet) request.getAttribute("ResultSet");

            if (_offensive == null) {
                out.println("Seu Lindo :)");
            } else {
                 out.println("<tr>");
                 out.println("<th>" + "Nome do Usuário" + "</th>");
                 out.println("<th>" + "Sobrenome do Usuário" + "</th>");
                 out.println("<th>" + "Conteudo do Post" + "</th>");
                 out.println("<th>" + "Data do Post" + "</th>");
                 out.println("<th>" + "Reincidente" + "</th>");
                 out.println("</tr>");
                 while (_offensive.next()){
                     out.println("<tr>");
                     out.println("<td>" + _offensive.getString("log_user_fname") + "</td>");
                     out.println("<td>" + _offensive.getString("log_user_lname") + "</td>");
                     out.println("<td>" + _offensive.getString("log_post_content") + "</td>");
                     out.println("<td>" + _offensive.getDate("log_post_data") + "</td>");
                     out.println("<td>" + _offensive.getBoolean("log_denovo") + "</td>");
                     out.println("</tr>");
                 }
                
                
            }
        %>
        </table>
    </body>
</html>
