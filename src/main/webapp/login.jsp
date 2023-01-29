<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

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
                <option value="ua" ${sessionScope.locale == 'ua' ? 'selected' : ''}><fmt:message key="lang.ua"/></option>
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

            <form action="controller?action=login" method="POST">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="user.signin"/></h1>
                <!-- Email input -->
                <div class="form-outline mb-4">
                    <input name="login" type="email" id="form3Example3" class="form-control form-control-lg"
                           placeholder="<fmt:message key="user.email"/>" required autofocus>
                </div>

                <!-- Password input -->
                <div class="form-outline mb-3">
                    <input name="password" type="password" id="form3Example4" class="form-control form-control-lg"
                           placeholder="<fmt:message key="user.password"/>"
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="<fmt:message key="password.check.message"/>"
                           required>
                </div>

                <div class="d-flex justify-content-between align-items-center">
                    <a href="controller?action=sendForgetPasswordData">
                        <fmt:message key="user.forgotPassword"/>
                    </a>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <input type="submit" class="btn btn-primary btn-lg"
                           style="padding-left: 2.5rem; padding-right: 2.5rem;"
                           value="<fmt:message key="signIn"/>">
                    <p class="small fw-bold mt-2 pt-1 mb-0"><fmt:message key="user.account_question"/> <a
                            href="controller?action=register"
                            class="link-danger"><fmt:message key="registration"/></a></p>
                </div>

            </form>
        </div>
    </div>
</div>

<footer class="mt-auto">
    <div class="mt-5 p-3 bg-primary text-white text-center">
        <p><fmt:message key="footer.text"/></p>
    </div>
</footer>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>