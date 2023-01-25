<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.train"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 400px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="train.number"/></th>
                <th><fmt:message key="admin.editInformation"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form action="controller?action=set_train" method="POST">
                    <input type="hidden" name="train_id" value="${current_train.id}">
                    <td><input name="train_number" class="form-control" value="${current_train.number}"></td>
                    <td>
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
        </table>
    </div>
    <form action="controller?action=trains" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
</body>
</html>
