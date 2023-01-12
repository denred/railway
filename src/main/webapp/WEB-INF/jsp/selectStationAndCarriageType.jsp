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

<form action="controller?action=select_carriage_and_count_seats" method="POST">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 800px;">
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
                <%--1--%>
                <td><select class="btn btn-info dropdown-toggle" name="departure_station_id">
                    <c:set var="departure_station" value="${departure_station}"/>
                    <c:forEach items="${station_list}" var="stationList">
                        <option
                                <c:choose>
                                    <c:when test="${departure_station eq stationList.station}">
                                        selected
                                    </c:when>
                                </c:choose>
                                value="${stationList.stationId}"><c:out value="${stationList.station}"/>
                        </option>
                    </c:forEach>
                </select>
                </td>
                <%--2--%>
                <td><select class="btn btn-info dropdown-toggle" name="arrival_station_id">
                    <c:set var="arrival_station" value="${arrival_station}"/>
                    <c:forEach items="${station_list}" var="stationList">
                        <option
                                <c:choose>
                                    <c:when test="${arrival_station eq stationList.station}">
                                        selected
                                    </c:when>
                                </c:choose>
                                value="${stationList.stationId}"><c:out value="${stationList.station}"/>
                        </option>
                    </c:forEach>
                </select></td>
                <%--3--%>
                <td><select class="btn btn-info dropdown-toggle" name="car_type">
                    <c:forEach items="${carTypeList}" var="car_type">
                        <option value="${car_type}"><fmt:message key="${car_type}"/></option>
                    </c:forEach>
                </select></td>
                <td>
                    <input type="hidden" name="station1" value="${station1}">
                    <input type="hidden" name="travel_time" value="${travel_time}">
                    <input type="hidden" name="station2" value="${station2}">
                    <input type="hidden" name="routs_id" value="${routs_id}">
                    <input type="hidden" name="user_id" value="${user_id}">
                    <input type="hidden" name="departure_station" value="${departure_station}">
                    <input type="hidden" name="arrival_station" value="${arrival_station}">
                    <input type="hidden" name="departure_date" value="${departure_date}">
                    <input type="hidden" name="train_id" value="${train_id}">
                    <input type="submit" class="btn btn-info" name="add_order"
                           value="<fmt:message key="next"/>">
                </td>
            </tr>
        </table>
    </div>
</form>
<form action="controller?action=search_routes" method="POST">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
