<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="confirmation.of.an.order"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
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
                    <form action="controller?action=confirm_order" method="POST">
                        <input type="hidden" name="routs_id" value="${routs_id}">
                        <input type="hidden" name="train_id" value="${train_id}">
                        <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                        <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                        <input type="hidden" name="car_id" value="${car_id}">
                        <input type="hidden" name="car_type" value="${car_type}">
                        <input type="hidden" name="count_of_seats" value="${count_of_seats}">
                        <input type="hidden" name="seats_id" value="${seats_id}">
                        <input type="hidden" name="travel_time" value="${travel_time}">
                        <input type="submit" class="btn btn-success" name="add_order"
                               value="<fmt:message key="order.make.order"/>">
                    </form>
                </td>
                <td>
                    <form action="controller?action=home" method="POST">
                        <input type="submit" class="btn btn-danger" value="<fmt:message key="order.cancel"/>">
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="controller?action=select_seats" method="POST">
        <input type="hidden" name="departure_station" value="${departure_station}">
        <input type="hidden" name="arrival_station" value="${arrival_station}">
        <input type="hidden" name="departure_station_id" value="${departure_station_id}">
        <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
        <input type="hidden" name="car_type" value="${car_type}">
        <input type="hidden" name="train_id" value="${train_id}">
        <input type="hidden" name="car_id" value="${car_id}">
        <input type="hidden" name="station1" value="${station1}">
        <input type="hidden" name="station2" value="${station2}">
        <input type="hidden" name="travel_time" value="${travel_time}">
        <input type="hidden" name="count_of_seats" value="${count_of_seats}">
        <input type="hidden" name="departure_date" value="${departure_date}">
        <input type="hidden" name="routs_id" value="${routs_id}">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>