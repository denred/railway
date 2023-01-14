<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.order"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 600px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="order.status"/></th>
                <th><fmt:message key="admin.editInformation"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <tr>
                <form action="controller?action=order_status" method="POST">
                    <input type="hidden" name="order_id" value="${current_order.id}">
                    <td><select class="btn btn-info dropdown-toggle" name="order_status">
                        <c:set var="current_order_status" value="${current_order.orderStatus}"/>
                        <c:forEach items="${statusList}" var="status">
                            <option
                                    <c:choose>
                                        <c:when test="${status eq current_order_status}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${status}"><fmt:message key="${status}"/>
                            </option>
                        </c:forEach>
                    </select></td>
                    <td>
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="controller?action=admin_orders" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
</body>
</html>
