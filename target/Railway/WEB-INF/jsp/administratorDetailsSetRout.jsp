<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.details.rout"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
          rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Libraries Stylesheet -->
    <link href="../../lib/animate/animate.min.css" rel="stylesheet" />
    <link href="../../lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet" />
    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet" />
    <!-- Template Stylesheet -->
    <link href="../../css/style.css" rel="stylesheet" />
</head>
<body>
<mrt:navigation/>
<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 10%"><fmt:message key="station.name"/></th>
                <th style="width: 20%"><fmt:message key="arrivalDate"/></th>
                <th style="width: 20%"><fmt:message key="dispatchDate"/></th>
                <th style="width: 20%"><fmt:message key="edit"/></th>
                <th style="width: 10%"><fmt:message key="delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rout_m_list}" var="item">
                <tr>
                    <td>${item.order}</td>
                    <td>${item.station}</td>
                    <td>${item.stationArrivalDate}</td>
                    <td>${item.stationDispatchData}</td>
                    <td>
                        <form action="administrator_edit_info_details_set_rout" method="GET">
                            <input type="hidden" name="station_id" value="${item.stationId}">
                            <input type="hidden" name="routs_id" value="${item.routsId}">
                            <input type="submit" class="btn btn-info" name="edit_info_rout_mapping"
                                   value="<fmt:message key="admin.editInformation"/>">
                        </form>
                    </td>
                    <td>
                        <form action="remove_rout_mapping" method="POST">
                            <input type="hidden" name="routs_id" value="${item.routsId}">
                            <input type="hidden" name="station_id" value="${item.stationId}">
                            <input type="submit" class="btn btn-danger" name="remove_rout_to_station_mapping"
                                   value="<fmt:message key="admin.remove"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <form action="administrator_set_rout_mapping" method="GET">
        <input type="hidden" name="routs_id" value="${routs_id}">
        <input type="submit" class="btn btn-success" name="add_rout_mapping"
               value="<fmt:message key="admin.addStationMapping"/>">
    </form>
    <p>
    <form action="administrator_info_route" method="GET">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
    </p>
</div>
</body>
</html>