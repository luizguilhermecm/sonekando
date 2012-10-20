<%-- 
    Document   : estatisticas
    Created on : 12/10/2012, 16:59:26
    Author     : snk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page language="java" %>
<%@page import="users.*" %>
<%@page import="posts.PostDao"%>
<%@page import="friends.FriendDao"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.sql.*" %>

<%-- FIX: Se usuário não estiver logado redirecionar para index. Ainda não sei como fazer --%>
<%-- TODO: ver como alguem fez para verificar se sessão é invalida e adaptar aqui --%>
<% int user_id = Integer.parseInt(session.getAttribute("user_id").toString());%>
<% UserDao _user = new UserDao();%>
<% FriendDao _friendDao = new FriendDao();%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estatisticas</title>
    </head>
    <body>
        <h1>Estatísticas</h1>
        <h2>Por seu sono em risco</h2>
        <form name=input method=post action=doEstatistica>
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
                <option value="2012">2012</option>
                <option value="2013"selected>2013</option>
            </select>
            <br>
            <select name="amigo">
                <option value="0" selected>nenhum</option>
        <%
            ResultSet _fresult = _friendDao.getAmigosDao(user_id);
            int _ffriend_id;
            while (_fresult.next()) {
                _ffriend_id = _fresult.getInt("ffriend_id");
                
                out.println("<option value=" + _ffriend_id + "> "
                     + _user.getNomeCompletoDao(_ffriend_id) + "</option>" + "<br>");
            }
        %>           </select>
            <br>
            <input type=submit value=Gerar>
        </form>
 



    </body>
</html>
