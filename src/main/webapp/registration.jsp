<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="registration"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link href="css/registration.css" rel="stylesheet">
</head>
<body class="text-center">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<div class="form-signup">
    <form action="registration" method="POST">
        <div class="form-group">
            <label class="h5" for="exampleInputEmail1"><fmt:message key="user.email"/></label>
            <input name="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputPassword"><fmt:message key="user.password"/></label>
            <input name="password" type="password" class="form-control" id="exampleInputPassword">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputFirstName"><fmt:message key="user.first_name"/></label>
            <input name="first_name" type="text" class="form-control" id="exampleInputFirstName">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputLastName"><fmt:message key="user.last_name"/></label>
            <input name="last_name" type="text" class="form-control" id="exampleInputLastName">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputDate"><fmt:message key="user.birth_date"/></label>
            <input name="birth_date" type="date" class="form-control" id="exampleInputDate">
        </div>
        <div class="form-group">
            <label class="h5" for="exampleInputPhone"><fmt:message key="user.phone"/></label>
            <input name="phone" type="text" class="form-control" id="exampleInputPhone">
        </div>
        <div>
            <input class="btn btn-primary btn-block" type="submit" name="registration" value="<fmt:message
                key="register"/>">
        </div>
    </form>
    <div>
        <form action="login.jsp">
            <input class="btn btn-primary btn-block text-down" type="submit" value="<fmt:message key="back"/>">
        </form>
    </div>
</div>
</body>
</html>