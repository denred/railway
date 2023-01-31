<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.rout.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.rout.list"/>
</h3>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=routes" method="POST">
            <div class="row">
                <div class="col-5 mr-2">
                    <select class="btn btn-info dropdown-toggle" name="filter" aria-label="Filter">
                        <option value="trainFilter"><fmt:message key="filter.train"/></option>
                        <option value="routeFilter"><fmt:message key="filter.route"/></option>
                        <option value="routeNameFilter"><fmt:message key="filter.route.name"/></option>
                    </select>
                </div>
                <div class="col-4">
                    <input class="form-control" name="filter_area">
                </div>
                <div class="col-2">
                    <input type="hidden" name="routeDto" value="${routeDto}">
                    <input type="submit" class="btn btn-info" name="filter"
                           value="<fmt:message key="route.filter"/>">
                </div>
            </div>
        </form>
    </div>

    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover" style="width: 85%">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 20%"><fmt:message key="rout.name"/></th>
                <th style="width: 10%"><fmt:message key="rout.number"/></th>
                <th style="width: 10%"><fmt:message key="train.number"/></th>
                <th style="width: 20%"></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="routeDto_list" scope="request" type="java.util.List"/>
            <c:forEach var="routeDto" items="${routeDto_list}" varStatus="i">
                <jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>
                <jsp:useBean id="recordsPerPage" scope="request" type="java.lang.Integer"/>
                <tr>
                    <td class="text-center">${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>
                        <div class="row align-items-center">
                            <form action="controller?action=route_mapping" method="POST">
                                <input type="hidden" name="routs_id" value="${routeDto.routsId}">
                                <button type="submit" style="display:none;"></button>
                                <a href="controller?action=route_mapping"
                                   onclick="event.preventDefault();this.closest('form').submit();">${routeDto.routName}</a>
                            </form>
                        </div>
                    </td>
                    <td class="text-center">
                        <div class="align-items-center">

                                ${routeDto.routNumber}

                        </div>
                    </td>
                    <td class="text-center">
                        <div class="row align-items-center">
                            <div class="col-sm-12">
                                    ${routeDto.trainNumber}
                            </div>
                        </div>
                    </td>
                    <td class="text-center">
                        <div class="row align-items-center">
                            <div class="col-sm-6">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                   href="controller?action=edit_route&routs_id=${routeDto.routsId}"><i
                                        class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i><fmt:message
                                        key="admin.editInformation"/></a>
                            </div>
                            <div class="col-sm-6">
                                <a class="btn btn-link text-danger text-gradient px-3 mb-0"
                                   href="controller?action=delete_route&routs_id=${routeDto.routsId}"><i
                                        class="far fa-trash-alt me-2" aria-hidden="true"></i><fmt:message
                                        key="admin.remove"/></a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-center">
        <%--For displaying Previous link except for the 1st page --%>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=routes&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>
                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
                <jsp:useBean id="noOfPages" scope="request" type="java.lang.Integer"/>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=routes&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=routes&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<form action="controller?action=add_route" method="POST">
    <input type="submit" class="btn btn-success ml-2" name="add_route" value="<fmt:message key="admin.addRout"/>">
</form>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>