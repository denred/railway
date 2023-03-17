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
            <th><fmt:message key="order.id"/></th>
            <th><fmt:message key="order.user.information"/></th>
            <th><fmt:message key="route.from.to"/></th>
            <th><fmt:message key="date"/></th>
            <th><fmt:message key="order.date"/></th>
            <th><fmt:message key="order.status"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${order_list}" varStatus="i">
            <tr>
                <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                <td><a href="controller?action=order_detail&order=${i.index}"> ${order.uuid}</a></td>
                <td>${order.user.firstName} ${order.user.lastName}</td>
                <td>${order.dispatchStation} - ${order.arrivalStation}</td>
                <td>${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))} -
                        ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}
                </td>
                <td>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
                <td><fmt:message key="${order.orderStatus}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}"
                 url="controller?action=admin_orders"/>

<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>