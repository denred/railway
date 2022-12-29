<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.route.mapping"/></title>
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
<form action="administrator_set_rout_mapping" method="POST">
    <table class="table table-bordered text-center" border="1" style="width: auto">
        <thead class="thead-light text-center">
        <tr>
            <th><fmt:message key="order"/></th>
            <th><fmt:message key="station.name"/></th>
            <th><fmt:message key="arrivalDate"/></th>
            <th><fmt:message key="dispatchDate"/></th>
            <th><fmt:message key="admin.editInformation"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <input type="hidden" name="routs_id" value="${routs_id}">
            <td><input class="form-control" name="station_order"></td>
            <td><select class="btn btn-info dropdown-toggle" name="station_station">
                <c:forEach items="${station_list}" var="station">
                    <option value="${station.stationId}"><c:out value="${station.station}"/></option>
                </c:forEach>
            </select></td>
            <td><input class="form-control" name="station_arrival_date" type="datetime-local"></td>
            <td><input class="form-control" name="station_dispatch_data" type="datetime-local"></td>
            <td>
                <form action="administrator_set_rout_mapping" method="GET">
                    <input type="hidden" name="routs_id" value="${routs_id}">
                    <input type="submit" class="btn btn-success" name="add_rout_mapping"
                           value="<fmt:message key="admin.addStationMapping"/>">
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</form>
<p>
<form action="administrator_details_set_rout" method="GET">
    <input type="hidden" name="routs_id" value="${routs_id}">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</p>
</body>
</html>