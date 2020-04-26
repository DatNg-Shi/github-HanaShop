<%-- 
    Document   : searching
    Created on : Feb 7, 2020, 1:05:56 AM
    Author     : nguye
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DatNT.food.FoodDTO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>

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
                    <form action="searchFood">
                        <h1>Search Page</h1>
                        &ensp;<input style="width: 20%" type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Input the name of food*"/>
                        <select name="txtCategory" >
                            <option value="All">All</option>
                            <c:if test="${not empty listCategory}">
                                <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                    <option value="${dto.category}">${dto.category}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <input id="searchButton" style="width: 6%" type="submit" value="Search" name="btAction"/>
                        <input type="hidden" name="txtStatusFood" value="Active" /><br/><br/>
                        <d>Range of Price (.000đ)</d>
                        <input class="txtMoney" id="minMoney" type="number" name="txtMin" value="${param.txtMin}" 
                               step="0.01" min="0" placeholder="0(VND)" 
                               oninput="checkValidMoney('minMoney', 'maxMoney', 'errorMoneyRange', 'searchButton')"/>
                        <d>---</d>
                        <input class="txtMoney" id="maxMoney" type="number" name="txtMax" value="${param.txtMax}" 
                               step="0.01" min="0" placeholder="Max(VND)" 
                               oninput="checkValidMoney('minMoney', 'maxMoney', 'errorMoneyRange', 'searchButton')"/>
                        <d style="visibility: hidden" id="errorMoneyRange">Invalid money range!</d><br/>
                        <a href="welcomeUser">Show All</a>&emsp;
                        <a class="history" href="viewCart">View Your Cart</a>&emsp;
                        <a class="history" href="searchHistory">Purchase history</a>
                    </form>
                    <div class="messCart">
                        <c:if test="${not empty messCart}">
                            ${messCart}
                        </c:if>
                        <c:if test="${not empty buySucc}">
                            ${buySucc}
                        </c:if>
                    </div> 
                    <br/>
                </div>
                <div style="border: solid black 1px; height: auto; background-color: none;">
                    <c:set var="categoryValue" value="${param.txtCategory}"/>
                    <c:set var="statusValue" value="${param.txtStatusFood}"/>
                    <c:set var="cateAll" value="${requestScope.ALL}"/>
                    <c:if test="${empty statusValue or not empty cateAll}">
                        <c:set var="statusValue" value="Active"/>
                    </c:if>

                    <c:if test="${empty categoryValue}">
                        <c:set var="categoryValue" value="All"/>
                    </c:if>
                    <c:set var="searchFood" value="${param.txtSearch}"/>
                    <c:set var="show" value="${requestScope.SHOWFOOD}"/>
                    <c:set var="isActive" value="${requestScope.ISACTIVE}"/>
                    <c:if test="${not empty searchFood or not empty show}">
                        <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
                        <c:if test="${not empty result}">
                            <c:set var="page" value="${requestScope.PAGE}"/>
                            <h3>Category: ${categoryValue} - Page ${page}</h3>

                            <div style="clear: both; background-color: bisque; margin-bottom: auto">
                                <c:forEach var="dto" items="${result}" varStatus="counter">
                                    <div class="food">
                                        <a style="text-decoration: none; color: black" href="viewFood?&pk=${dto.name}">
                                            <span class="image">
                                                <img alt="${dto.name}" src="images/${dto.image}" width="100%" height="100%"/>                                          
                                            </span>
                                            <div class="test">
                                                <p class="foodName">${dto.name}</p>
                                                <p class="foodPrice" style="font-weight: bold">${dto.price}VND</p>
                                                <p class="fooddate">${dto.createDate}</p>
                                            </div>
                                        </a>  
                                    </div>
                                </c:forEach>
                            </div>
                            <div style="clear: both; padding: 20px 0 0 42px; height: 30px; font-size: 20px; background-color: none;">
                                <c:url var="previous" value="changePage?&page=${page}&option=previous&txtSearch=${searchFood}&txtCategory=${categoryValue}&txtStatusFood=${statusValue}&txtMin=${param.txtMin}&txtMax=${param.txtMax}"/>
                                <c:url var="next" value="changePage?&page=${page}&option=next&txtSearch=${searchFood}&txtCategory=${categoryValue}&txtStatusFood=${statusValue}&txtMin=${param.txtMin}&txtMax=${param.txtMax}"/>
                                <a href="${previous}">Previous page</a> <d>----------</d> <a href="${next}">Next Page</a>
                            </div>
                        </c:if>
                    </c:if>             
                </div>
            </div>
        </div>
    </body>
</html>
