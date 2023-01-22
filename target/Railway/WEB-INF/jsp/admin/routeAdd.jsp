<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.addRout"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="d-flex justify-content-center">
    <form action="controller?action=add_route" method="POST">
        <table class="table table-bordered table-hover caption-top" style="width: 800px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="rout.name"/></th>
                <th><fmt:message key="rout.number"/></th>
                <th><fmt:message key="train.number"/></th>
                <th><fmt:message key="admin.editInformation"/></th>
            </tr>
            </thead>
            <tbody class="align-middle text-center">
            <tr>
                <td><input class="form-control" name="rout_name"></td>
                <td><input class="form-control" name="rout_number"></td>
                <td><select class="btn btn-info dropdown-toggle" name="train_number">
                    <c:forEach items="${trainList}" var="train">
                        <option value="${train.id}"><c:out value="${train.number}"/></option>
                    </c:forEach>
                </select></td>

                <td>
                    <input type="submit" class="btn btn-success" name="add_rout"
                           value="<fmt:message key="admin.addRout"/>">
                </td>
            </tr>
            </tbody>
        </table>

    </form>
</div>
<form action="controller?action=routes" method="POST">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>

</body>
</html>
