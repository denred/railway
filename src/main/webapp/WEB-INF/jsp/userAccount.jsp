<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.account"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
        }

        table {
            table-layout: fixed;
            width: auto;
            height: auto;
            text-align: center;
        }

        tr {
            width: auto;
            height: auto;
            text-align: center;

        }

        td {
            width: auto;
            text-align: center;

        }
    </style>
</head>
<body>
<mrt:navigation/>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<div class="h5" align="right">
    <fmt:message key="enterRole"></fmt:message>
    <mrt:role role="${user.role}"></mrt:role>
</div>
<div class="h2" style="text-align: center;">
    <fmt:message key="account"></fmt:message>
</div>
<p class="h4">
    <h12><fmt:message key="user.order.information"/></h12>
</p>
<table class="table table-bordered table-hover text-center" border="1" style="width: auto">
    <thead class="thead-light text-center">
    <tr>
        <th><fmt:message key="order.train.number"/></th>
        <th><fmt:message key="rout.name"/></th>
        <th><fmt:message key="order.car.type"/></th>
        <th><fmt:message key="order.car.number"/></th>
        <th><fmt:message key="order.count.of.seats"/></th>
        <th><fmt:message key="order.seats.number"/></th>
        <th><fmt:message key="order.price"/></th>
        <th><fmt:message key="order.dispatch.station"/></th>
        <th><fmt:message key="order.dispatch.date"/></th>
        <th><fmt:message key="order.travel.time"/></th>
        <th><fmt:message key="order.arrival.station"/></th>
        <th><fmt:message key="order.arrival.date"/></th>
        <th><fmt:message key="order.date"/></th>
        <th><fmt:message key="order.status"/></th>
        <th><fmt:message key="order.cancel"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${order_list}" var="order">
        <tr>
            <td>${order.trainNumber}</td>
            <td>${order.routsId}</td>
            <td><fmt:message key="${order.carType}"/></td>
            <td>${order.carNumber}</td>
            <td>${order.countOfSeats}</td>
            <td>${order.seatNumber}</td>
            <td>${order.price}</td>
            <td>${order.dispatchStation}</td>
            <td>${order.arrivalDate}</td>
            <td>${order.travelTime}</td>
            <td>${order.arrivalStation}</td>
            <td>${order.dispatchDate}</td>
            <td>${order.orderDate}</td>
            <td><fmt:message key="${order.orderStatus}"/></td>
            <td>
                <form action="cancel_order" method="POST">
                    <input type="hidden" name="order_id" value="${order.orderId}">
                    <input type="hidden" name="user_id" value="${user_id}">
                    <input type="submit" class="btn btn-info" name="edit_info_order"
                           value="<fmt:message key="decline"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="h4"><fmt:message key="total"/> ${sum}</div>
<form action="home" method="GET">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>