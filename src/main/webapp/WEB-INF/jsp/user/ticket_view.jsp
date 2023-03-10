<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="qr" uri="/WEB-INF/tags/qrcode.tld" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.order.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>

<div class="container">
    <div class="text-end">
        <i class="bi bi-printer"></i>
        <i class="bi bi-envelope"></i>
    </div>
    <div class="row">
        <div class="col-md-12 text-center">
            <h4>${order.trainNumber}
                - ${order.dispatchDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</h4>
            <h5>${order.dispatchStation} - ${order.arrivalStation}</h5>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <p><fmt:message key="passenger.name"/> : ${order.user.firstName} ${order.user.lastName}</p>
            <p><fmt:message key="order.seats.number"/> : ${order.seatNumber}</p>
        </div>

        <div class="col-md-6 text-right">
            <qr:generateQRCode data="${order.uuid}" width="${150}" height="${150}" />
            <img src="${qrcodeFilePath}" alt="${order.uuid}">
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-md-6">
            <p><fmt:message key="car.type"/> : <fmt:message key="${order.carriageType}"/></p>
            <p><fmt:message key="order.fare"/>: ${order.price}</p>
            <p><fmt:message key="order.id"/>: ${order.uuid}</p>
        </div>

        <div class="col-md-6 text-right">
            <p><strong><fmt:message key="order.instruction"/>:</strong></p>
            <ul>
                <li><fmt:message key="order.validation.rule"/></li>
                <li><fmt:message key="order.boarding.rule"/></li>
                <li><fmt:message key="order.security.rule"/></li>
            </ul>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-md-12 text-center">
            <p><fmt:message key="powered.by"/> <fmt:message key="app.name"/>
            </p>
        </div>
    </div>
    <a href="controller?action=orders" class="btn bg-gradient-blue text-primary mb-0">
        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
        <fmt:message key="back"/></a>
</div>


</body>
</html>