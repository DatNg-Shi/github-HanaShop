<%-- 
    Document   : createFood
    Created on : Feb 9, 2020, 9:30:49 PM
    Author     : nguye
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Food</title>
        <style>
            .wapper {
                line-height: 50px;
                font-size: 20px;
                border: solid black 1px;
            }
            .chill {
                width: 25%;
                background-color: none;
                height: auto;
            }
            .input {
                float: right;
                width: 50%;
                margin-top: 4%;
            }
        </style>
    </head>
    <body>
        <div style="border: solid black 1px">
            <font color="black">
            <c:if test="${not empty sessionScope.USERID}">
                <h3>Welcome, ${sessionScope.NAME} </h3>
                <a href="logout">Logout</a><br/>
            </c:if>
            </font>
        </div>
        <div class="wapper">
            <c:set var="viewResult" value="${requestScope.VIEWRESULT}"/>
            <c:set var="errors" value="${requestScope.CREATEERRORS}"/>
            <c:if test="${not empty viewResult}">
                <c:set var="food" value="${viewResult}"/>    
                <c:if test="${not empty errors.messCreateSucc}">
                    <font color="green" style="font-size: 20px;">
                    ${errors.messCreateSucc}
                    </font><br/>
                </c:if>
                    <b>Name: ${food.name} </b><br/>
                    <b>Image: </b> 
                    <div style="height: 130px; width: 130px;">
                        <img alt="${food.name}" src="${pageContext.request.contextPath}/images/${food.image}" width="100%" height="100%"/>
                    </div>
                    <b>Description: ${food.description}</b><br/>
                    <b>Price: ${food.price}</b><br/>
                    <b>Create Date: ${food.createDate}</b><br/>                
                    <b>Quantity: ${food.quantity}</b><br/>
                    <b>Source: ${food.source}</b><br/>
                    <b>Category: ${food.category}</b><br/>
                    <a href="welcomeAdmin">Back to Page</a>
            </c:if>
            <c:if test="${empty viewResult}">
                <form action="createFood" enctype="multipart/form-data" method="POST">
                    <h1>Create a new Food</h1>
                    <div class="chill">
                        Name <input class="input" type="text" name="txtName" value="${param.txtName}" /><br/>
                        <c:if test="${not empty errors.nameEmpty}">
                            <font color="red">
                            ${errors.nameEmpty}
                            </font><br/>
                        </c:if>
                        Image <input class="input" type="file" name="txtImage" value="${param.txtImage}" /><br/>
                        <c:if test="${not empty errors.imageEmpty}">
                            <font color="red">
                            ${errors.imageEmpty}
                            </font><br/>
                        </c:if>
                        Description: <input class="input" type="text" name="txtDescription" value="${param.txtDescription}" /><br/>
                        <c:if test="${not empty errors.descriptionEmpty}">
                            <font color="red">
                            ${errors.descriptionEmpty}
                            </font><br/>
                        </c:if>
                        Price <input class="input" type="text" name="txtPrice" value="${param.txtPrice}" /><br/>
                        <c:if test="${not empty errors.priceEmpty}">
                            <font color="red">
                            ${errors.priceEmpty}
                            </font><br/>
                        </c:if>
                        Category <input class="input" type="text" name="txtCategory" value="${param.txtCategory}" /><br/>
                        <c:if test="${not empty errors.categoryEmpty}">
                            <font color="red">
                            ${errors.categoryEmpty}
                            </font><br/>
                        </c:if>
                        Quantity <input class="input" type="number" step="1" min="1" max="50" name="txtQuantity" value="1" size="10" /><br/>
                        <c:if test="${not empty errors.quantityEmpty}">
                            <font color="red">
                            ${errors.quantityEmpty}
                            </font><br/>
                        </c:if>
                        Source <input class="input" type="text" name="txtSource" value="${param.txtSource}" /><br/>
                        <c:if test="${not empty errors.sourceEmpty}">
                            <font color="red">
                            ${errors.sourceEmpty}
                            </font><br/>
                        </c:if>
                        <input type="hidden" name="txtStatus" value="Active" />
                    </div>
                    <input style="margin-left: 20px; width: 5%;" type="submit" value="Create" name="btAction"/>
                    <input style="margin-left: 20px; width: 5%;" type="reset" value="Reset" />
                    <input style="margin-left: 20px; width: 5%;" type="submit" value="Cancel" name="btAction" />
                </form><br/>
                <c:if test="${not empty errors.nameIsExisted}">
                    <font color="red">
                    ${errors.nameIsExisted}
                    </font><br/>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
