<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.car"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form <c:choose> <c:when
                test="${commandAdd}"> action="controller?action=add_carriage"</c:when>
            <c:otherwise>action="controller?action=set_carriage"</c:otherwise>
        </c:choose> method="POST">
            <input type="hidden" name="car_id" value="${current_car.carriageId}">
            <div class="row">
                <div class="col-sm-3">
                    <label for="trainNumber"><fmt:message key="train.number"/></label>
                    <select id="trainNumber" class="btn btn-info dropdown-toggle" name="train_id">
                        <c:set var="train_id" value="${current_car.trainId}"/>
                        <jsp:useBean id="trainList" scope="session" type="java.util.List"/>
                        <c:forEach items="${trainList}" var="train">
                            <option
                                    <c:choose>
                                        <c:when test="${train.id eq train_id}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${train.id}"><c:out value="${train.number}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-sm-2">
                    <label for="carriage-class"><fmt:message key="car.type"/></label>
                    <select id="carriage-class" class="btn btn-info dropdown-toggle" name="car_type">
                        <c:set var="train_id" value="${current_car.type}"/>
                        <jsp:useBean id="carTypeList" scope="session" type="java.util.List"/>
                        <c:forEach items="${carTypeList}" var="car_type">
                            <option
                                    <c:choose>
                                        <c:when test="${car_type eq current_car.type}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${car_type}"><fmt:message key="${car_type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-sm-2">
                    <label for="carriage-number"><fmt:message key="car.number"/></label>
                    <input id="carriage-number" name="car_number" class="form-control" value="${current_car.number}">
                </div>

                <div class="col-sm-2">
                    <label for="seats-count"><fmt:message key="count.of.seats"/></label>
                    <input id="seats-count" name="count_of_seats" class="form-control" value="${count_of_seats}">
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-lg-2">
                    <a href="controller?action=carriages" class="btn bg-gradient-blue text-primary mb-0">
                        <i class="far fa-arrow-alt-circle-left" aria-hidden="true"></i>
                        <fmt:message key="back"/></a>
                </div>
                <div class="col-lg-3">
                    <button class="btn bg-gradient-blue text-success" type="submit"><i class="far fa-check-circle"
                                                                                       aria-hidden="true"></i>
                        <fmt:message key="admin.saveInformation"/></button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
