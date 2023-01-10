<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
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
    <div class="h5 mr-auto p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>

<form action="confirm_order" method="GET">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 600px;">
            <thead class="thead-light text-center">
        <tr>
            <c:forEach begin="1" end="${count_of_seats}">
                <th><fmt:message key="order.seats.number"/></th>
            </c:forEach>
            <th><fmt:message key="order.make.order"/></th>
        </tr>
        </thead>
        <tbody class="text-center">
        <tr>
            <c:forEach begin="1" end="${count_of_seats}">
                <td><select class="btn btn-info dropdown-toggle" name="seats_number">
                    <c:forEach var="seat" items="${seat_list}">
                        <option value="${seat.id}"><c:out value="${seat.seatNumber}"/></option>
                    </c:forEach>
                </select></td>
            </c:forEach>
            <td>
                <input type="hidden" name="routs_id" value="${routs_id}">
                <input type="hidden" name="train_id" value="${train_id}">
                <input type="hidden" name="user_id" value="${user_id}">
                <input type="hidden" name="departure_station" value="${departure_station}">
                <input type="hidden" name="arrival_station" value="${arrival_station}">
                <input type="hidden" name="departure_date" value="${departure_date}">
                <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                <input type="hidden" name="station1" value="${station1}">
                <input type="hidden" name="travel_time" value="${travel_time}">
                <input type="hidden" name="station2" value="${station2}">
                <input type="hidden" name="car_type" value="${car_type}">
                <input type="hidden" name="car_id" value="${car_id}">
                <input type="hidden" name="count_of_seats" value="${count_of_seats}">
                <input type="submit" class="btn btn-success" name="add_order"
                       value="<fmt:message key="next"/>">
            </td>
        </tr>
    </table>
    </div>
</form>

<form action="select_cars_and_seats_for_order" method="GET">
    <input type="hidden" name="station1" value="${station1}">
    <input type="hidden" name="travel_time" value="${travel_time}">
    <input type="hidden" name="station2" value="${station2}">
    <input type="hidden" name="routs_id" value="${routs_id}">
    <input type="hidden" name="train_id" value="${train_id}">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="hidden" name="departure_station_id" value="${departure_station_id}">
    <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
    <input type="hidden" name="car_type" value="${car_type}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>
