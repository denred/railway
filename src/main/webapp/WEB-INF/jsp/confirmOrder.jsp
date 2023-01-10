<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="confirmation.of.an.order"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
          rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet"/>
    <!-- Libraries Stylesheet -->
    <link href="../../lib/animate/animate.min.css" rel="stylesheet"/>
    <link href="../../lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet"/>
    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Template Stylesheet -->
    <link href="../../css/style.css" rel="stylesheet"/>
</head>
<body>
<mrt:navigation/>
<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>
<h3 style="text-align: center;">
    <fmt:message key="user.order.information"/>
</h3>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 1400px;">
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
                <th></th>
                <th></th>
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
                        <input type="hidden" name="seat_id" value="${seat_id}">
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
    </div>
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
</div>
</body>
</html>