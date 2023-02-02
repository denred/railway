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
<%--  ================================  --%>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=route_mapping_set_station" method="POST">
            <input type="hidden" name="routs_id" value="${routs_id}">
            <input type="hidden" name="station_current_id" value="${station_id}">
            <input type="hidden" name="operationStatus" value="${operationStatus}">

            <div class="row">
                <div class="col-sm-3">
                    <label for="routeOrder"><fmt:message key="order"/></label>
                    <input id="routeOrder" class="form-control" name="station_order"
                           value="${current_rout.order}">
                </div>
                <div class="col-sm-3">
                    <label for="stationId"><fmt:message key="station.name"/></label>
                    <select id="stationId" class="btn btn-info dropdown-toggle" name="station_station">
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
                </div>
                <div class="col-sm-3">
                    <label for="arrivalDate"><fmt:message key="arrivalDate"/></label>
                    <input id="arrivalDate" class="form-control" name="station_arrival"
                           type="datetime-local" value="${current_rout.stationArrivalDate}">
                </div>

                <div class="col-sm-3">
                    <label for="dispatchDate"><fmt:message key="dispatchDate"/></label>
                    <input id="dispatchDate" class="form-control" name="station_dispatch"
                           type="datetime-local" value="${current_rout.stationDispatchData}">
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-lg-2">
                    <a href="controller?action=route_mapping&routs_id=${routs_id}"
                       class="btn bg-gradient-blue text-primary mb-0">
                        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                        <fmt:message key="back"/></a>
                </div>
                <div class="col-lg-3">
                    <button class="btn bg-gradient-blue text-success" type="submit">
                        <i class="far fa-check-circle" aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/></button>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>