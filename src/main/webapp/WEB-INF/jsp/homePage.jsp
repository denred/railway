<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="app.description"/></title>
    <link rel="icon" type="image/x-icon" href="img/icons8-high-speed-train-32.png">
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


<div class="container-fluid mx-5 mt-5">
    <form action="search_rout_for_order" method="GET">
        <div class="input-group w-25">
            <span class="input-group-text"><fmt:message key="rout.from"/></span>
            <input name="departure_station" type="text" class="form-control" id="exampleInputRoutFrom">
        </div>
        <div class="input-group my-3 w-25">
            <span class="input-group-text"><fmt:message key="rout.to"/></span>
            <input name="arrival_station" type="text" class="form-control" id="exampleInputRoutTo">
        </div>
        <div class="input-group w-25">
            <span class="input-group-text"><fmt:message key="date"/></span>
            <input name="departure_date" type="datetime-local" class="form-control" id="exampleInputRoutWhen">
        </div>
        <div>
            <input type="hidden" name="user_id" value="${user.userId}"/>
            <input class="btn btn-primary btn-block text-down mt-3" type="submit" name="route_search"
                   value="<fmt:message key="rout.search"/>">
        </div>
    </form>
</div>
</body>
</html>
