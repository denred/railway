<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="registration"/></title>
    <link rel="icon" type="image/x-icon" href="img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
          rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet"/>
    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet"/>
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet"/>
    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet"/>
</head>
<body class="bg-primary">
<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5"><fmt:message key="user.registration.form"/></h3>
                        <form action="registration" method="POST">

                            <div class="row">
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline">
                                        <input name="first_name" type="text" id="firstName"
                                               class="form-control form-control-lg"/>
                                        <label class="form-label" for="firstName"><fmt:message
                                                key="user.first_name"/></label>
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
                        <a href="login.jsp"><fmt:message key="back"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>