<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<jsp:include page="/WEB-INF/templates/_head.jsp"/>
<head>
    <title><fmt:message key="user.account"/></title>
</head>
<body>
<mrt:navigation/>

<h3 style="text-align: center;">
    <fmt:message key="user.order.information"/>
</h3>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="order"/></th>
                <th><fmt:message key="order.id"/></th>
                <th><fmt:message key="order.train.number"/></th>
                <th><fmt:message key="route.from.to"/></th>
                <th><fmt:message key="date"/></th>
                <th><fmt:message key="order.date"/></th>
                <th><fmt:message key="order.price"/></th>
                <th><fmt:message key="order.status"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="order" items="${order_list}" varStatus="i">
                <tr>
                    <td class="align-middle">${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td class="align-middle">
                        <form class="m-0" action="controller?action=view_tickets" method="POST">
                            <input type="hidden" name="orderUuid" value="${order.uuid}">
                            <button type="submit" class="btn btn-link mb-0">${order.uuid}</button>
                        </form>
                    </td>
                    <td class="align-middle">${order.trainNumber}</td>
                    <td class="align-middle"><span>${order.dispatchStation} - ${order.arrivalStation}</span></td>
                    <td class="align-middle">
                        <div class="row">
                            <div class="col-md-4">
                                <fmt:message key="departure"/>:
                            </div>
                            <div class="col-md-8">
                                    ${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <fmt:message key="arrival"/>:
                            </div>
                            <div class="col-md-8">
                                    ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                            </div>
                        </div>
                    </td>
                    <td class="align-middle"><span>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</span></td>
                    <td class="align-middle">${order.price}</td>
                    <td class="align-middle"><fmt:message key="${order.orderStatus}"/></td>
                    <td class="align-middle">
                        <form class="m-0" action="controller?action=cancel_order" method="POST">
                            <input type="hidden" name="order_id" value="${order.uuid}">
                            <input type="hidden" name="user_id" value="${user_id}">

                            <button class="btn bg-gradient-red text-danger">
                                <i class="bi bi-x-circle" aria-hidden="true"></i> <fmt:message key="button.decline"/>
                            </button>

                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}"
                     url="controller?action=orders"/>
</div>
</body>
</html>