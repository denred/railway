<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="signIn"/></title>
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

<body class="d-flex flex-column min-vh-100">
<header>
    <div class="p-2 bg-primary text-white text-center">
        <h1 class="text-white"><fmt:message key="app.name"/></h1>
        <p><fmt:message key="app.description"/></p>
    </div>
    <div class="p-2 btn-group btn-group-sm d-flex justify-content-end" role="group" aria-label="Basic example">
        <form action="change_language" method="POST">
            <input type="submit" class="btn btn-outline-primary" name="lang" value="en">
            <input type="submit" class="btn btn-outline-primary" name="lang" value="ua">
        </form>
    </div>
</header>

<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-85">
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="img/train-main.png" class="img-fluid" alt="Sample image">
        </div>
        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">


            <form action="login" method="POST">
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

<footer class="mt-auto">
    <div class="mt-5 p-3 bg-primary text-white text-center">
        <!-- Copyright -->
        <p><fmt:message key="footer.text"/></p>
    </div>
</footer>

</body>
</html>