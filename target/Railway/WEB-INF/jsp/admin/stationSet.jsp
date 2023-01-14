<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.station"/></title>
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
                <th><fmt:message key="station.name"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form action="controller?action=set_station" method="POST">
                    <input type="hidden" name="station_id" value="${station_id}">
                    <td><input name="station" class="form-control" value="${station}"></td>
                    <td>
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="controller?action=stations" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
