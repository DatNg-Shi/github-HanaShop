<%-- 
    Document   : shoppingHistory
    Created on : Mar 1, 2020, 11:38:04 AM
    Author     : nguye
--%>

<%@page import="DatNT.purchaseHistory.PurchaseHistoryDTO" %>
<%@page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/common.js" ></script>
    </head>
    <body>
        <div class="wapper">
            <div style="border: solid black 1px">
                <font color="black">
                <c:if test="${not empty sessionScope.USERID}">
                    <h3>Welcome, ${sessionScope.NAME} </h3>
                    <a href="logout">Logout</a><br/>
                </c:if>
                <c:if test="${empty sessionScope.USERID}">
                    <h3>Welcome, Quest.</h3>
                    <a href="login.jsp">Login</a><br/>
                    <a href="createNewAccount.jsp">Sign Up</a><br/>
                </c:if>
                </font> 
            </div><br/>
            <div class="container">
                <div style="border: solid black 1px; position: relative;">
                    <c:set var="listCategory" value="${requestScope.LISTCATEGORY}"/>
                    <c:set var="messCart" value="${requestScope.MESS_ADDTOCART}"/>
                    <c:set var="buySucc" value="${requestScope.BUYSUCCESS}"/>
                    <c:set var="hisList" value="${requestScope.HISTORYLIST}"/>
                    <c:set var="show" value="${requestScope.SHOW}"/>
                    <form action="searchHistory">
                        <h1>History Page</h1>
                        &ensp;<input style="width: 20%" type="text" name="txtSearchHistory" value="${param.txtSearchHistory}" placeholder="Input the name of food*"/> 
                        <input id="searchButton" style="width: 6%" type="submit" value="Search" name="btAction"/><br/>
                        <d>Range of Date: </d>
                        <input class="txtDate" id="minDate" type="date" name="txtMinDate" value="" 
                               placeholder="1753-01-01" min="1753-01-01" max=""
                               oninput="checkValidDate('minDate', 'maxDate', 'errorDateRange', 'searchButton')"/>
                        <d>---</d>
                        <input class="txtDate" id="maxDate" type="date" name="txtMaxDate" value="" 
                               placeholder="" min="1753-01-01" max=""
                               oninput="checkValidDate('minDate', 'maxDate', 'errorDateRange', 'searchButton')"/>
                        <d id="errorDateRange"></d><br>
                        <c:url var="urlRewriting" value="searchHistory?&txtSearchHistory=&txtMinDate=&txtMaxDate"/>
                        <a href="${urlRewriting}">Show All</a>&emsp;
                        
                        <a class="history" href="viewCart">View Your Cart</a>&emsp;
                        <a class="history" href="searchHistory">Purchase history</a>
                    </form>
                </div>
                <div style="border: solid black 1px; height: auto; background-color: none;">
                    <c:set var="searchHisList" value="${param.txtSearchHistory}"/>
                    <c:if test="${not empty searchHisList or not empty show}">
                        <c:if test="${not empty hisList}">
                            <c:set var="page" value="${requestScope.PAGE}"/>
                            <h3>History: </h3>
                            <div style="clear: both; margin-bottom: auto">
                                <table border="1" cellpadding="5">
                                    <thead>
                                        <tr>
                                            <th>No.</th>
                                            <th>Food Image</th>
                                            <th>Food Name</th>
                                            <th>Food Quantity</th>
                                            <th>Food Price/item</th>
                                            <th>Food Total</th>
                                            <th>Shopping Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="dto" items="${hisList}" varStatus="counter">
                                            <tr>
                                                <td>${counter.count}</td>
                                                <td style="height: 120px; width: 120px;">
                                                    <img src="images/${dto.image}" width="100%" height="100%"/>
                                                </td>
                                                <td>
                                                    ${dto.name}
                                                </td>
                                                <td>
                                                    ${dto.quantity}
                                                </td>
                                                <td>
                                                    ${dto.price}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber var="totalItems" maxFractionDigits="1" value="${dto.quantity*dto.price}"/>
                                                    ${totalItems}
                                                </td>
                                                <td>
                                                    ${dto.date}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <a href="welcomeUser">Back to Page</a>   
                        </c:if>
                    </c:if>  
                </div>        

            </div>
        </div>
    </body>
</html>
