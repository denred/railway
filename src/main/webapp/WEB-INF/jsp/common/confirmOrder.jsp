<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
        <table class="table table-bordered table-hover caption-top">
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
                <td>${user.firstName} ${user.lastName}</td>
                <td>${rout_name}</td>
                <td>${train_number}</td>
                <td><fmt:message key="${car_type}"/></td>
                <td>${car_number}</td>
                <td>${count_of_seats}</td>
                <td><c:forEach items="${seat_list}" var="seat">${seat.seatNumber} </c:forEach></td>
                <td>${price}</td>
                <c:forEach items="${sessionScope.rout_order_dto_list}" var="rout">
                    <c:forEach items="${rout.stations}" var="station">
                        <c:if test="${station.station eq departure_station}">
                            <c:set var="dispatchDateTime"
                                   value="${station.stationDispatchDateTime}"/>
                        </c:if>
                        <c:if test="${station.station eq arrival_station}">
                            <c:set var="arrivalDateTime" value="${station.stationArrivalDateTime}"/>
                        </c:if>
                    </c:forEach>
                </c:forEach>
                <td>
                    ${departure_station}
                    <br> ${dispatchDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                </td>
                <td>
                    <period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}"
                                   locale="${sessionScope.locale}"/>
                </td>
                <td>
                    ${arrival_station} <br> ${arrivalDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                </td>

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
    <a href="controller?action=select_seats" class="btn btn-primary"><fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>