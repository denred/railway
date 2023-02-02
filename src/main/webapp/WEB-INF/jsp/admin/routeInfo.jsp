<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

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
    <%-- Search --%>
    <div class="d-flex justify-content-center">
        <form class="was-validated" action="controller?action=routes" method="POST">
            <div class="row">
                <div class="col-sm-3">
                    <input class="form-control" name="routeNameFilter" type="text"
                           placeholder="<fmt:message key="filter.route.name"/>" value="${routeNameFilter}"
                           pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']*">
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.station.name"/></div>
                </div>
                <div class="col-sm-3">
                    <input class="form-control" name="routeFilter" type="text"
                           placeholder="<fmt:message key="filter.route"/>" value="${routeFilter}"
                           pattern="[0-9]*">
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.number"/></div>
                </div>
                <div class="col-sm-3">
                    <input class="form-control" name="trainFilter" type="text"
                           placeholder="<fmt:message key="filter.train"/>" value="${trainFilter}"
                           pattern="^\d*\(?\w*[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']*\)?$">
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.train.number"/></div>
                </div>
                <div class="col-sm-3">
                    <input type="hidden" name="routeDto" value="${routeDto}">
                    <input type="submit" class="btn btn-info" name="filter"
                           value="<fmt:message key="route.filter"/>">
                </div>
            </div>
        </form>
    </div>

    <%-- Route info --%>
    <div class="d-flex justify-content-center">
        <table class="table table-hover align-middle" style="width: 85%">
            <thead class="thead-light text-center">
            <tr>
                <th class="align-middle" style="width: 1%"><fmt:message key="order"/></th>
                <th class="text-start align-middle" style="width: 20%"><fmt:message key="rout.name"/></th>
                <th class="align-middle" style="width: 10%"><fmt:message key="rout.number"/></th>
                <th class="align-middle" style="width: 10%"><fmt:message key="train.number"/></th>
                <th class="align-middle" style="width: 25%">
                    <div class="col-12 text-end">
                        <a class="btn bg-gradient-green text-success mb-0" href="controller?action=edit_route"><i
                                class="fas fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;<fmt:message
                                key="admin.addRout"/></a>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="routeDto_list" scope="request" type="java.util.List"/>
            <c:forEach var="routeDto" items="${routeDto_list}" varStatus="i">
                <jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>
                <jsp:useBean id="recordsPerPage" scope="request" type="java.lang.Integer"/>
                <tr>
                    <td class="text-center align-middle">${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td class="align-middle">
                        <a class="text-decoration-none"
                           href="controller?action=route_mapping&routs_id=${routeDto.routsId}">
                                ${routeDto.routName}
                        </a>
                    </td>
                    <td class="text-center align-middle">
                            ${routeDto.routNumber}
                    </td>
                    <td class="text-center align-middle">
                        <div class="row align-items-center">
                            <div class="col-sm-12">
                                    ${routeDto.trainNumber}
                            </div>
                        </div>
                    </td>
                    <td class="text-center align-middle">
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

    <%-- Pagination --%>
    <div class="d-flex justify-content-center">
        <nav aria-label="Page navigation">
            <ul class="pagination ">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=routes&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page">
                                <a class="page-link" href="#">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=routes&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                <c:if test="${last_page gt noOfPages}">
                    <li class="page-item disabled"><a class="page-link circle circle-md"
                                                      href="#"><span>...</span></a></li>
                    <c:choose>
                        <c:when test="${currentPage eq last_page}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="controller?action=routes&page=${last_page}">${last_page}</a>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <c:if test="${currentPage gt noOfPages}">
                                <li class="page-item active">
                                    <a class="page-link"
                                       href="controller?action=routes&page=${currentPage}">${currentPage}</a>
                                </li>
                                <li class="page-item disabled">
                                    <a class="page-link circle circle-md"
                                       href="#"><span>...</span></a></li>
                            </c:if>
                            <li class="page-item">
                                <a class="page-link"
                                   href="controller?action=routes&page=${last_page}">${last_page}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${currentPage lt last_page}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=routes&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>