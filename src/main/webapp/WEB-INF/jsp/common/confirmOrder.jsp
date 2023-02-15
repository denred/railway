<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="confirmation.of.an.order"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<h3 style="text-align: center;">
    <fmt:message key="user.order.information"/>
</h3>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="rout.name"/>:</div>
        <div class="col-md-3 border">${rout_name}</div>
        <div class="col-md-2">
            <a href="controller?action=home" class="btn bg-gradient-red text-danger mb-0">
                <i class="fas fa-times" aria-hidden="true"></i>
                <fmt:message key="order.cancel"/></a>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.train.number"/>:</div>
        <div class="col-md-3 border">${train_number}</div>
    </div>

    <c:forEach items="${sessionScope.rout_order_dto_list}" var="rout">
        <c:forEach items="${rout.stations}" var="station">
            <c:if test="${station.station eq departure_station}">
                <c:set var="dispatchDateTime"
                       value="${station.stationDispatchDateTime}"/>
            </c:if>
            <c:if test="${station.station eq arrival_station}">
                <c:set var="arrivalDateTime" value="${station.stationArrivalDateTime}"/>
            </c:if>
        </c:forEach>
    </c:forEach>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.dispatch.station.and.dispatch.time"/>:</div>
        <div class="col-md-3 border">
            <div class="row">
                <div class="col-md-3">
                    ${departure_station}
                </div>
                <div class="col-md-6">
                    ${dispatchDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.arrival.station.and.dispatch.time"/>:</div>
        <div class="col-md-3 border">
            <div class="row">
                <div class="col-md-3">
                    ${arrival_station}
                </div>
                <div class="col-md-6">
                    ${arrivalDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.travel.time"/>:</div>
        <div class="col-md-3 border"><period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}"
                                                    locale="${sessionScope.locale}"/></div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.car.type"/>:</div>
        <div class="col-md-3 border"><fmt:message key="${car_type}"/></div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.car.number"/>:</div>
        <div class="col-md-3 border">${car_number}</div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.count.of.seats"/>:</div>
        <div class="col-md-3 border">${count_of_seats}</div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.seats.number"/>:</div>
        <div class="col-md-3 border">
            <c:forEach items="${seat_list}" var="seat" varStatus="i">
                <c:choose>
                    <c:when test="${i.index eq 0}">
                        ${seat.seatNumber}
                    </c:when>
                    <c:otherwise>
                        <span>, ${seat.seatNumber}</span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2 border"><fmt:message key="order.price"/>:</div>
        <div class="col-md-3 border">${price}</div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-2"></div>
        <div class="col-md-3 text-end">
            <form class="mt-2" action="controller?action=confirm_order" method="POST">
                <input type="hidden" name="routs_id" value="${routs_id}">
                <input type="hidden" name="train_id" value="${train_id}">
                <input type="hidden" name="arrival_station_id" value="${arrival_station_id}">
                <input type="hidden" name="departure_station_id" value="${departure_station_id}">
                <input type="hidden" name="car_id" value="${car_id}">
                <input type="hidden" name="car_type" value="${car_type}">
                <input type="hidden" name="count_of_seats" value="${count_of_seats}">
                <input type="hidden" name="seats_id" value="${seats_id}">
                <input type="hidden" name="travel_time" value="${travel_time}">
                <input type="submit" class="btn btn-primary btn-lg" name="add_order"
                       value="<fmt:message key="order.make.order"/>">
            </form>

        </div>
    </div>

    <a href="controller?action=select_seats" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>