<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="confirmation.of.an.order"/></title>
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
    <h12><fmt:message key="user.order.information"/></h12>
</div>
<table class="table table-bordered table-hover text-center" border="1" style="width: auto">
    <thead class="thead-light text-center">
    <tr>
        <th><fmt:message key="order.user.information"/></th>
        <th><fmt:message key="rout.name"/></th>
        <th><fmt:message key="order.train.number"/></th>
        <th><fmt:message key="order.car.type"/></th>
        <th><fmt:message key="order.car.number"/></th>
        <th><fmt:message key="order.count.of.seats"/></th>
        <th><fmt:message key="order.seats.number"/></th>
        <th><fmt:message key="order.price"/></th>
        <th><fmt:message key="order.dispatch.station.and.dispatch.time"/></th>
        <th><fmt:message key="order.travel.time"/></th>
        <th><fmt:message key="order.arrival.station.and.dispatch.time"/></th>
        <th><fmt:message key="order.make.order"/></th>
        <th><fmt:message key="order.cancel"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${first_name} ${last_name}</td>
        <td>${rout_name}</td>
        <td>${train_number}</td>
        <td><fmt:message key="${car_type}"/></td>
        <td>${car_number}</td>
        <td>${count_of_seats}</td>
        <td><c:forEach items="${seats}" var="seat">${seat.seatNumber} </c:forEach></td>
        <td>${price}</td>
        <td>${station1}</td>
        <td>${travel_time}</td>
        <td>${station2}</td>
        <td>
            <form action="confirm_order" method="POST">
                <input type="hidden" name="routs_id" value="${routs_id}">
                <input type="hidden" name="train_id" value="${train_id}">
                <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                <input type="hidden" name="car_id" value="${car_id}">
                <input type="hidden" name="car_type" value="${car_type}">
                <input type="hidden" name="count_of_seats" value="${count_of_seats}">
                <input type="hidden" name="seat_id"  value="${seat_id}">
                <input type="submit" class="btn btn-success" name="add_order"
                       value="<fmt:message key="order.make.order"/>">
            </form>
        </td>
        <td>
            <form action="home" method="GET">
                <input type="submit" class="btn btn-danger" value="<fmt:message key="order.cancel"/>">
            </form>
        </td>
    </tr>
    </tbody>
</table>
<form action="select_seats_for_order" method="GET">
    <input type="hidden" name="station1" value="${station1}">
    <input type="hidden" name="travel_time" value="${travel_time}">
    <input type="hidden" name="station2" value="${station2}">
    <input type="hidden" name="routs_id" value="${routs_id}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="hidden" name="count_of_seats" value="${count_of_seats}">
    <input type="hidden" name="car_id" value="${car_id}">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="train_id" value="${train_id}">
    <input type="hidden" name="car_type" value="${car_type}">
    <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
    <input type="hidden" name="departure_station_id" value="${departure_station_id}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="routs_id" value="${routs_id}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>