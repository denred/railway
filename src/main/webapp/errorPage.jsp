<%@ page import="ua.nure.huzhyn.exception.ServiceException" %>
<%@ page isErrorPage='true' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
        }

        .ccc {
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <img src="https://upload.wikimedia.org/wikipedia/commons/f/fc/Circle-icons-train.svg" width="35" height="35"
             class="d-inline-block align-top" alt="">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
</nav>
<div style="text-align: center;">
    <h1 class="h1 text-center ccc" style="height:100px; width: 100%">
        HTTP STATUS CODE: <%out.print(((ServiceException) exception).getHttpStatusCode());%>
    </h1>
</div>
<br/>
<br/>
<br/>
<div style="text-align: center;">
    <h3 class="h3 text-center ccc" style="height:100px; width: 100%">
        <%out.print(exception.getMessage());%>
    </h3>
</div>
</body>
</html>


