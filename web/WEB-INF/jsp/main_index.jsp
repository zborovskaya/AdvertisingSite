<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" import="java.util.List,by.zborovskaya.final_project.entity.Advertisement"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Insert title here</title>

    <style>
        header{
            height: 60px;
            background-color: lightgray;
            vertical-align: center;
        }
        .home-page{
            width: 40px;
            margin: 5px 20px;
        }
        .box{
            width: 350px;
            padding: 40px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%,-50%);
            background: #404040;
            border-radius: 20px;
            text-align: center;
        }
        .box h1{
            color: white;
            text-transform: uppercase;
            font-weight: 500;
        }
        .box input[type = "text"],.box input[type = "password"]{
            border:0;
            background: none;
            display: block;
            margin: 20px auto;
            text-align: center;
            border: 2px solid #3498db;
            padding: 14px 10px;
            width: 200px;
            outline: none;
            color: white;
            border-radius: 24px;
            transition: 0.25s;
        }
        .box input[type = "text"]:focus,.box input[type = "password"]:focus{
            width: 280px;
            border-color: #2ecc71;
        }
        .box input[type = "submit"]{
            border:0;
            background: none;
            display: block;
            margin: 20px auto;
            width: 200px ;
            text-align: center;
            border: 2px solid #2ecc71;
            padding: 14px 40px;
            outline: none;
            color: white;
            border-radius: 24px;
            transition: 0.25s;
            cursor: pointer;
        }
        .box input[type = "submit"]:hover{
            background: #2ecc71;
        }
        .box a{
            border:0;
            background: none;
            display: block;
            margin: 20px auto;
            width: 115px;
            text-decoration: none;
            text-align: center;
            border: 2px solid #2ecc71;
            padding: 14px 40px;
            outline: none;
            color: white;
            border-radius: 24px;
            transition: 0.25s;
            cursor: pointer;
        }
        .box p{
            margin: 20px auto;
            border:0;
            background: none;
            display: block;
            width: 155px;
            text-decoration: none;
            text-align: center;
            padding: 14px 40px;
            outline: none;
            color: white;
        }
        .box a:hover{
            background: #2ecc71;
        }
        .floating-button {
            text-decoration: none;
            display: inline-block;
            width: 140px;
            height: 45px;
            line-height: 45px;
            font-family: 'Montserrat', sans-serif;
            font-size: 11px;
            text-transform: uppercase;
            text-align: center;
            letter-spacing: 3px;
            font-weight: 600;
            color: #524f4e;
            background: lightgray;
            box-shadow: 0 8px 5px rgba(0, 0, 0, .1);
            transition: .3s;
        }
        .floating-button:hover {
            background: #2EE59D;
            box-shadow: 0 5px 10px rgba(46, 229, 157, .4);
            color: white;
            transform: translateY(+5px);
        }
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
        .modal-content {
            position: relative;
            background-color: #fefefe;
            margin: auto;
            padding: 0;
            border: 1px solid #888;
            width: 60%;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
        }
        .closeMessage {
            color: white;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .closeMessage:hover,
        .closeMessage:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }
        .modal-header {
            padding: 2px 16px;
            background-color: #5cb85c;
            color: white;
        }
        .modal-body {padding: 2px 16px;}
        .floating-button-change{
            border: none;
            text-decoration: none;
            display: inline-block;
            width: 140px;
            height: 45px;
            line-height: 45px;
            margin: 30px 10px;
            font-family: 'Montserrat', sans-serif;
            font-size: 11px;
            text-transform: uppercase;
            text-align: center;
            letter-spacing: 3px;
            font-weight: 600;
            color: #524f4e;
            background: white;
            box-shadow: 0 8px 15px rgba(0, 0, 0, .1);
            transition: .3s;
            outline: none;
        }
        .floating-button-change:hover {
            background: #2EE59D;
            box-shadow: 0 15px 20px rgba(46, 229, 157, .4);
            color: white;
            transform: translateY(-7px);
        }
        .change-lang{
            font-family: 'Montserrat', sans-serif;
            font-size: 17px;
            font-weight: 500;
            text-transform: uppercase;
        }
    </style>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localisation.local" var="loc" />

    <fmt:message bundle="${loc}" key="local.message" var="message" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.login" var="login" />
    <fmt:message bundle="${loc}" key="local.password" var="password" />
    <fmt:message bundle="${loc}" key="local.submit" var="submit" />
    <fmt:message bundle="${loc}" key="local.registration" var="registration" />
    <fmt:message bundle="${loc}" key="local.signin" var="signin" />
    <fmt:message bundle="${loc}" key="local.notRegistrated" var="notregistrated" />
</head>
<body>
<header>
    <table border="0" align="left">
        <tr>
            <td>
                <a href="Controller?command=gotoindexpage"><img src="${pageContext.request.contextPath}/images/home.png"
                                                                class="home-page"/></a>
            </td>
        </tr>
    </table>
    <table border="0" align="right" >
        <tr height="45">
            <td align="right" class="change-lang">
                <c:out value="${message}" />
            </td>
            <td>
                <a href="Controller?command=changelocalru">
                    <img src="${pageContext.request.contextPath}/images/ru.jpg" width="40" height="30"> </a>
            </td>
            <td>
                <a href="Controller?command=changelocalen">
                    <img src="${pageContext.request.contextPath}/images/en.jpg" width="40" height="30"></a>
            </td>
    </table>
</header>
<br />

<form action="Controller" method="post" class="box">
    <h1>${signin}</h1>
    <input type="hidden" name="command" value="logination" />
    <input type="text" required name="login" value="" placeholder="${login}"/>
    <input type="password" required name="password" value="" placeholder="${password}"/>
    <input type="submit" value="${signin}" />
    <a href="Controller?command=registration">${registration}</a>
</form>
<div id="messageModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="closeMessage">&times;</span>
            <h2>Message</h2>
        </div>
        <div class="modal-body">
            <p style="font-size: 15px; font-weight: bolder">${sessionScope.message}</p>
            <button type="button" class="floating-button-change" id="okMessage">OK</button>
        </div>
    </div>
</div>

<c:if test="${sessionScope.message != null}">
    <script>
        var messageModal = document.getElementById("messageModal");
        var closeMessage = document.getElementById("okMessage");
        var span = document.getElementsByClassName("closeMessage");
        messageModal.style.display = "block";
        closeMessage.onclick = function (){
            messageModal.style.display = "none";
        }
        span.onclick = function (){
            messageModal.style.display = "none";
        }
        window.onclick = function(event) {
            if (event.target == messageModal) {
                messageModal.style.display = "none";
            }
        }
    </script>
    ${sessionScope.remove("message")}
</c:if>
</body>
</html>
