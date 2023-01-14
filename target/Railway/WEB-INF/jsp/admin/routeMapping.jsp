<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

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
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 10%"><fmt:message key="station.name"/></th>
                <th style="width: 20%"><fmt:message key="arrivalDate"/></th>
                <th style="width: 20%"><fmt:message key="dispatchDate"/></th>
                <th style="width: 20%"><fmt:message key="edit"/></th>
                <th style="width: 10%"><fmt:message key="delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rout_m_list}" var="item">
                <tr>
                    <td>${item.order}</td>
                    <td>${item.station}</td>
                    <td>${item.stationArrivalDate}</td>
                    <td>${item.stationDispatchData}</td>
                    <td>
                        <form action="administrator_edit_info_details_set_rout" method="GET">
                            <input type="hidden" name="station_id" value="${item.stationId}">
                            <input type="hidden" name="routs_id" value="${item.routsId}">
                            <input type="submit" class="btn btn-info" name="edit_info_rout_mapping"
                                   value="<fmt:message key="admin.editInformation"/>">
                        </form>
                    </td>
                    <td>
                        <form action="remove_rout_mapping" method="POST">
                            <input type="hidden" name="routs_id" value="${item.routsId}">
                            <input type="hidden" name="station_id" value="${item.stationId}">
                            <input type="submit" class="btn btn-danger" name="remove_rout_to_station_mapping"
                                   value="<fmt:message key="admin.remove"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <form action="controller?action=route_mapping_add_station" method="POST">
        <input type="hidden" name="routs_id" value="${routs_id}">
        <input type="submit" class="btn btn-success" name="add_rout_mapping"
               value="<fmt:message key="admin.addStationMapping"/>">
    </form>
    <p>
    <form action="controller?action=routes" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
    </p>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>