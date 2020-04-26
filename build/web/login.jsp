<%-- 
    Document   : login
    Created on : Feb 6, 2020, 11:05:51 PM
    Author     : nguye
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="login" method="POST">
            <c:set var="error" value="${requestScope.LOGINERROR}"/>
            User ID <input type="text" name="txtUserID" value="${param.txtUserID}" /><br/>
            <c:if test="${not empty error.noSpecialChar}">
                <font color="red">
                ${error.noSpecialChar}
                </font><br/>
            </c:if>
            
            Password <input type="password" name="txtPassword" value="" /><br/>
            <input type="submit" value="Login" name="btAction" />
            <input type="reset" value="Reset" />
        </form><br/>
        <c:if test="${not empty error.accountNotFound}">
            <font color="red">
            ${error.accountNotFound}
            </font><br/>
        </c:if>
        
        Don't have an account? 
        <a href="createNewAccount.jsp"> Click here</a><br/>
        <a href="welcomeUser">View some Food</a><br/>
        
    </body>
</html>
