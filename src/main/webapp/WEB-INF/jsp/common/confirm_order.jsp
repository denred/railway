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
                <div class="col-md-6">
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
                <div class="col-md-6">
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

        <div class="col-md-3 border">
            <c:set var="travel_time">
                <period:period dateFrom="${dispatchDateTime}" dateTo="${arrivalDateTime}"
                               locale="${sessionScope.locale}"/>
            </c:set>
            ${travel_time}
        </div>

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

            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary mt-3" data-toggle="modal" data-target="#paymentModal">
                Make Payment
            </button>

            <!-- Payment Modal -->
            <div class="modal fade" id="paymentModal" tabindex="-1" aria-labelledby="paymentModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="paymentModalLabel">Payment Information</h5>
                            <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body text-start">

                            <form id="confirm-order" action="controller?action=confirm_order" method="POST">
                                <input type="hidden" name="travel_time" value="${travel_time}">
                                <!-- Card Holders Input -->
                                <div class="form-group">
                                    <label for="cc_name">Card Holder's Name</label>
                                    <input name="cardHoldersName" type="text" class="form-control" id="cc_name" pattern="\w+ \w+.*"
                                           title="First and last name" required>
                                    <div id="cc_name_error"></div>
                                </div>
                                <!-- Card Number Input -->
                                <div class="form-group row">
                                    <label class="col-md-12">Card Number</label>
                                    <div class="col-md-3">
                                        <input name="cardNumber" type="tel" class="form-control card-number" autocomplete="off" maxlength="4"
                                               pattern="\d{4}" title="First four digits" required>
                                    </div>
                                    <div class="col-md-3">
                                        <input name="cardNumber" type="tel" class="form-control card-number" autocomplete="off" maxlength="4"
                                               pattern="\d{4}" title="Second four digits" required>
                                    </div>
                                    <div class="col-md-3">
                                        <input name="cardNumber" type="tel" class="form-control card-number" autocomplete="off" maxlength="4"
                                               pattern="\d{4}" title="Third four digits" required>
                                    </div>
                                    <div class="col-md-3">
                                        <input name="cardNumber" type="tel" class="form-control card-number" autocomplete="off" maxlength="4"
                                               pattern="\d{4}" title="Fourth four digits" required>
                                    </div>
                                    <div id="cc_number_error"></div>
                                </div>

                                <!-- Expiration Date Input -->
                                <div class="form-group row">
                                    <label class="col-md-12">Card Expiry Date</label>
                                    <div class="col-md-9">
                                        <label for="expiry-month"></label>
                                        <select class="form-control" name="expiryMonth" id="expiry-month">
                                            <option value="01">January</option>
                                            <option value="02">February</option>
                                            <option value="03">March</option>
                                            <option value="04">April</option>
                                            <option value="05">May</option>
                                            <option value="06">June</option>
                                            <option value="07">July</option>
                                            <option value="08">August</option>
                                            <option value="09">September</option>
                                            <option value="10">October</option>
                                            <option value="11">November</option>
                                            <option value="12">December</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="expiry-year"></label>
                                        <select class="form-control" name="expiryYear" id="expiry-year">
                                            <option>2016</option>
                                            <option>2017</option>
                                            <option>2018</option>
                                            <option>2019</option>
                                            <option>2020</option>
                                            <option>2021</option>
                                            <option>2022</option>
                                            <option>2023</option>
                                        </select>
                                    </div>
                                </div>
                                <!-- Security Code Input -->
                                <div class="form-group row">
                                    <label class="col-md-12">Card CVV</label>
                                    <div class="col-md-3">
                                        <input name="cvvCode" id="cc_cvv" type="text" class="form-control" autocomplete="off" maxlength="3"
                                               pattern="\d{3}" title="Three digits at back of your card"
                                               required>
                                    </div>
                                    <div id="cc_cvv_error"></div>
                                </div>
                                <!-- Write Off Balance Checkbox -->
                                <div class="form-check">
                                    <input name="balancePayment" value="on" class="form-check-input" type="checkbox" id="writeOffBalance">
                                    <label class="form-check-label" for="writeOffBalance">
                                        Payment from balance
                                    </label>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button id="payment-button" type="submit" class="btn btn-primary">Pay</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
    <div>${errorMessage}</div>

    <a href="controller?action=select_seats" class="btn bg-gradient-blue text-primary mb-0">
        <i class="fas fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script src="${pageContext.request.contextPath}/js/payment-validation.js"></script>
</body>
</html>