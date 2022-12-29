<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="signIn"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">


</head>
<body>
<section class="vh-100">

    <div class="p-2 btn-group btn-group-sm d-flex justify-content-end" role="group" aria-label="Basic example">
        <form action="change_language" method="POST">
            <input type="submit" class="btn btn-outline-warning" name="lang" value="en">
            <input type="submit" class="btn btn-outline-warning" name="lang" value="ru">
        </form>
    </div>

    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-85">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="img/rail.png" class="img-fluid" alt="Sample image" width="512">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">


                <form action="login" method="POST">
                    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="user.signin"/></h1>
                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input name="login" type="email" id="form3Example3" class="form-control form-control-lg"
                               placeholder="<fmt:message key="user.email"/>"/>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-3">
                        <input name="password" type="password" id="form3Example4" class="form-control form-control-lg"
                               placeholder="<fmt:message key="user.password"/>"/>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <a href="#!" class="text-body"><fmt:message key="user.forgotPassword"/></a>
                    </div>

                    <div class="text-center text-lg-start mt-4 pt-2">
                        <input type="submit" class="btn btn-primary btn-lg"
                               style="padding-left: 2.5rem; padding-right: 2.5rem;"
                               value="<fmt:message key="signIn"/>">
                        <p class="small fw-bold mt-2 pt-1 mb-0"><fmt:message key="user.account_question"/> <a
                                href="registration.jsp"
                                class="link-danger"><fmt:message key="registration"/></a></p>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div
            class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary">
        <!-- Copyright -->
        <div class="text-white mb-3 mb-md-0">
            Copyright © 2023. All rights reserved.
        </div>

    </div>
</section>

</body>
</html>