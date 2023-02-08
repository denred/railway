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
    <%-- Pagination --%>
    <div class="d-flex justify-content-center">
        <nav aria-label="Page navigation">
            <ul class="pagination ">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=route&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page">
                                <a class="page-link" href="#">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=route&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                <c:if test="${last_page gt noOfPages}">
                    <li class="page-item disabled"><a class="page-link circle circle-md"
                                                      href="#"><span>...</span></a></li>
                    <c:choose>
                        <c:when test="${currentPage eq last_page}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="controller?action=route&page=${last_page}">${last_page}</a>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <c:if test="${currentPage gt noOfPages}">
                                <li class="page-item active">
                                    <a class="page-link"
                                       href="controller?action=route&page=${currentPage}">${currentPage}</a>
                                </li>
                                <li class="page-item disabled">
                                    <a class="page-link circle circle-md"
                                       href="#"><span>...</span></a></li>
                            </c:if>
                            <li class="page-item">
                                <a class="page-link"
                                   href="controller?action=route&page=${last_page}">${last_page}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${currentPage lt last_page}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=route&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <a href="controller?action=search_routes" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>