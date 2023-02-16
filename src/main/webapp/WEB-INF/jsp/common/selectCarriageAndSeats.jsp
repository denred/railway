<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carriage.css">
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container">
    <h3 class="text-center my-2"><fmt:message key="seat.selection"/></h3>
    <form action="controller?action=select_seats" method="POST">
        <div class="row">
            <div class="col-md-auto"><fmt:message key="carriage.carriages"/></div>
            <c:forEach var="carriage" items="${car_list}">
                <div class="col-md-auto">
                    <div class="form-check">
                        <input name="car_id" value="${carriage.carId}" class="form-check-input" type="radio"
                               id="checkFreeSeats" onclick="this.form.submit()"
                               <c:if test="${notedCarriage eq carriage.carId}">checked="checked"</c:if> />
                        <label class="form-check-label" for="checkFreeSeats">
                            <span>${carriage.carNumber}</span><span class="fas fa-train ml-1">(${carriage.seats})</span>
                        </label>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="row mt-2">
            <div class="col-12"><fmt:message key="car.type"/>: <fmt:message key="${car_type}"/></div>
        </div>
    </form>
    <form id="seatsId" action="controller?action=create_order" method="POST">
        <%-- Carriage choose seat --%>
        <c:if test="${fn:length(seat_list) gt 0}">
            <div class="carriage row">
                <c:forEach begin="1" end="36" varStatus="loop">
                    <c:set var="state" value="true"/>
                    <c:forEach var="seat" items="${seat_list}">
                        <c:if test="${seat.seatNumber eq loop.index}">
                            <c:set var="state" value="false"/>
                            <div class="col-auto mx-1">
                                <div aria-details="${seat.id}"
                                     class="seat seat-${loop.index} seat-free">${loop.index}</div>
                            </div>
                        </c:if>
                    </c:forEach>
                    <c:if test="${state}">
                        <div class="col-auto mx-1">
                            <div class="seat seat-busy seat-${loop.index}">${loop.index}</div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-6 text-start">
                <a href="controller?action=search_routes" class="btn bg-gradient-blue text-primary mb-0">
                    <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
                    <fmt:message key="back"/></a>
            </div>
            <div class="col-md-6 text-end">
                <button disabled id="button_next" type="submit" class="btn bg-gradient-green text-primary mb-0">
                    <fmt:message key="next"/> <i class="fas fas fa-arrow-alt-circle-right" aria-hidden="true"></i>
                </button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chooseSeat.js"></script>
</body>
</html>