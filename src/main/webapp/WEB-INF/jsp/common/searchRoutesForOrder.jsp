<%-- Include files --%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<%-- Import classes --%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<%-- Set the page language --%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<%-- Header components --%>
<jsp:include page="/WEB-INF/templates/_head.jsp"/>
<head>
    <title><fmt:message key="rout.search"/></title>
</head>
<body>
<%-- Choose navigation menu --%>
<tags:navigation/>
<%-- Roles --%>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container">
    <h3 class="row d-flex justify-content-center my-2"><fmt:message key="search.result"/></h3>
    <form method="POST" action="controller?action=search_routes">
        <div class="form-check">
            <input id="check-free-seats" class="form-check-input" type="checkbox" name="stable" value="true"
                    onclick="this.form.submit()" <c:if test="${stable}">checked="checked"</c:if>>
            <label class="form-check-label" for="check-free-seats">
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
                <table class="table table-hover caption-top">
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
                        <th>
                            <div class="row">
                                <div class="col-sm-3">
                                    <fmt:message key="order.car.type"/>
                                </div>
                                <div class="col-sm-2">
                                    <fmt:message key="free.seats.count"/>
                                </div>
                                <div class="col-sm-3">
                                    <fmt:message key="car.price"/>
                                </div>
                                <div class="col-sm-4"></div>
                            </div>
                        </th>
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
                                        <div class="col-md-3 d-flex align-items-center justify-content-center">
                                            <fmt:message key="${entry.key}"/></div>
                                        <div class="col-md-2 d-flex align-items-center justify-content-center">${entry.value}</div>
                                        <c:if test="${entry.value>0}">
                                            <c:set var="checkFreeSeats" value="true"/>
                                        </c:if>
                                        <div class="col-md-3 d-flex align-items-center justify-content-center">
                                            <span>${entry.key.price} &#8372;</span>
                                        </div>
                                        <div class="col-md-4 d-flex align-items-center justify-content-center">
                                            <c:if test="${checkFreeSeats}">
                                                <form class="d-inline m-0"
                                                      action="controller?action=select_station_and_carriage_type"
                                                      method="POST">
                                                    <input type="hidden" name="routs_id"
                                                           value="${rout.routsId}">
                                                    <input type="hidden" name="train_id"
                                                           value="${rout.trainId}">
                                                    <input type="hidden" name="car_type"
                                                           value="${entry.key}">
                                                    <input type="hidden" name="departure_station_id"
                                                           value="${rout.stations.get(0).stationId}">
                                                    <input type="hidden" name="travel_time"
                                                           value="<period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}" locale="${sessionScope.locale}"/>">
                                                    <input type="hidden" name="arrival_station_id"
                                                           value="${rout.stations.get(1).stationId}">

                                                    <button type="submit"
                                                            class="btn bg-gradient-green text-primary mb-0">
                                                        <i class="fa fa-check" aria-hidden="true"></i>
                                                        <fmt:message key="order.choose"/>
                                                    </button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}" url="controller?action=search_routes&stable=${stable}"/>

    <a href="controller?action=home" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>