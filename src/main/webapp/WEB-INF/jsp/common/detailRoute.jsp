<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<div class="container">
    <h3 class="row d-flex justify-content-center"><fmt:message key="route.detail"/></h3>
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top">
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
                    <td>
                        <c:if test="${item.order ne 1}">
                            ${item.stationArrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                        </c:if>
                    </td>
                    <td>
                        <c:set var="endLoop" value="${last_station}"/>
                        <c:if test="${item.order ne endLoop}">
                            ${item.stationDispatchData.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${item.order ne 1 and item.order ne endLoop}">
                            <period:period dateFrom="${item.stationArrivalDate}" dateTo="${item.stationDispatchData}"
                                           locale="${sessionScope.locale}"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}" url="controller?action=route"/>
    <a href="controller?action=search_routes" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>