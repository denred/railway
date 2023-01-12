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

<form action="select_seats_for_order" method="GET">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 600px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="car.number"/></th>
                <th><fmt:message key="count.of.seats"/></th>
                <th><fmt:message key="order.make.order"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <tr>
                <td>
                    <select class="btn btn-info dropdown-toggle" name="car_id">
                        <c:forEach var="carriage" items="${car_list}">
                            <option value="${carriage.carriageId}"><c:out value="${carriage.number}"/></option>
                        </c:forEach>
                    </select>
                </td>
                <td><input class="form-control" name="count_of_seats"></td>
                <td>
                    <input type="hidden" name="station1" value="${station1}">
                    <input type="hidden" name="travel_time" value="${travel_time}">
                    <input type="hidden" name="station2" value="${station2}">
                    <input type="hidden" name="routs_id" value="${routs_id}">
                    <input type="hidden" name="train_id" value="${train_id}">
                    <input type="hidden" name="user_id" value="${user_id}">
                    <input type="hidden" name="departure_station" value="${departure_station}">
                    <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                    <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                    <input type="hidden" name="arrival_station" value="${arrival_station}">
                    <input type="hidden" name="departure_date" value="${departure_date}">
                    <input type="hidden" name="car_type" value="${car_type}">
                    <input type="submit" class="btn btn-success" name="add_order"
                           value="<fmt:message key="next"/>">
                </td>
            </tr>
        </table>
    </div>
</form>
<form action="controller?action=select_station_and_carriage_type" method="POST">
    <input type="hidden" name="station1" value="${station1}">
    <input type="hidden" name="travel_time" value="${travel_time}">
    <input type="hidden" name="station2" value="${station2}">
    <input type="hidden" name="routes_id" value="${routs_id}">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="hidden" name="train_id" value="${train_id}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
