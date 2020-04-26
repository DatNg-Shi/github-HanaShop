<%-- 
    Document   : adminPage
    Created on : Feb 7, 2020, 2:08:34 AM
    Author     : nguye
--%>
<%@page import="DatNT.food.FoodDTO" %>
<%@page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
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

                </font> 
            </div><br/>
            <div class="container">
                <c:set var="listCategory" value="${requestScope.LISTCATEGORY}"/>       
                <c:set var="messChangeStatus" value="${requestScope.MESSCHANGESTATUS}"/>       

                <div style="border: solid black 1px;">
                    <a href="createFood.jsp">Create New Article</a><br/>
                    <form action="searchFood">
                        <h1>Admin Page</h1>
                        &ensp;<input style="width: 20%" type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Input the name of food*"/>
                        <select name="txtCategory">
                            <option value="All">All</option>
                            <c:if test="${not empty listCategory}">
                                <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                    <option value="${dto.category}">${dto.category}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select name="txtStatusFood">
                            <option value="Active">Active</option>
                            <option value="Inactive">Inactive</option>
                        </select>
                        <input id="searchButton" style="width: 6%" type="submit" value="Search" name="btAction" required="type something here!!!"/>
                        <input type="hidden" name="txtStatusFood" value="Active" /><br/><br/>

                        <d>Range of Price (.000đ)</d>
                        <input class="txtMoney" id="minMoney" type="number" name="txtMin" value="${param.txtMin}" 
                               step="0.01" min="0" placeholder="0(VND)" 
                               oninput="checkValidMoney('minMoney', 'maxMoney', 'errorMoneyRange', 'searchButton')"/>
                        <d>---</d>
                        <input class="txtMoney" id="maxMoney" type="number" name="txtMax" value="${param.txtMax}" 
                               step="0.01" min="0" placeholder="Max(VND)" 
                               oninput="checkValidMoney('minMoney', 'maxMoney', 'errorMoneyRange', 'searchButton')"/>
                        <d style="visibility: hidden" id="errorMoneyRange">Invalid money range!</d>
                        <!--                            <select name="txtPrice" style="width: 200px">
                                                        <option value="none" selected="selected" >none</option>
                                                        <option value="cheapest">Cheapest (10.000 - 30.000 VND)</option>
                                                        <option value="cheap">Cheap (30.000 - 50.000 VND)</option>
                                                        <option value="expensive">Expensive (50.000 - 100.000 VND)</option>
                                                        <option value="mostExpensive">Most expensive (100.000 - 200.000 VND)</option>
                                                    </select>-->
                        <br/>
                        <a href="welcomeAdmin">Show All</a>
                    </form><br/>
                </div>
                <div style="border: solid black 1px; height: auto; background-color: none;">
                    <c:set var="categoryValue" value="${param.txtCategory}"/>
                    <c:set var="statusValue" value="${param.txtStatusFood}"/>
                    <c:set var="cateAll" value="${requestScope.ALL}"/>
                    <c:set var="priceNone" value="${requestScope.PRICE}"/>

                    <c:if test="${empty statusValue or not empty cateAll}">
                        <c:set var="statusValue" value="Active"/>
                    </c:if>
                    <c:if test="${empty categoryValue or not empty cateAll}">
                        <c:set var="categoryValue" value="All"/>
                    </c:if>

                    <c:set var="searchFood" value="${param.txtSearch}"/>
                    <c:set var="show" value="${requestScope.SHOWFOOD}"/>
                    <c:set var="isActive" value="${requestScope.ISACTIVE}"/>
                    <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
                    <c:if test="${not empty searchFood or not empty show}">
                        <c:if test="${not empty result}">
                            <c:set var="page" value="${requestScope.PAGE}"/>
                            <h3>Category: ${categoryValue} - Page ${page}</h3>
                            <c:if test="${not empty messChangeStatus}">
                                <font color="green" style="font-size: 20px;">
                                ${messChangeStatus}
                                </font><br/>
                            </c:if>
                            <form action="multipleChoice" name="form">
                                <table border="2">
                                    <thead>
                                        <tr>
                                            <th>No.</th>
                                            <th>Name</th>
                                            <th>Image</th>
                                            <th>Description</th>
                                            <th>Price</th>
                                            <th>Create Date</th>
                                            <th>Quantity</th>
                                            <th>Source</th>
                                            <th>Category</th>
                                            <th>Status</th>
                                                <c:if test="${not empty isActive}">
                                                <th>Delete</th>
                                                </c:if>
                                                <c:if test="${empty isActive}">
                                                <th>Re-Activate</th>
                                                </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="counts" value="${1}"/>
                                        <c:forEach var="dto" items="${result}" varStatus="counter">

                                            <tr>
                                                <td>${counter.count}</td>
                                                <td>
                                                    <c:url var="urlRewriting" value="viewFood">  
                                                        <c:param name="pk" value="${dto.name}"/>
                                                        <c:param name="txtStatusFood" value="${dto.statusFood}"/>
                                                    </c:url>
                                                    <a href="${urlRewriting}" style="text-decoration: none; color: black" >${dto.name}</a>
                                                </td>
                                                <td style="height: 120px; width: 120px;">
                                                    <img alt="${dto.name}" src="images/${dto.image}" width="100%" height="100%"/>
                                                </td>
                                                <td style="text-align: center">${dto.description}</td>
                                                <td>${dto.price}VND</td>
                                                <td style="text-align: center">${dto.createDate}
                                                    <c:if test="${dto.updateDate != null}">
                                                        Last Update: ${dto.updateDate}
                                                    </c:if>
                                                </td>
                                                <td style="text-align: center">${dto.quantity}</td>
                                                <td style="text-align: center">${dto.source}</td>
                                                <td>
                                                    <select name="txtCategory">
                                                        <option value="${dto.category}">${dto.category}</option>
                                                        <c:if test="${not empty listCategory}">
                                                            <c:forEach var="dtoo" items="${listCategory}">
                                                                <c:if test="${dtoo.category != dto.category}">
                                                                    <option value="${dtoo.category}" ${param.txtCategory == dtoo.category ? 'selected=""':''} >${dtoo.category}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>

                                                </td>
                                                <td>
                                                    <select name="txtStatusFood">
                                                        <option value="${dto.statusFood}">${dto.statusFood}</option>
                                                        <c:if test="${dto.statusFood == 'Active'}">
                                                            <option value="Inactive">Inactive</option>
                                                        </c:if>
                                                        <c:if test="${dto.statusFood == 'Inactive'}">
                                                            <option value="Active">Active</option>
                                                        </c:if> 
                                                    </select>                                     
                                                </td>
                                                <td>
                                                    <c:set var="AlterItemID" value="alterItem${counts}"/>
                                                    <c:if test="${not empty isActive}">
                                                        <input class="Alterbox" id="${AlterItemID}" type="checkbox" name="chkItem" value="${dto.name}"/>
                                                    </c:if>
                                                    <c:if test="${empty isActive}">
                                                        <input class="Alterbox" id="${AlterItemID}" type="checkbox" name="chkItem" value="${dto.name}"/>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <c:set var="counts" value="${counts+1}"/>
                                        </c:forEach> 
                                        <tr>
                                            <td colspan="10"></td>                                           
                                            <td>
                                                <c:set var="checkBox" value="${param.chkItem}"/>
                                                <input type="hidden" name="txtStatusFood" id="statusFood" value="${statusValue}" />
                                                <input type="hidden" name="chkItem" id="chkItem" value="${checkBox}" />
                                                <c:if test="${not empty isActive}">
                                                    <input type="button" value="Delete" name="btAction" style="width: 80px" onclick="myFunction(document.getElementById('statusFood').value,document.getElementById('chkItem').value)" />
                                                </c:if>
                                                <c:if test="${empty isActive}">
                                                    <input type="button" value="Reactivate" name="btAction" style="width: 80px" onclick="myFunction(document.getElementById('statusFood').value,document.getElementById('chkItem').value)" />
                                                </c:if>                                       
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                            <div style="clear: both; margin-top: 10px; height: 30px; font-size: 20px;">
                                <c:url var="previous" value="changePage?&page=${page}&option=previous&txtSearch=${searchFood}&txtCategory=${categoryValue}&txtStatusFood=${statusValue}&txtMin=${param.txtMin}&txtMax=${param.txtMax}"/>
                                <c:url var="next" value="changePage?&page=${page}&option=next&txtSearch=${searchFood}&txtCategory=${categoryValue}&txtStatusFood=${statusValue}&txtMin=${param.txtMin}&txtMax=${param.txtMax}"/>
                                <a href="${previous}">Previous page</a> <d>----------</d> <a href="${next}">Next Page</a>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>Not found!!!</h3><br/>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
