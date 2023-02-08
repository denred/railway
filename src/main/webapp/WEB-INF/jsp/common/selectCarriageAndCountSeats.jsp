<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="period" uri="/WEB-INF/tags/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="user.makeOrder"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container">
    <form action="controller?action=select_seats" method="POST">
        <div class="d-flex justify-content-center">
            <table class="table table-bordered table-hover caption-top">
                <thead class="thead-light text-center">
                <tr>
                    <th><fmt:message key="car.number"/></th>
                    <th><fmt:message key="count.of.seats"/></th>
                    <th><fmt:message key="order.make.order"/></th>
                </tr>
                </thead>
                <tbody class="text-center">
                <tr>
                    <td>
                        <label for="carriageNumber"></label>
                        <select id="carriageNumber" class="btn btn-info dropdown-toggle" name="car_id">
                            <c:forEach var="carriage" items="${car_list}">
                                <option value="${carriage.carriageId}"><c:out value="${carriage.number}"/></option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input class="form-control" name="count_of_seats"></td>
                    <td>
                        <input type="submit" class="btn btn-success" name="add_order"
                               value="<fmt:message key="next"/>">
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <a href="controller?action=select_station_and_carriage_type" class="btn btn-primary"><fmt:message key="back"/></a>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
