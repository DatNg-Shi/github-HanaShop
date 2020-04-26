<%-- 
    Document   : cart
    Created on : Feb 29, 2020, 9:36:44 PM
    Author     : nguye
--%>
<%@page import="java.util.LinkedHashMap" %>
<%@page import="DatNT.food.FoodDTO" %>
<%@page import="DatNT.cart.CartDTO" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
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
                <div style="border: solid black 1px;">
                    <c:set var="listCategory" value="${requestScope.LISTCATEGORY}"/>
                    <c:set var="messCart" value="${requestScope.MESS_ADDTOCART}"/>
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
                        <a class="history" href="History">Purchase history</a>
                    </form>
                    <div style="color: green; font-size: 20px; right: 50px;">
                        <c:if test="${not empty messCart}">
                            ${messCart}
                        </c:if>
                    </div> 
                    <br/>
                </div>
                <div style="border: solid black 1px; height: auto; background-color: none;">
                    <c:set var="removedItemList" value="${requestScope.REMOVELIST}"/>
                    <c:set var="amountexceedItemList" value="${requestScope.AMOUNTEXCEEDLIST}"/>
                    <c:set var="cart" value="${requestScope.CARTLIST}"/>

                    <c:if test="${not empty cart}">
                        <form action="processCart" name="form">
                            <c:set var="count" value="${1}"/>
                            <fmt:formatNumber var="totalAllItem" type="number" maxFractionDigits="2" value="${0}"/>
                            <div class="itemContainer">
                                <table border="1">
                                    <thead>
                                        <tr>
                                            <th>No.</th>
                                            <th>Image</th>
                                            <th>Name</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Total</th>
                                            <th>Detail</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="dto" items="${cart}" varStatus="counter">
                                            <tr>
                                                <td>
                                                    ${counter.count}
                                                </td>
                                                <td style="height: 120px; width: 120px;">
                                                    <img alt="${dto.value.image}}" src="images/${dto.value.image}" width="100%" height="100%"/>
                                                </td>
                                                <td>
                                        <d>${dto.value.name}</d>
                                        </td>
                                        <!--amount====================================================-->
                                        <td>
                                            <c:set var="amount" value="${'amount'}${count}"/>
                                            <c:set var="defaultamount" value="${'defaultamount'}${count}"/>
                                            <c:set var="prevamount" value="${'prevamount'}${count}"/>
                                            <c:set var="total" value="${'total'}${count}"/>
                                            <c:set var="updateAmount" value="${'upamount'}${count}"/>
                                            <c:set var="change" value="${'change'}${count}"/>

                                            <input id="${change}" name="" value="${0}" type="hidden">
                                            <input id="${prevamount}" name="" value="${dto.key.quantity}" type="hidden">
                                            <input id="${defaultamount}" name="" value="${dto.key.quantity}" type="hidden">
                                            <!--change amount,(amount, previous amount: check change or not) change total money, change value forward to servlet-->
                                            <input class="sub" type="button" name="sub" onclick="decrementValueCS('${amount}', '${prevamount}',
                                                            '${total}', '${updateAmount}', ${dto.value.price}, '${dto.key.foodID}',
                                                            '${dto.value.name}')" value="-">

                                            <input class="amount" id="${amount}" type="number" oninput="changeValueOnInputCS('${amount}', '${prevamount}',
                                                   '${total}', '${updateAmount}', ${dto.value.price}, '${dto.key.foodID}',
                                                   '${dto.value.name}')" value="${dto.key.quantity}" step="1" min="1" required="">

                                            <input class="sum" type="button" name="sum" onclick="incrementValueCS('${amount}', '${prevamount}',
                                                            '${total}', '${updateAmount}', ${dto.value.price}, '${dto.key.foodID}',
                                                            '${dto.value.name}')" value="+">

                                        </td>
                                        <td>
                                        <d>${dto.value.price}</d><d>(VND)</d>
                                        </td>
                                        <td>
                                        <d id="${total}">
                                            <fmt:formatNumber var="totalprice" type="number" groupingUsed = "false" maxFractionDigits="2" value="${dto.value.price*dto.key.quantity}"/>
                                            ${totalprice}
                                        </d><d>(VND)</d>
                                        </td>
                                        <td>
                                            <c:url var="ViewProduct" value="viewFood?pk=${dto.key.foodID}"/>
                                            <a class="viewLink" href="${ViewProduct}">View food.</a>
                                        </td>
                                        <td>
                                            <c:set var="deleteItem" value="${'deleteItem'}${count}"/>
                                            <input class="checkbox" id="${deleteItem}" type="checkbox" name="chkItem" value="${dto.key.foodID}"/>
                                        </td>

                                        </tr>
                                        <input id="${updateAmount}" type="text" name="listCodeAndAmount" value="${dto.key.foodID};;;;;${dto.value.name};;;;;${dto.key.quantity}">

                                        <c:set var="count" value="${count + 1}"/>

                                        <fmt:formatNumber var="totalAllItem" type="number" groupingUsed = "false" maxFractionDigits="2" value="${totalAllItem+totalprice}"/>
                                    </c:forEach>
                                    <tr>
                                        <td></td>
                                        <td colspan="6"><d>Total:</d><input id="totalAllItem" type="button" value="${totalAllItem}"></td>
                                    <td>    
                                        <c:set var="checkBox" value="${param.chkItem}"/>
                                        <input type="hidden" name="chkItem" id="chkItem" value="${checkBox}" />
                                        <input type="button" value="Delete" name="btAction" style="width: 80px" onclick="deleteItemsCart(document.getElementById('chkItem').value)" />
                                    </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td> <input type="submit" name="btAction" value="Save" /> </td>
                                        <td colspan="6"> <input type="submit" name="btAction" value="Confirm" /> </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${not empty removedItemList}">
                        <div id="removedItem">
                            <div>
                                <h2>These products have been deleted!</h2>
                                <c:forEach var="removedItem" items="${removedItemList}" varStatus="counter">
                                    <d style="color: red">${counter.count}</d><d>${removedItem}</d><br>
                                    </c:forEach>                               
                            </div>
                        </div>
                    </c:if>    
                    <c:if test="${not empty amountexceedItemList}">
                        <div id="amountExceedItem">
                            <div>
                                <c:forEach var="amountexceedItem" items="${amountexceedItemList}" varStatus="counter">
                                    <d style="color: red">${counter.count}</d><d>${amountexceedItem}</d><br>
                                    </c:forEach>                                
                            </div>
                        </div>
                    </c:if>  
                    <c:if test="${empty cart}">
                        <h1>No items in your shopping cart!</h1>
                    </c:if>
                </div>
            </div>
        </div>  
    </body>
</html>
