<%-- Include files --%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%-- Set the page language --%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>
<html>
<head>
    <title><fmt:message key="admin.train.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<tags:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.train.list"/>
</h3>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form class="was-validated" action="controller?action=trains" method="POST">
            <div class="row">
                <div class="col-sm-8">
                    <label for="filter-train-number"><fmt:message key="filter.train"/></label>
                        <input id="filter-train-number" class="form-control" name="trainFilter" value="${trainFilter}"
                               pattern="^[A-Za-z0-9А-Яа-яЁёІіЇїҐґ']+$"
                               data-error="<fmt:message bundle="${excMsg}" key="validation.train.filter.number"/>"
                               oninvalid="this.setCustomValidity(this.getAttribute('data-error'))"
                               oninput="this.setCustomValidity('')">
                        <div class="invalid-feedback d-block">${errorMessage}</div>
                </div>
                <div class="col-sm-4 d-flex justify-content-center align-items-end">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-filter"></i> <fmt:message key="route.filter"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover" style="width: 58%">
            <thead>
            <tr>
                <th>
                    <div class="row">
                        <div class="col-md-2 d-flex justify-content-center align-items-center">
                            <fmt:message key="order"/>
                        </div>
                        <div class="col-md-3 d-flex justify-content-center align-items-center">
                            <fmt:message key="train.number"/>
                        </div>
                        <div class="col-md-7 text-end">
                            <a class="btn bg-gradient-green text-success mb-0" href="controller?action=set_train"><i
                                    class="fas fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;
                                <fmt:message key="admin.addTrain"/>
                            </a>
                        </div>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="train" items="${trainList}" varStatus="i">
                <tr>
                    <td>
                        <div class="row">
                            <div class="col-md-2 d-flex justify-content-center align-items-center">
                                    ${i.index + recordsPerPage * (currentPage - 1) + 1}
                            </div>
                            <div class="col-md-3 d-flex justify-content-center align-items-center">
                                    ${train.number}
                            </div>
                            <div class="col-md-3 d-flex justify-content-center">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                   href="controller?action=set_train&train_id=${train.id}">
                                    <i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i>
                                    <fmt:message key="admin.editInformation"/></a>
                            </div>
                            <div class="col-md-3 d-flex justify-content-center">
                                <a class="btn btn-link text-danger text-gradient px-3 mb-0"
                                   href="controller?action=remove_train&train_id=${train.id}">
                                    <i class="far fa-trash-alt me-2" aria-hidden="true"></i>
                                    <fmt:message key="admin.remove"/></a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}"
                     url="controller?action=trains"/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation-train.js"></script>
</body>
</html>