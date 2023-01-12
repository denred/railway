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
        <div class="d-flex justify-content-center">
            <table class="table table-bordered table-hover caption-top" style="width: 1000px;">
                <thead class="thead-light text-center">
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
                            <form action="controller?action=route" method="POST">
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
                                <span>${entry.value}</span><br>
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
