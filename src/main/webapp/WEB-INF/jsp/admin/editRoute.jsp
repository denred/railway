<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.route"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="rout.name"/></th>
                <th><fmt:message key="rout.number"/></th>
                <th><fmt:message key="train.number"/></th>
                <th><fmt:message key="admin.editInformation"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form action="controller?action=edit_route" method="POST">
                    <input type="hidden" name="routs_id" value="${current_rout.routsId}">
                    <td><input class="form-control" name="rout_name" value="${current_rout.routName}"></td>
                    <td><input class="form-control" name="rout_number" value="${current_rout.routNumber}"></td>
                    <td><select class="btn btn-info dropdown-toggle" name="train_number">
                        <c:set var="train_id" value="${current_rout.trainId}"/>
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
                    </td>
                    <td>
                        <input type="hidden" name="routs_id" value="${current_rout.routsId}">
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="controller?action=routes" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
