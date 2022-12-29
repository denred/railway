<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
        }

        table {
            table-layout: fixed;
            width: auto;
            height: auto;
            text-align: center;
        }

        tr {
            width: auto;
            height: auto;
            text-align: center;

        }

        td {
            width: auto;
            text-align: center;

        }
    </style>
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
<div class="h5" align="right">
    <fmt:message key="enterRole"></fmt:message>
    <mrt:role role="${user.role}"></mrt:role>
</div>
<form action="select_cars_and_seats_for_order" method="GET">
    <table class="table table-bordered table-hover text-center" border="1" style="width: auto">
        <thead class="thead-light text-center">
        <tr>
            <th><fmt:message key="station.dispatch"/></th>
            <th><fmt:message key="station.arrival"/></th>
            <th><fmt:message key="car.type"/></th>
            <th><fmt:message key="order.make.order"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><select class="btn btn-info dropdown-toggle" name="departure_station_id">
                <c:set var="departure_station" value="${departure_station}"/>
                <c:forEach items="${station_list}" var="stationList">
                    <option
                            <c:choose>
                                <c:when test="${departure_station eq stationList.station}">
                                    selected
                                </c:when>
                            </c:choose>
                            value="${stationList.stationId}"><c:out value="${stationList.station}"/>
                    </option>
                </c:forEach>
            </select>
            </td>
            <td><select class="btn btn-info dropdown-toggle" name="arrival_station_id">
                <c:set var="arrival_station" value="${arrival_station}"/>
                <c:forEach items="${station_list}" var="stationList">
                    <option
                            <c:choose>
                                <c:when test="${arrival_station eq stationList.station}">
                                    selected
                                </c:when>
                            </c:choose>
                            value="${stationList.stationId}"><c:out value="${stationList.station}"/>
                    </option>
                </c:forEach>
            </select></td>
            <td><select class="btn btn-info dropdown-toggle" name="car_type">
                <c:forEach items="${carTypeList}" var="car_type">
                    <option value="${car_type}"><fmt:message key="${car_type}"/></option>
                </c:forEach>
            </select></td>
            <td>
                <input type="hidden" name="station1" value="${station1}">
                <input type="hidden" name="travel_time" value="${travel_time}">
                <input type="hidden" name="station2" value="${station2}">
                <input type="hidden" name="routs_id" value="${routs_id}">
                <input type="hidden" name="user_id" value="${user_id}">
                <input type="hidden" name="departure_station" value="${departure_station}">
                <input type="hidden" name="arrival_station" value="${arrival_station}">
                <input type="hidden" name="departure_date" value="${departure_date}">
                <input type="hidden" name="train_id" value="${train_id}">
                <input type="submit" class="btn btn-success" name="add_order"
                       value="<fmt:message key="next"/>">
            </td>
        </tr>
    </table>
</form>
<form action="search_rout_for_order" method="GET">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="departure_station" value="${departure_station}">
    <input type="hidden" name="arrival_station" value="${arrival_station}">
    <input type="hidden" name="departure_date" value="${departure_date}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>
