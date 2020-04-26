<%-- 
    Document   : foodDetail
    Created on : Feb 11, 2020, 5:15:22 PM
    Author     : nguye
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DatNT.food.FoodDTO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
        <style>
            .wapper {
                border: solid black 1px;
                line-height: 40px;
                font-size: 20px;
            }
        </style>
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/common.js" ></script>
    </head>
    <body>
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
        
        <c:set var="isAdmin" value="${sessionScope.ISADMIN}"/>
        <c:set var="listCategory" value="${requestScope.LISTCATEGORY}"/>
        <c:set var="viewResult" value="${requestScope.VIEWRESULT}" />
        <c:set var="primarikey" value="${requestScope.PRIMARYKEY}" />
        <c:if test="${empty isAdmin}">
            <form action="addToCart">       
                <div class="wapper">
                    <c:if test="${not empty viewResult}">
                        <c:set var="food" value="${viewResult}"/>    
                        <h2>Food Information</h2>
                        <b>Name: </b> ${food.name}
                        <input type="hidden" name="pk" value="${primarikey}" /><br/>
                        <b>Image: </b> 
                        <div style="height: 150px; width: 150px;">
                            <img alt="${food.name}" src="images/${food.image}" width="100%" height="100%"/>
                        </div>
                        <br/>
                        <b>Description: </b>${food.description}<br/>
                        <b>Create Date: </b> ${food.createDate}<br/>


                        <b>Price: </b>${food.price} <d>(VND)</d><br/>
                        <b>Quantity: </b>                 
                        <div id="numberOfItems">
                            <input id="sub" type="button" name="sub" onclick="decrementValue(document.getElementById('amount').value, ${food.price})" value="-">
                            <input id="amount" type="number" name="txtAmount" oninput="changeValueOnInput(document.getElementById('amount').value, ${food.price})" value="1" step="1" min="1" required="">
                            <input id="sum" type="button" name="sum" onclick="incrementValue(document.getElementById('amount').value, ${food.price})" value="+">
                        </div>
                        <b id="tittle">Total: </b>
                        <div class="totalpricesymble">
                            <d id="total">${food.price}</d><d>(VND)</d>
                        </div><br/>

                        <b>Source: </b>${food.source}<br/>
                        <b>Category: </b> ${food.category}<br/>
                        <input style="margin-left: 50px; width: 7%;" type="submit" value="Add to cart" name="btAction" />

                        <input type="hidden" name="txtItemID" value="${food.name}" />
                    </c:if>
                    <c:if test="${empty viewResult}">
                        <h1 style="float: left; margin: 30px">Food is not available at this moment!</h1>
                    </c:if>
                </div>
            </form><br/>
            <a href="welcomeUser">Back to Page</a>    
        </c:if>
        <c:if test="${not empty isAdmin}">
            <form action="updateFood" name="form">
                <c:set var="errors" value="${requestScope.CREATEERRORS}"/>
                <div class="wapper">
                    <c:if test="${not empty viewResult}">
                        <c:set var="food" value="${viewResult}"/>    
                        <h2>Food Information</h2>
                        <b>Name </b>
                        <input type="text" name="txtName" value="${food.name}" />
                        <input type="hidden" name="pk" value="${primarikey}" />
                        <c:if test="${not empty errors.nameEmpty}">
                            <font color="red">
                            ${errors.nameEmpty}
                            </font>
                        </c:if><br/>
                        <b>Image: </b> 
                        <div style="height: 150px; width: 150px;">
                            <img alt="${food.name}" src="${pageContext.request.contextPath}/images/${food.image}" width="100%" height="100%"/>
                        </div>
                        <input type="hidden" name="txtImageOld" value="${food.image}" /><br/>
                        <b>Change Image: </b> <input class="input" type="file" name="txtImageNew" value="" /><br/>
                        <b>Description: </b> <input type="text" name="txtDescription" value="${food.description}" />
                        <c:if test="${not empty errors.descriptionEmpty}">
                            <font color="red">
                            ${errors.descriptionEmpty}
                            </font>
                        </c:if><br/>
                        <b>Price: </b> <input type="text" name="txtPrice" value="${food.price}" />
                        <c:if test="${not empty errors.descriptionEmpty}">
                            <font color="red">
                            ${errors.descriptionEmpty}
                            </font>
                        </c:if><br/>
                        <b>Create Date: </b> ${food.createDate} 
                        <input type="hidden" name="txtCreateDate" value="${food.createDate}" /> <br/>
                        <b>Quantity: </b> <input class="input" type="number" step="1" min="1" max="" name="txtQuantity" value="${food.quantity}" size="10" />
                        <c:if test="${not empty errors.descriptionEmpty}">
                            <font color="red">
                            ${errors.descriptionEmpty}
                            </font>
                        </c:if><br/>
                        <b>Source: </b> <input type="text" name="txtSource" value="${food.source}" />
                        <c:if test="${not empty errors.sourceEmpty}">
                            <font color="red">
                            ${errors.sourceEmpty}
                            </font>
                        </c:if><br/>
                        <b>Category: </b> 
                        <select name="txtCategory">
                            <option value="${food.category}">${food.category}</option>
                            <c:if test="${not empty listCategory}">
                                <c:forEach var="dtoo" items="${listCategory}">
                                    <c:if test="${dtoo.category != food.category}">
                                        <option value="${dtoo.category}">${dtoo.category}</option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </select><br/>
                        <b>Status: </b> 
                        <select name="txtStatusFood">
                            <option value="${food.statusFood}">${food.statusFood}</option>
                            <c:if test="${food.statusFood == 'Active'}">
                                <option value="Inactive">Inactive</option>
                            </c:if>
                            <c:if test="${food.statusFood == 'Inactive'}">
                                <option value="Active">Active</option>
                            </c:if>    
                        </select><br/>
                    </c:if>

                    <c:if test="${food.statusFood == 'Inactive'}">
                        <input style="margin-left: 50px; width: 7%;" type="submit" value="Restore" name="btAction" />
                    </c:if>
                    <c:if test="${food.statusFood == 'Active'}">
                        <input style="margin-left: 50px; width: 7%;" type="submit" value="Update" name="btAction" />
                        &ensp;
                        <input type="hidden" name="pk" id="pk" value="${primarikey}" />            
                        <input type="button" name="btAction" style="margin-left: 50px; width: 7%;" onclick="deleteRecord(document.getElementById('pk').value)" value="Delete"/>
                    </c:if>

                </div>
            </form>
            <c:if test="${not empty errors.nameIsExisted}">
                <font color="red">
                ${errors.nameIsExisted}
                </font>
            </c:if><br/>
            <a href="welcomeAdmin">Back to Page</a>
        </c:if>
    </body>
</html>
