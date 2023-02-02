<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.details.rout"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container mt-4">
    <h3 class="text-center mb-4"><fmt:message key="admin.details.rout"/></h3>
    <div class="d-flex justify-content-center">
        <table class="table table-hover table-sm">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th class="text-start" style="width: 20%"><fmt:message key="station.name"/></th>
                <th style="width: 20%"><fmt:message key="arrivalDate"/></th>
                <th style="width: 20%"><fmt:message key="dispatchDate"/></th>
                <th style="width: 30%"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rout_m_list}" var="item">
                <tr>
                    <td class="text-center align-middle">${item.order}</td>
                    <td class="align-middle">${item.station}</td>
                    <td class="text-center align-middle">
                        <c:choose>
                            <c:when test="${item.order eq 1}">
                                <p>---</p>
                            </c:when>
                            <c:otherwise>
                                ${item.stationArrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center align-middle">
                        <c:choose>
                            <c:when test="${item.order eq last_station}">
                                <p>---</p>
                            </c:when>
                            <c:otherwise>
                                ${item.stationDispatchData.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="form-row text-center align-middle">
                            <div class="col-sm-6">
                                <form class="mb-0" action="controller?action=route_mapping_set_station" method="POST">
                                    <input type="hidden" name="station_id" value="${item.stationId}">
                                    <input type="hidden" name="routs_id" value="${item.routsId}">
                                    <input type="hidden" name="operationStatus" value="change">
                                    <button type="submit" class="btn btn-link text-dark my-0 justify-content-center">
                                        <i class="fas fa-pencil-alt text-dark" aria-hidden="true"></i>
                                        <fmt:message key="admin.editInformation"/>
                                    </button>
                                </form>
                            </div>
                            <div class="col-sm-6">
                                <form class="mb-0" action="controller?action=route_mapping_remove_station"
                                      method="POST">
                                    <input type="hidden" name="routs_id" value="${item.routsId}">
                                    <input type="hidden" name="station_id" value="${item.stationId}">
                                    <button type="submit" class="btn btn-link text-danger text-gradient my-0">
                                        <i class="far fa-trash-alt" aria-hidden="true"></i>
                                        <fmt:message key="admin.remove"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="row mt-1">
        <%--<div class="col-md-1"></div>--%>
        <div class="col-md-6">
            <a href="controller?action=routes" class="btn bg-gradient-blue text-primary mb-0">
                <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
                <fmt:message key="back"/></a>
        </div>
        <div class="col-md-6 text-end">
            <form action="controller?action=route_mapping_set_station" method="POST">
                <input type="hidden" name="routs_id" value="${routs_id}">
                <input type="hidden" name="operationStatus" value="add">
                <button class="btn bg-gradient-blue text-success" type="submit">
                    <i class="fas fa-plus" aria-hidden="true"></i>
                    <fmt:message key="admin.addStationMapping"/></button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>