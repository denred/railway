<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="app.description"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>

<div class="d-flex justify-content-end">
    <div class="h5 mr-auto p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>


<div class="container-fluid mx-5 mt-5">
    <form action="controller?action=search_routes" method="POST">
        <div class="input-group w-25">
            <span class="input-group-text"><fmt:message key="rout.from"/></span>
            <label for="exampleInputRoutFrom"></label>
            <input name="departure_station" type="text" class="form-control" id="exampleInputRoutFrom">
        </div>
        <div class="input-group my-3 w-25">
            <span class="input-group-text"><fmt:message key="rout.to"/></span>
            <label for="exampleInputRoutTo"></label>
            <input name="arrival_station" type="text" class="form-control" id="exampleInputRoutTo">
        </div>
        <div class="input-group w-25">
            <span class="input-group-text"><fmt:message key="date"/></span>
            <label for="exampleInputRoutWhen"></label>
            <input name="departure_date" type="datetime-local" class="form-control" id="exampleInputRoutWhen">
        </div>
        <div>
            <input type="hidden" name="user_id" value="${user.userId}"/>
            <input class="btn btn-primary btn-block text-down mt-3 w-25" type="submit" name="route_search"
                   value="<fmt:message key="rout.search"/>">
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
