<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<form action="controller?action=create_order" method="POST">
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
                <td><select class="btn btn-info dropdown-toggle" name="seats_id">
                    <c:forEach var="seat" items="${seat_list}">
                        <option value="${seat.id}"><c:out value="${seat.seatNumber}"/></option>
                    </c:forEach>
                </select></td>
            </c:forEach>
            <td>
                <input type="hidden" name="train_id" value="${train_id}">
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

<form action="controller?action=select_carriage_and_count_seats" method="POST">
    <input type="hidden" name="station1" value="${station1}">
    <input type="hidden" name="travel_time" value="${travel_time}">
    <input type="hidden" name="station2" value="${station2}">
    <input type="hidden" name="routs_id" value="${routs_id}">
    <input type="hidden" name="train_id" value="${train_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="hidden" name="departure_station_id" value="${departure_station_id}">
    <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
    <input type="hidden" name="car_type" value="${car_type}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
