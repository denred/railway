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
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<h2 style="text-align: center;">
    <fmt:message key="account"/>
</h2>

<h3 style="text-align: center;">
    <fmt:message key="user.order.information"/>
</h3>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
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
                <th style="width: 12%"></th>
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
                                ${order.dispatchDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                        </span>
                        </div>

                        <div class="row">
                            <span class="text-start"><fmt:message key="arrival"/>:</span>
                        </div>
                        <div class="row">
                        <span class="text-start">
                                ${order.arrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                        </span>
                        </div>
                    </td>
                    <td><period:period dateFrom="${order.arrivalDate}" dateTo="${order.dispatchDate}"
                                       locale="${lang}"/>
                    </td>

                    <td><span>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</span></td>
                    <td><fmt:message key="${order.orderStatus}"/></td>
                    <td>
                        <form action="controller?action=cancel_order" method="POST">
                            <input type="hidden" name="order_id" value="${order.id}">
                            <input type="hidden" name="user_id" value="${user_id}">
                            <input type="submit" class="btn btn-info" name="edit_info_order"
                                   value="<fmt:message key="decline"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="d-flex justify-content-center">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=orders&user_id=${user_id}&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=orders&user_id=${user_id}&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=orders&user_id=${user_id}&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>