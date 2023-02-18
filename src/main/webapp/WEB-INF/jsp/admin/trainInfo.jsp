<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<html>
<head>
    <title><fmt:message key="admin.train.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.train.list"/>
</h3>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form class="was-validated" action="controller?action=trains" method="POST" novalidate>
            <div class="row">
                <div class="col-sm-8">
                    <label>
                        <input class="form-control" name="trainFilter" type="text"
                               placeholder="<fmt:message key="filter.train"/>" value="${trainFilter}"
                               pattern="^\d+\(?\w*[\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']*\)?$">
                    </label>
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.train.number"/></div>
                </div>
                <div class="col-sm-4">
                    <input type="submit" class="btn btn-info" name="filter"
                           value="<fmt:message key="route.filter"/>">
                </div>
            </div>
        </form>
    </div>
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 10%"><fmt:message key="order"/><fmt:message key="train.number"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="train" items="${trainList}" varStatus="i">
                <tr>
                    <td>
                        <div class="row">
                            <div class="col-md-3 d-flex justify-content-center">
                                    ${i.index + recordsPerPage * (currentPage - 1) + 1}
                            </div>
                            <div class="col-md-3 d-flex justify-content-center">
                                    ${train.number}
                            </div>
                            <div class="col-md-3 d-flex justify-content-center">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                   href="controller?action=set_train&train_id=${train.id}"><i
                                        class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i><fmt:message
                                        key="admin.editInformation"/></a>
                            </div>
                            <div class="col-md-3 d-flex justify-content-center">
                                <a class="btn btn-link text-danger text-gradient px-3 mb-0"
                                   href="controller?action=remove_train&train_id=${train.id}"><i
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
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=trains&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>
                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=trains&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=trains&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <form action="controller?action=set_train" method="POST">
        <input type="submit" class="btn btn-success" name="add_train" value="<fmt:message key="admin.addTrain"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>