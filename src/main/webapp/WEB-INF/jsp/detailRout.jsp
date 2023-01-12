<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<jsp:include page="/WEB-INF/templates/_head.jsp"/>
<head>
    <title><fmt:message key="admin.details.rout"/></title>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="d-flex justify-content-center">
    <table class="table table-bordered table-hover caption-top" style="width: 800px;">
        <thead class="thead-light text-center">
        <tr>
            <th><fmt:message key="order"/></th>
            <th><fmt:message key="station.name"/></th>
            <th><fmt:message key="arrivalDate"/></th>
            <th><fmt:message key="dispatchDate"/></th>
            <th><fmt:message key="parkingTime"/></th>
        </tr>
        </thead>
        <tbody class="align-middle text-center">
        <c:forEach items="${rout_m_list}" var="item">
            <tr>
                <td>${item.order}</td>
                <td>${item.station}</td>
                <td>${item.stationArrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
                <td>${item.stationDispatchData.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
                <td><period:period dateFrom="${item.stationArrivalDate}" dateTo="${item.stationDispatchData}"
                                   locale="${lang}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<p>
<form action="controller?action=search_routes" method="POST">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</p>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>