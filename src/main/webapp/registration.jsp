<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<html>
<head>
    <title><fmt:message key="registration"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body class="bg-light">
<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5"><fmt:message key="user.registration.form"/></h3>
                        <form class="was-validated" action="controller?action=register" method="POST"
                              oninput='upc.setCustomValidity(upc.value !== up.value ?
                                      "<fmt:message bundle="${excMsg}" key="validator.user.password.confirm"/>" : "")'>

                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="firstName"><fmt:message
                                                key="user.first_name"/></label>
                                        <input name="first_name" type="text" id="firstName"
                                               class="form-control form-control-lg" required
                                               pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                   key="validation.user.name"/></div>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline">
                                        <label class="form-label" for="lastName"><fmt:message
                                                key="user.last_name"/></label>
                                        <input name="last_name" type="text" id="lastName"
                                               class="form-control form-control-lg" required
                                               pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                   key="validation.user.name"/></div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline datepicker w-100">
                                        <label for="birthdayDate" class="form-label"><fmt:message
                                                key="user.birth_date"/></label>
                                        <input name="birth_date" type="date" class="form-control form-control-lg"
                                               id="birthdayDate" required>
                                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                   key="validation.user.empty"/></div>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="phoneNumber"><fmt:message
                                                key="user.phone"/></label>
                                        <input name="phone" type="tel" id="phoneNumber"
                                               class="form-control form-control-lg" required
                                               pattern="\+[3]{1}[8]{1}[0]{1}[0-9]{9}">
                                        <div class="invalid-feedback">
                                            <fmt:message bundle="${excMsg}" key="validation.user.phone"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">
                                    <label class="form-label" for="emailAddress"><fmt:message
                                            key="user.email"/></label>
                                    <div class="form-outline">
                                        <input name="email" type="email" id="emailAddress"
                                               class="form-control form-control-lg" aria-describedby="emailHelp"
                                               required
                                               pattern="[a-zA-Z0-9._-][a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}">
                                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                   key="validation.user.email"/></div>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-4 pb-2"></div>

                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline datepicker w-100">
                                        <label class="form-label" for="up">
                                            <fmt:message key="user.password"/></label>
                                        <input name="password" type="password" class="form-control form-control-lg"
                                               id="up" required
                                               pattern="^(?:(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).*)[^\s]{8,}$">
                                        <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                   key="validation.user.password"/></div>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline datepicker w-100">
                                        <div class="form-group">
                                            <label class="form-label" for="upc"><fmt:message
                                                    key="user.confirm.password"/></label>
                                            <input name="password_confirm" class="form-control form-control-lg"
                                                   type="password" id="upc">
                                            <div class="invalid-feedback"><fmt:message bundle="${excMsg}"
                                                                                       key="validator.user.password.confirm"/></div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <div class="mt-4 pt-2">
                                <input class="btn btn-primary btn-lg" type="submit" name="registration"
                                       value="<fmt:message key="register"/>"/>
                            </div>
                        </form>
                        <a href="controller?action=login"><fmt:message key="back"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>