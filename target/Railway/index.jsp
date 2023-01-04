<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="app.name"/></title>
    <link rel="icon" type="image/x-icon" href="img/icons8-high-speed-train-32.png">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom styles for this template -->
    <link href="css/headers.css" rel="stylesheet">
</head>

<body>

<div class="p-2 bg-primary text-white text-center">
    <h1><fmt:message key="app.name"/></h1>
    <p><fmt:message key="app.description"/></p>
</div>

<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <form action="change_language" method="POST">
            <input type="submit" class="btn btn-outline-primary" name="lang" value="en">
            <input type="submit" class="btn btn-outline-primary" name="lang" value="ua">
        </form>

        <div class="col-md-3 text-end">
            <button type="button" class="btn btn-outline-primary me-2"><fmt:message key="login"/></button>
            <button type="button" class="btn btn-primary"><fmt:message key="signUp"/></button>
        </div>
    </header>
</div>

<div class="container mt-5">
    <form action="search_rout_for_order" method="GET">
        <div class="mb-3 mt-3">
            <label class="form-label" for="exampleInputRoutFrom"> <fmt:message key="rout.from"/></label>
            <input name="departure_station" type="text" class="form-control" id="exampleInputRoutFrom">
        </div>
        <div class="mb-3">
            <label class="form-label" for="exampleInputRoutTo"><fmt:message key="rout.to"/></label>
            <input name="arrival_station" type="text" class="form-control" id="exampleInputRoutTo">
        </div>
        <div class="mb-3">
            <label class="form-label" for="exampleInputRoutWhen"><fmt:message key="rout.when"/></label>
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


