<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.route.mapping"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=route_mapping_add_station" method="POST">
            <table class="table table-bordered text-center" style="width: auto">
                <thead class="thead-light text-center">
                <tr>
                    <th><fmt:message key="order"/></th>
                    <th><fmt:message key="station.name"/></th>
                    <th><fmt:message key="arrivalDate"/></th>
                    <th><fmt:message key="dispatchDate"/></th>
                    <th><fmt:message key="admin.editInformation"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <input type="hidden" name="routs_id" value="${routs_id}">
                    <input type="hidden" name="station_current_id" value="${station_id}">
                    <td><input class="form-control" name="station_order" value="${current_rout.order}"></td>
                    <td><select class="btn btn-info dropdown-toggle" name="station_station">
                        <c:set var="station_id" value="${current_rout.stationId}"/>
                        <c:forEach items="${station_list}" var="station">
                            <option
                                    <c:choose>
                                        <c:when test="${station.id eq station_id}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${station.id}"><c:out value="${station.station}"/>
                            </option>
                        </c:forEach>
                    </select>
                    </td>
                    <td><input class="form-control" name="station_arrival_date" type="datetime-local"
                               value="${current_rout.stationArrivalDate}">
                    </td>
                    <td><input class="form-control" name="station_dispatch_data" type="datetime-local"
                               value="${current_rout.stationDispatchData}">
                    </td>
                    <td>
                        <input type="submit" class="btn btn-success" name="add_rout_mapping"
                               value="<fmt:message key="admin.addStationMapping"/>">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <p>
    <form action="controller?action=route_mapping" method="POST">
        <input type="hidden" name="routs_id" value="${routs_id}">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
    </p>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>