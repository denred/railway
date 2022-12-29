<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="homepage"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link href="css/home.css" rel="stylesheet">
</head>
<body class="text-center">
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
<div class="h5">
    <fmt:message key="enterRole"></fmt:message>
    <mrt:role role="${user.role}"></mrt:role>
</div>

<div class="form-home form-ere">
    <form action="search_rout_for_order" method="GET">
        <div class="form-group">
            <label class="h5" for="exampleInputRoutFrom"> <fmt:message key="rout.from"/></label>
            <input name="departure_station" type="text" class="form-control" id="exampleInputRoutFrom">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputRoutTo"><fmt:message key="rout.to"/></label>
            <input name="arrival_station" type="text" class="form-control" id="exampleInputRoutTo">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputRoutWhen"><fmt:message key="rout.when"/></label>
            <input name="departure_date" type="datetime-local" class="form-control" id="exampleInputRoutWhen">
        </div>
        <div>
            <input type="hidden" name="user_id" value="${user.userId}">
            <input class="btn btn-primary btn-block text-down" type="submit" name="route_search"
                   value="<fmt:message key="rout.search"/>">
        </div>
    </form>
</div>
</body>
</html>
