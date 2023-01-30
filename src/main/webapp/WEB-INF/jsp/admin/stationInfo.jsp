<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<html>
<head>
    <title><fmt:message key="admin.station.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>


<div class="container mt-4">
    <div class="align-content-center">
        <div class="d-flex justify-content-center">
            <form class="was-validated" action="controller?action=stations" method="POST" novalidate>
                <div class="row">
                    <div class="col-sm-8">
                        <input class="form-control" name="stationFilter" type="text"
                               placeholder="<fmt:message key="filter.station"/>" value="${stationFilter}"
                               pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']*">
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.station.name"/></div>
                    </div>
                    <div class="col-sm-4">
                        <input type="submit" class="btn btn-info" name="filter"
                               value="<fmt:message key="route.filter"/>">
                    </div>
                </div>
            </form>
        </div>
        <div class="d-flex justify-content-center">
            <table class="table table-bordered table-hover caption-top" style="width: 58%;">
                <thead class="thead-light text-center">
                <tr>
                    <th>
                        <div class="row">
                            <div class="col-6 d-flex align-items-center">
                                <h5 class="mb-0"><fmt:message key="admin.station.list"/></h5>
                            </div>
                            <div class="col-6 text-end">
                                <a class="btn bg-gradient-green text-success mb-0" href="controller?action=set_station"><i
                                        class="fas fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;<fmt:message
                                        key="admin.addStation"/></a>
                            </div>
                        </div>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="station" items="${station_list}" varStatus="i">
                    <tr>
                        <td>
                            <div class="row align-items-center">
                                <div class="col-sm-1">
                                        ${i.index + recordsPerPage * (currentPage - 1) + 1}
                                </div>
                                <div class="col-sm-5">
                                        ${station.station}
                                </div>

                                <div class="col-sm-3">
                                    <a class="btn btn-link text-dark px-3 mb-0"
                                       href="controller?action=set_station&station_id=${station.id}"><i
                                            class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i><fmt:message
                                            key="admin.editInformation"/></a>
                                </div>
                                <div class="col-sm-3">
                                    <a class="btn btn-link text-danger text-gradient px-3 mb-0"
                                       href="controller?action=delete_station&station_id=${station.id}"><i
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
                            <a class="page-link" href="controller?action=stations&page=${currentPage - 1}"
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
                                                         href="controller?action=stations&page=${i}">${i}</a></li>
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
                                       href="controller?action=stations&page=${last_page}">${last_page}</a>
                                </li>
                            </c:when>

                            <c:otherwise>
                                <c:if test="${currentPage gt noOfPages}">
                                    <li class="page-item active">
                                        <a class="page-link"
                                           href="controller?action=stations&page=${currentPage}">${currentPage}</a>
                                    </li>
                                    <li class="page-item disabled">
                                        <a class="page-link circle circle-md"
                                           href="#"><span>...</span></a></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="controller?action=stations&page=${last_page}">${last_page}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>

                    <c:if test="${currentPage lt last_page}">
                        <li class="page-item">
                            <a class="page-link" href="controller?action=stations&page=${currentPage + 1}"
                               aria-label="Next">
                                <span aria-hidden="true">&raquo;</span></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>