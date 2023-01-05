<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>
<%@ page session="true" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="rout.search"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<mrt:navigation/>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

<div class="d-flex justify-content-end">
    <div class="h5 mr-auto p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>


<c:choose>
    <c:when test="${empty rout_list}">
        <div class="text-xs-center align-middle h3" style="text-align: center;">
            <fmt:message key="rout.no"/></div>
    </c:when>
    <c:otherwise>
        <div class="container d-flex align-items-center justify-content-center">
            <table class="table table-bordered table-hover text-lg-start" style="width: auto">
                <thead class="thead-light text-center text-bg-secondary align-middle">
                <tr>
                    <th><fmt:message key="train.number"/></th>
                    <th><fmt:message key="route.from.to"/></th>
                    <th><fmt:message key="date"/></th>
                    <th><fmt:message key="departure"/><br><fmt:message key="arrival"/></th>
                    <th><fmt:message key="order.travel.time"/></th>
                    <th><fmt:message key="free.seats.count"/></th>
                    <th><fmt:message key="car.price"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody class="text-center">
                <c:forEach items="${rout_list}" var="rout">
                    <tr>
                        <c:set var="dispatchDateTime" value="${rout.stations.get(0).stationDispatchDateTime}"/>
                        <c:set var="arrivalDateTime"
                               value="${rout.stations.get(rout.stations.size() - 1).stationArrivalDateTime}"/>
                        <c:set var="dispatchDate" value="${rout.stations.get(0).stationDispatchDate}"/>
                        <c:set var="dispatchTime" value="${rout.stations.get(0).stationDispatchTime}"/>
                        <c:set var="arrivalDate"
                               value="${rout.stations.get(rout.stations.size() - 1).stationArrivalDate}"/>
                        <c:set var="arrivalTime"
                               value="${rout.stations.get(rout.stations.size() - 1).stationArrivalTime}"/>

                        <td>${rout.trainNumber}
                            <form action="detail_rout" method="GET">
                                <input type="hidden" name="routs_id" value="${rout.routsId}">
                                <input type="hidden" name="user_id" value="${user_id}">
                                <input type="hidden" name="departure_station" value="${departure_station}">
                                <input type="hidden" name="arrival_station" value="${arrival_station}">
                                <input type="hidden" name="departure_date" value="${departure_date}">
                                <input type="submit" class="btn btn-link" name="oder"
                                       value="<fmt:message key="route"/>">
                            </form>
                        </td>
                        <td>${rout.stations.get(0).station} - ${rout.stations.get(1).station}</td>
                        <td>
                            <div class="row">
                                <span class="col-md-6 text-start"><fmt:message key="departure"/></span>
                                <span class="col-md-6 text-end">${dispatchDate}</span>
                            </div>
                            <div class="row">
                                <span class="col-md-6 text-start"><fmt:message key="arrival"/></span>
                                <span class="col-md-6 text-end">${arrivalDate}</span>
                            </div>
                        </td>
                        <td>
                                ${dispatchTime}<br>
                                ${arrivalTime}
                        </td>
                        <td><period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}"
                                           locale="${language}"/></td>

                        <td>
                        <c:forEach items="${seatsCount}" var="entry">
                            <div class="row">
                                <span class="col-md-8 text-start"><fmt:message key="${entry.key}"/></span>
                                <span class="col-md-4 text-end">${entry.value}</span>
                            </div>
                        </c:forEach>
                        </td>
                        <td>
                        <c:forEach items="${seatsPrice}" var="entry">
                        <span>${entry.value} UHA</span><br>
                        </c:forEach>
                        </td>

                        <td>
                            <form action="select_station_and_car_type_for_order" method="GET">
                                <input type="hidden" name="routs_id" value="${rout.routsId}">
                                <input type="hidden" name="train_id" value="${rout.trainId}">
                                <input type="hidden" name="station1"
                                       value="${rout.stations.get(0).station} - ${dispatchTime}">
                                <input type="hidden" name="travel_time"
                                       value="<period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}" locale="${language}"/>">
                                <input type="hidden" name="station2"
                                       value="${rout.stations.get(1).station} - ${arrivalTime}">
                                <input type="hidden" name="user_id" value="${user_id}">
                                <input type="hidden" name="departure_station" value="${departure_station}">
                                <input type="hidden" name="arrival_station" value="${arrival_station}">
                                <input type="hidden" name="departure_date" value="${departure_date}">
                                <input type="submit" class="btn btn-info" name="oder"
                                       value="<fmt:message key="order.make.order"/>">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
<form action="home" method="GET">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>
