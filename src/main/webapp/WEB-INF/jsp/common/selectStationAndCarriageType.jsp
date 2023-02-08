<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container">
    <form action="controller?action=select_carriage_and_count_seats" method="POST">
        <div class="d-flex justify-content-center">
            <table class="table table-bordered table-hover caption-top">
                <thead class="thead-light text-center">
                <tr>
                    <th><fmt:message key="station.dispatch"/></th>
                    <th><fmt:message key="station.arrival"/></th>
                    <th><fmt:message key="car.type"/></th>
                    <th><fmt:message key="order.make.order"/></th>
                </tr>
                </thead>
                <tbody class="align-middle text-center">
                <tr>
                    <td>
                        <label for="departureStation"></label>
                        <select id="departureStation" class="btn btn-info dropdown-toggle" name="departure_station_id">
                            <c:forEach items="${station_list}" var="station">
                                <option
                                        <c:choose>
                                            <c:when test="${departure_station eq station.station}">
                                                selected
                                            </c:when>
                                        </c:choose>
                                        value="${station.stationId}"><c:out value="${station.station}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <label for="arrivalStation"></label>
                        <select id="arrivalStation" class="btn btn-info dropdown-toggle" name="arrival_station_id">
                            <c:forEach items="${station_list}" var="station">
                                <option
                                        <c:choose>
                                            <c:when test="${arrival_station eq station.station}">
                                                selected
                                            </c:when>
                                        </c:choose>
                                        value="${station.stationId}"><c:out value="${station.station}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <label for="carriage"></label>
                        <select id="carriage" class="btn btn-info dropdown-toggle" name="car_type">
                            <c:forEach items="${carTypeList}" var="car_type">
                                <option value="${car_type}"><fmt:message key="${car_type}"/></option>
                            </c:forEach>
                        </select></td>
                    <td>
                        <input type="hidden" name="travel_time" value="${travel_time}">
                        <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                        <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                        <input type="submit" class="btn btn-info" name="add_order"
                               value="<fmt:message key="next"/>">
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <a href="controller?action=search_routes" class="btn btn-primary"><fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
