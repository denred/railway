<%-- Include files --%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%-- Set the page language --%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.train"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form class="mx-auto w-25 was-validated" action="controller?action=set_train" method="POST">
            <input type="hidden" name="train_id" value="${current_train.id}">
            <div class="row">
                <div class="col-sm-12">
                    <label for="train-number"><fmt:message key="train.number"/></label>
                    <input id="train-number" name="train_number" class="form-control" value="${current_train.number}"
                           required minlength="1" pattern="^\d+\(?\w*[\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']*\)?$"
                           data-error="<fmt:message bundle="${excMsg}" key="validation.train.number"/>"
                           oninvalid="this.setCustomValidity(this.getAttribute('data-error'))"
                           oninput="this.setCustomValidity('')">
                    <div class="invalid-feedback d-block"></div>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-lg-6">
                    <a href="controller?action=trains" class="btn bg-gradient-blue text-primary mb-0" aria-label="Back">
                        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                        <fmt:message key="back"/>
                    </a>
                </div>
                <div class="col-lg-6">
                    <button class="btn bg-gradient-blue text-success" type="submit" aria-label="Save">
                        <i class="far fa-check-circle" aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation-train.js"></script>
</body>
</html>
