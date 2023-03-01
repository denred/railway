<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.route"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=edit_route" method="POST">
            <input type="hidden" name="routs_id" value="${current_rout.routsId}">
            <div class="row">
                <div class="col-sm-5">
                    <label for="routeName"><fmt:message key="rout.name"/></label>
                    <input id="routeName" class="form-control" name="rout_name"
                           value="${current_rout.routName}">
                </div>
                <div class="col-sm-4">
                    <label for="routeNumber"><fmt:message key="rout.number"/></label>
                    <input id="routeNumber" class="form-control" name="rout_number"
                           value="${current_rout.routNumber}">
                </div>
                <div class="col-sm-3">
                    <label for="trainNumber"><fmt:message key="train.number"/></label>
                    <select id="trainNumber" class="btn btn-info dropdown-toggle" name="train_number">
                        <c:set var="train_id" value="${current_rout.trainId}"/>
                        <c:forEach items="${trainList}" var="train">
                            <option
                                    <c:choose>
                                        <c:when test="${train.id eq train_id}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${train.id}"><c:out value="${train.number}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-lg-3">
                    <a href="controller?action=routes" class="btn bg-gradient-blue text-primary mb-0">
                        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                        <fmt:message key="back"/></a>
                </div>
                <div class="col-lg-3">
                    <input type="hidden" name="routs_id" value="${current_rout.routsId}">
                    <button class="btn bg-gradient-blue text-success" type="submit"><i class="far fa-check-circle"
                                                                                       aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
