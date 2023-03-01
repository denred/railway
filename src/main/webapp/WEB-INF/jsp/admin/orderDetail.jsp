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
<div class="container mt-4 mx-auto">
    <h3 class="text-center mb-2"><fmt:message key="admin.order.detail"/></h3>
    <c:set var="order" scope="session" value="${order_list.get(index)}"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <th><fmt:message key="order.user.information"/>:</th>
            <td>${order.user.firstName} ${order.user.lastName}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.train.number"/>:</th>
            <td>${order.trainNumber}</td>
        </tr>
        <tr>
            <th><fmt:message key="rout.name"/>:</th>
            <td>${order.routeName}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.car.type"/>:</th>
            <td><fmt:message key="${order.carriageType}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="order.car.number"/>:</th>
            <td>${order.carriageNumber}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.seats.number"/>:</th>
            <td>${order.seatNumber}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.price"/>:</th>
            <td>${order.price}</td>
        </tr>
        <tr>
            <th><fmt:message key="filter.departure.station"/>:</th>
            <td>${order.dispatchStation} ${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
        </tr>
        <tr>
            <th><fmt:message key="filter.arrival.station"/>:</th>
            <td>${order.arrivalStation} ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.travel.time"/>:</th>
            <td><period:period dateFrom="${order.arrivalDate}" dateTo="${order.dispatchDate}"
                               locale="${sessionScope.locale}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="order.date"/>:</th>
            <td>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
        </tr>
        <tr>
            <th><fmt:message key="order.status"/>:</th>
            <td>
                <form action="controller?action=order_status" method="POST">
                    <input type="hidden" name="order_id" value="${order.id}">
                    <label for="change-status"></label>
                    <select id="change-status" class="btn form-control dropdown-toggle w-25" name="order_status">
                        <c:set var="current_order_status" value="${order.orderStatus}"/>
                        <c:forEach items="${statusList}" var="status">
                            <option
                                    <c:choose>
                                        <c:when test="${status eq current_order_status}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${status}"><fmt:message key="${status}"/>
                            </option>
                        </c:forEach>
                    </select>

                    <button class="btn bg-gradient-blue text-success" type="submit" aria-label="Save">
                        <i class="far fa-check-circle" aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/>
                    </button>

                </form>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="row mt-4">
        <div class="col-lg-6">
            <a href="controller?action=admin_orders" class="btn bg-gradient-blue text-primary mb-0" aria-label="Back">
                <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                <fmt:message key="back"/>
            </a>
        </div>
        <div class="col-lg-6">

        </div>
    </div>
</div>
</body>
</html>