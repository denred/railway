<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.order.information"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
          rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet"/>
    <!-- Libraries Stylesheet -->
    <link href="../../lib/animate/animate.min.css" rel="stylesheet"/>
    <link href="../../lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet"/>
    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Template Stylesheet -->
    <link href="../../css/style.css" rel="stylesheet"/>
</head>

<body>
<mrt:navigation/>
<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>

<h3 style="text-align: center;">
    <fmt:message key="admin.order.list"/>
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
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="order" items="${order_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${order.user.firstName}<br> ${order.user.lastName}</td>
                    <td>${order.trainNumber}</td>
                    <td>${order.routeName}</td>
                    <td><fmt:message key="${order.carrType}"/></td>
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
                                       locale="${language}"/>
                    </td>

                    <td><span>${order.orderDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</span></td>
                    <td>
                        <a href="administrator_edit_info_order?order_id=${order.id}">
                            <fmt:message key="${order.orderStatus}"/></a>
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
                        <a class="page-link" href="administrator_info_order?page=${currentPage - 1}"
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
                                                     href="administrator_info_order?page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="administrator_info_order?page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>