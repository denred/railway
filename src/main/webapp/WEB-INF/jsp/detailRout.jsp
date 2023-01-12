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
    <title><fmt:message key="admin.details.rout"/></title>
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

<div class="d-flex justify-content-center">
    <table class="table table-bordered table-hover caption-top" style="width: 800px;">
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
                <td>${item.stationArrivalDate.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
                <td>${item.stationDispatchData.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
                <td><period:period dateFrom="${item.stationArrivalDate}" dateTo="${item.stationDispatchData}"
                                   locale="${lang}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<p>
<form action="controller?action=search_routes" method="POST">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</p>
</body>
</html>