<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<jsp:include page="/WEB-INF/templates/_head.jsp"/>
<head>
    <title><fmt:message key="rout.search"/></title>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container">
    <h3 class="row d-flex justify-content-center my-2"><fmt:message key="search.result"/></h3>
    <form method="POST" action="controller?action=search_routes">
        <div class="form-check">
            <input name="stable" value="true" class="form-check-input" type="checkbox"
                   id="checkFreeSeats" onclick="this.form.submit()" <c:if test="${stable}">checked="checked"</c:if>/>
            <label class="form-check-label" for="checkFreeSeats">
                <fmt:message key="train.show.not.available.seats"/>
            </label>
        </div>
    </form>
    <c:choose>
        <c:when test="${empty sessionScope.rout_order_dto_list}">
            <div class="text-xs-center align-middle h3" style="text-align: center;">
                <fmt:message key="rout.no"/></div>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-center">
                <table class="table table-hover caption-top" style="width: 1400px;">
                    <thead class="thead-light text-center">
                    <tr>
                        <th><fmt:message key="train.number"/></th>
                        <th><fmt:message key="rout.from"/><br><fmt:message key="rout.to"/></th>
                        <th>
                            <div class="row">
                                <div class="col-md-4"></div>
                                <div class="col-md-4"><fmt:message key="date"/></div>
                                <div class="col-md-4 text-end"><fmt:message key="time"/></div>
                            </div>
                        </th>
                        <th><fmt:message key="order.travel.time"/></th>
                        <th><fmt:message key="free.seats.count"/><br></th>
                        <th><fmt:message key="car.price"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${sessionScope.rout_order_dto_list}" var="rout">
                        <tr>
                            <c:set var="dispatchDateTime" value="${rout.stations.get(0).stationDispatchDateTime}"/>
                            <c:set var="arrivalDateTime"
                                   value="${rout.stations.get(rout.stations.size() - 1).stationArrivalDateTime}"/>

                            <td>${rout.trainNumber}
                                <form action="controller?action=route" method="POST">
                                    <input type="hidden" name="routs_id" value="${rout.routsId}">
                                    <input type="submit" class="btn btn-link" name="order"
                                           value="<fmt:message key="route"/>">
                                </form>
                            </td>
                            <td>${rout.stations.get(0).station} <br> ${rout.stations.get(1).station}</td>
                            <td>
                                <div class="row">
                                    <span class="col-md-4 text-start"><fmt:message key="departure"/></span>
                                    <span class="col-md-4 text-end">${dispatchDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</span>
                                    <span class="col-md-4 text-end">${dispatchDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}</span>
                                </div>
                                <div class="row">
                                    <span class="col-md-4 text-start"><fmt:message key="arrival"/></span>
                                    <span class="col-md-4 text-end">${arrivalDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</span>
                                    <span class="col-md-4 text-end">${arrivalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}</span>
                                </div>
                            </td>
                            <td><period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}"
                                               locale="${sessionScope.locale}"/></td>

                            <td>
                                <c:set var="checkFreeSeats" value="${false}"/>
                                <c:forEach items="${rout.availableSeats}" var="entry">
                                    <div class="row">
                                        <span class="col-md-8 text-start"><fmt:message key="${entry.key}"/></span>
                                        <span class="col-md-4 text-end">${entry.value}</span>
                                        <c:if test="${entry.value>0}">
                                            <c:set var="checkFreeSeats" value="true"/>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${rout.availableSeats}" var="entry">
                                    <span>${entry.key.price} &#8372;</span><br>
                                </c:forEach>
                            </td>

                            <td>
                                <c:if test="${checkFreeSeats}">
                                    <form action="controller?action=select_station_and_carriage_type" method="POST">
                                        <input type="hidden" name="routs_id" value="${rout.routsId}">
                                        <input type="hidden" name="train_id" value="${rout.trainId}">
                                        <input type="hidden" name="station1"
                                               value="${rout.stations.get(0).station}">
                                        <input type="hidden" name="travel_time"
                                               value="<period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}" locale="${sessionScope.locale}"/>">
                                        <input type="hidden" name="station2"
                                               value="${rout.stations.get(1).station}">
                                        <input type="submit" class="btn btn-info" name="order"
                                               value="<fmt:message key="order.make.order"/>">
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
    <%-- Pagination --%>
    <div class="d-flex justify-content-center">
        <nav aria-label="Page navigation">
            <ul class="pagination ">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=search_routes&page=${currentPage - 1}"
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
                                                     href="controller?action=search_routes&page=${i}">${i}</a></li>
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
                                   href="controller?action=search_routes&page=${last_page}">${last_page}</a>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <c:if test="${currentPage gt noOfPages}">
                                <li class="page-item active">
                                    <a class="page-link"
                                       href="controller?action=search_routes&page=${currentPage}">${currentPage}</a>
                                </li>
                                <li class="page-item disabled">
                                    <a class="page-link circle circle-md"
                                       href="#"><span>...</span></a></li>
                            </c:if>
                            <li class="page-item">
                                <a class="page-link"
                                   href="controller?action=search_routes&page=${last_page}">${last_page}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${currentPage lt last_page}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=search_routes&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <a href="controller?action=home" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
