<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.order.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<tags:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.order.list"/>
</h3>
<div class="container mt-4">
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="order"/></th>
            <th><fmt:message key="order.number"/></th>
            <th><fmt:message key="order.user.information"/></th>
            <th><fmt:message key="route.from.to"/></th>
            <th><fmt:message key="date"/></th>
            <th><fmt:message key="order.date"/></th>
            <th><fmt:message key="order.status"/></th>
            <th><fmt:message key="admin.details"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${order_list}" varStatus="i">
            <tr>
                <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                <td>${order.hashCode()}</td>
                <td>${order.user.firstName} ${order.user.lastName}</td>
                <td>${order.dispatchStation} - ${order.arrivalStation}</td>
                <td>${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))} -
                        ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}
                </td>
                <td>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
                <td><a href="controller?action=order_status&order_id=${order.id}">
                    <fmt:message key="${order.orderStatus}"/></a></td>
                <td><a href="controller?action=order_detail&order=${i.index}">
                    <fmt:message key="admin.details"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--
        <table class="table table-bordered table-hover caption-top" style="width: 1400px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 10%"><fmt:message key="order.user.information"/></th>
                <th style="width: 10%"><fmt:message key="order.train.number"/></th>
                <th style="width: 10%"><fmt:message key="rout.name"/></th>
                <th style="width: 8%"><fmt:message key="order.car.type"/></th>
                <th style="width: 8%"><fmt:message key="order.car.number"/></th>
                <th style="width: 5%"><fmt:message key="order.count.of.seats"/></th>
                <th style="width: 10%"><fmt:message key="order.seats.number"/></th>
                <th style="width: 10%"><fmt:message key="order.price"/></th>
                <th style="width: 15%"><fmt:message key="route.from.to"/></th>
                <th style="width: 30%"><fmt:message key="date"/></th>
                <th style="width: 10%"><fmt:message key="order.travel.time"/></th>
                <th style="width: 10%"><fmt:message key="order.date"/></th>
                <th style="width: 12%"><fmt:message key="order.status"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="order" items="${order_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${order.user.firstName}<br> ${order.user.lastName}</td>
                    <td>${order.trainNumber}</td>
                    <td>${order.routeName}</td>
                    <td><fmt:message key="${order.carriageType}"/></td>
                    <td>${order.carriageNumber}</td>
                    <td>${order.countOfSeats}</td>
                    <td>${order.seatNumber}</td>
                    <td>${order.price}</td>
                    <td>
                        <span>${order.dispatchStation} - ${order.arrivalStation}</span>
                    </td>
                    <td>
                        <div class="row">
                            <span class="text-start"><fmt:message key="departure"/>:</span>
                        </div>
                        <div class="row">
                            <span class="text-start">
                                    ${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}
                            </span>
                        </div>

                        <div class="row">
                            <span class="text-start"><fmt:message key="arrival"/>:</span>
                        </div>
                        <div class="row">
                            <span class="text-start">
                                    ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}
                            </span>
                        </div>
                    </td>
                    <td><period:period dateFrom="${order.arrivalDate}" dateTo="${order.dispatchDate}"
                                       locale="${lang}"/>
                    </td>

                    <td><span>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</span></td>
                    <td>
                        <a href="controller?action=order_status&order_id=${order.id}">
                            <fmt:message key="${order.orderStatus}"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>--%>
</div>

<tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}"
                 url="controller?action=admin_orders"/>

<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>