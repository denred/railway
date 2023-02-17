<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.car.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.carriage.list"/>
</h3>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=carriages" method="POST">
            <div class="row">
                <div class="col-4">
                    <label>
                        <input class="form-control" name="trainFilter" type="text"
                               placeholder="<fmt:message key="filter.train"/>" value="${trainFilter}">
                    </label>
                </div>
                <div class="col-4">
                    <label>
                        <select class="btn btn-info dropdown-toggle" name="carriageTypeFilter">
                            <option value=""><fmt:message key="filter.all.carriages"/></option>
                            <c:forEach items="${carTypeList}" var="car_type">
                                <option
                                        <c:choose>
                                            <c:when test="${(car_type eq current_car.type) or (car_type eq carriageTypeFilter)}">
                                                selected
                                            </c:when>
                                        </c:choose>
                                        value="${car_type}">
                                    <fmt:message key="${car_type}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="col-2">
                    <input type="hidden" name="routeDto" value="${car_list}">
                    <input type="submit" class="btn btn-info" name="filter"
                           value="<fmt:message key="route.filter"/>">
                </div>
            </div>
        </form>
    </div>
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 15%"><fmt:message key="train.number"/></th>
                <th style="width: 15%"><fmt:message key="car.type"/></th>
                <th style="width: 10%"><fmt:message key="car.number"/></th>
                <th style="width: 10%"><fmt:message key="car.seats"/></th>
                <th style="width: 10%"><fmt:message key="car.price"/></th>
                <th class="align-middle" style="width: 30%">
                    <div class="col-12 text-end">
                        <a class="btn bg-gradient-green text-success mb-0" href="controller?action=add_carriage"><i
                                class="fas fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;<fmt:message key="admin.addCar"/></a>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="carriage" items="${car_list}" varStatus="i">
                <tr>
                    <td class="align-middle">${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td class="align-middle">${carriage.trainNumber}</td>
                    <td class="align-middle"><fmt:message key="${carriage.carriageType}"/></td>
                    <td class="align-middle">${carriage.carNumber}</td>
                    <td class="align-middle">${carriage.seats}</td>
                    <td class="align-middle">${carriage.price}</td>
                    <td class="text-center align-middle">
                        <div class="row align-items-center">
                            <div class="col-sm-6">
                                <a class="btn btn-link text-dark px-3 mb-0"
                                   href="controller?action=set_carriage&car_id=${carriage.carId}">
                                    <i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i><fmt:message
                                        key="admin.editInformation"/></a>
                            </div>
                            <div class="col-sm-6">
                                <a class="btn btn-link text-danger text-gradient px-3 mb-0"
                                   href="controller?action=remove_carriage&car_id=${carriage.carId}"><i
                                        class="far fa-trash-alt me-2" aria-hidden="true"></i><fmt:message
                                        key="admin.remove"/></a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-center">
        <%--For displaying Previous link except for the 1st page --%>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=carriages&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>
                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=carriages&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=carriages&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>