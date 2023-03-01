<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.station"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form class="mx-auto w-25 was-validated" action="controller?action=set_station" method="POST">
            <input type="hidden" name="station_id" value="${station_id}">
            <div class="row">
                <div class="col">
                    <label for="station-name"><fmt:message key="station.name"/></label>
                    <input id="station-name" name="station" class="form-control" value="${station}">
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-lg-6">
                    <a href="controller?action=stations" class="btn bg-gradient-blue text-primary mb-0">
                        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                        <fmt:message key="back"/></a>
                </div>
                <div class="col-lg-6">
                    <button class="btn bg-gradient-blue text-success" type="submit">
                        <i class="far fa-check-circle" aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<%-- end --%>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
