<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${applicationScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<!doctype html>
<html>
<jsp:include page="/WEB-INF/templates/_head.jsp"/>
<head>
    <title><fmt:message key="signIn"/></title>
</head>

<body class="d-flex flex-column min-vh-100">
<header>
    <div class="p-2 bg-primary text-white text-center">
        <h1 class="text-white"><fmt:message key="app.name"/></h1>
        <p><fmt:message key="app.description"/></p>
    </div>
    <div class="d-flex justify-content-end m-5">
        <%-- Change language --%>
        <form class="d-flex mx-2" method="POST" action="controller?action=i18n">
            <select class="form-select" id="locale" name="lang" onchange="submit()" aria-label="Change language">
                <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>eng</option>
                <option value="ua" ${sessionScope.locale == 'ua' ? 'selected' : ''}><fmt:message
                        key="lang.ua"/></option>
            </select>
        </form>
    </div>
</header>

<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-85">
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="img/train-main.png" class="img-fluid" alt="Sample image">
        </div>
        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">

            <form id="login-form" class="was-validated" action="controller?action=login" method="POST">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="user.signin"/></h1>
                <!-- Email input -->
                <div class="form-outline mb-4">
                    <label for="email-field"></label>
                    <input id="email-field" name="login" type="email" class="form-control form-control-lg"
                           placeholder="<fmt:message key="user.email"/>" required
                           pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{1,}$">
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.email"/></div>
                </div>

                <!-- Password input -->
                <div class="form-outline mb-3">
                    <input name="password" type="password" id="password" class="form-control form-control-lg"
                           placeholder="<fmt:message key="user.password"/>"
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="<fmt:message key="password.check.message"/>" required>
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.password"/></div>
                </div>
                <div class="text-danger">${errorMessage}</div>
            </form>

            <!-- Popup Form Button -->
            <button type="button" class="btn btn-link" data-toggle="modal" data-target="#emailModal">
                <fmt:message key="user.forgotPassword"/>
            </button>

            <!-- Popup Form -->
            <div class="modal fade" id="emailModal" tabindex="-1" role="dialog" aria-labelledby="emailModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="emailModalLabel">Enter Your Email</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form id="emailForm" action="controller?action=sendForgetPasswordData"
                              class="modal-body was-validated" method="POST">

                            <div class="form-group mx-3">
                                <label for="email" class="col-form-label"><fmt:message key="user.email"/>:</label>
                                <input type="email" class="form-control" id="email" name="email" required
                                       pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{1,}$">

                                <div class="invalid-feedback">
                                    <fmt:message bundle="${excMsg}" key="validation.user.email"/>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                        key="decline"/></button>
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message key="submit"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <input type="submit" class="btn btn-primary btn-lg"
                       style="padding-left: 2.5rem; padding-right: 2.5rem;"
                       value="<fmt:message key="signIn"/>" form="login-form">
                <p class="small fw-bold mt-2 pt-1 mb-0"><fmt:message key="user.account_question"/>
                    <a href="controller?action=register"
                       class="link-danger"><fmt:message key="registration"/></a></p>
            </div>


        </div>
    </div>
</div>

<footer class="mt-auto">
    <div class="mt-5 p-3 bg-primary text-white text-center">
        <p><fmt:message key="footer.text"/></p>
    </div>
</footer>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
</body>
</html>