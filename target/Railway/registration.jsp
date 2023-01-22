<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="registration"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body class="bg-primary">
<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5"><fmt:message key="user.registration.form"/></h3>
                        <form action="controller?action=register" method="POST">

                            <div class="row">
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline">
                                        <input name="first_name" type="text" id="firstName"
                                               class="form-control form-control-lg"
                                               pattern="^.{1,25}$"/>
                                        <label class="form-label" for="firstName"><fmt:message
                                                key="user.first_name"/></label>
                                        <c:if test="${not empty user_registration_data}"> value=${user_registration_data.firstName} </c:if>
                                    </div>

                                </div>
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline">
                                        <input name="last_name" type="text" id="lastName"
                                               class="form-control form-control-lg"/>
                                        <label class="form-label" for="lastName"><fmt:message
                                                key="user.last_name"/></label>
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline datepicker w-100">
                                        <input name="birth_date" type="date" class="form-control form-control-lg"
                                               id="birthdayDate"/>
                                        <label for="birthdayDate" class="form-label"><fmt:message
                                                key="user.birth_date"/></label>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <input name="phone" type="tel" id="phoneNumber"
                                               class="form-control form-control-lg" required/>
                                        <label class="form-label" for="phoneNumber"><fmt:message
                                                key="user.phone"/></label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">

                                    <div class="form-outline">
                                        <input name="email" type="email" id="emailAddress"
                                               class="form-control form-control-lg" aria-describedby="emailHelp"
                                               required/>
                                        <label class="form-label" for="emailAddress"><fmt:message
                                                key="user.email"/></label>
                                    </div>

                                </div>
                                <div class="col-md-6 mb-4 pb-2">

                                    <div class="form-outline datepicker w-100">
                                        <input name="password" type="password" class="form-control form-control-lg"
                                               id="userPassword">
                                        <label class="form-label" for="userPassword">
                                            <fmt:message key="user.password"/></label>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>