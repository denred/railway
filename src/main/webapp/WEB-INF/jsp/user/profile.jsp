<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<html>
<head>
    <title><fmt:message key="app.description"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<mrt:navigation/>

<div class="container mt-5">
    <div class="row">
        <%-- left side --%>
        <div class="col-lg-4 pb-5">
            <div class="author-card p-3">
                <div class="author-card-profile">
                    <div class="author-card-details">
                        <h5 class="author-card-name text-lg">${first_name} ${last_name}</h5>
                        <span class="">${email}</span>
                    </div>
                </div>
            </div>
        </div>

        <%-- right side --%>
        <div class="col-lg-8 pb-5">
            <form class="row was-validated" action="controller?action=profile" method="POST"
                  oninput='upc.setCustomValidity(upc.value != up.value ?
                          "<fmt:message bundle="${excMsg}" key="validator.user.password.confirm"/>" : "")'>
                <%-- First name --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-fn"><fmt:message key="user.first_name"/></label>
                        <input class="form-control" name="first_name" type="text" id="account-fn"
                               value=${first_name} required
                               pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.name"/></div>
                    </div>
                </div>

                <%-- Last name --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-ln"><fmt:message key="user.last_name"/></label>
                        <input class="form-control" name="last_name" type="text" id="account-ln"
                               value=${last_name} required
                               pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.name"/></div>
                    </div>
                </div>

                <%-- Birth Date --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-birth"><fmt:message key="user.birth_date"/></label>
                        <input name="birth_date" class="form-control" type="date" id="account-birth"
                               value=${birth_date} required>
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.empty"/></div>
                    </div>
                </div>

                <%-- Phone number --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-phone"><fmt:message key="user.phone"/></label>
                        <input name="phone" type="tel" id="account-phone" class="form-control"
                               value=${phone} required
                               pattern="\+[3]{1}[8]{1}[0]{1}[0-9]{9}">
                        <div class="invalid-feedback">+380991234567</div>
                    </div>
                </div>

                <%-- New Password --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="validationCustom02" for="account-pass"><fmt:message
                                key="user.new.password"/></label>
                        <input name="password" class="form-control" type="password" id="up"
                               pattern="^(?:(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\s]{8,}$">
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                   key="validation.user.password"/></div>
                    </div>
                </div>

                <%-- Confirm password --%>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="account-confirm-pass"><fmt:message key="user.confirm.password"/></label>
                        <input name="password_confirm" class="form-control" type="password" id="upc">
                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                   key="validator.user.password.confirm"/></div>
                    </div>
                </div>

                <%-- Submit --%>
                <div class="col-12">
                    <hr class="mt-2 mb-3">
                    <div class="d-flex flex-wrap justify-content-end align-items-center">
                        <button class="btn btn-style-1 btn-primary" type="submit" data-toast=""
                                data-toast-position="topRight" data-toast-type="success"
                                data-toast-icon="fe-icon-check-circle" data-toast-title="Success!"
                                data-toast-message="Your profile updated successfuly.">
                            <fmt:message key="user.update.profile"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>