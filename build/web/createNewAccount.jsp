<%-- 
    Document   : createNewAccount
    Created on : Feb 7, 2020, 7:30:35 PM
    Author     : nguye
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Sign up</h1>
        <form action="createNewAccount" method="POST">
            <div class="wapper">
                <c:set var="errors" value="${requestScope.CREATEERRORS}"/>
                User ID* <input type="text" name="txtUserID" value="${param.txtUserID}" /> (6 - 20 chars)<br/> 
                <c:if test="${not empty errors.noSpecialCharacter}">
                    <font color="red" >
                    ${errors.noSpecialCharacter}
                    </font><br/>
                </c:if>

                Password* <input type="password" name="txtPassword" value="${param.txtPassword}" /> (6 - 30 chars)<br/>
                <c:if test="${not empty errors.passwordLengthErr}">
                    <font color="red" >
                    ${errors.passwordLengthErr}
                    </font><br/>
                </c:if>
                Confirm* <input type="password" name="txtComfirm" value="${param.txtComfirm}" /><br/>
                <c:if test="${not empty errors.confirmNotMatchPassword}">
                    <font color="red" >
                    ${errors.confirmNotMatchPassword}
                    </font><br/>
                </c:if>

                Full name* <input type="text" name="txtFullname" value="${param.txtFullname}" /> (2 - 50 chars)<br/>
                <c:if test="${not empty errors.fullNameLengthErr}">
                    <font color="red" >
                    ${errors.fullNameLengthErr}
                    </font><br/>
                </c:if>
                <input type="submit" value="Create New Account" name="btAction" />
                <input type="reset" value="Reset" />
            </div>
        </form><br/>
        <c:if test="${not empty errors.userIDIsExisted}">
            <font color="red" >
            ${errors.userIDIsExisted}
            </font><br/>
        </c:if>
        <a href="login.jsp">Back</a>
        
    </body>
</html>
