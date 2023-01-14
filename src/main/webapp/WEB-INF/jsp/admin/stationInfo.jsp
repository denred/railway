<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.station.information"/></title>
    <link rel="icon" type="image/x-icon" href="../../../img/icons8-high-speed-train-32.png">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.station.list"/>
</h3>

<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 30%"><fmt:message key="station"/></th>
                <th style="width: 20%"><fmt:message key="edit"/></th>
                <th style="width: 10%"><fmt:message key="delete"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="station" items="${station_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${station.station}</td>
                    <td>
                        <form action="controller?action=set_station" method="POST">
                            <input type="hidden" name="station_id" value="${station.id}">
                            <input type="submit" class="btn btn-info" name="edit_info_station"
                                   value="<fmt:message key="admin.editInformation"/>">
                        </form>
                    </td>
                    <td>
                        <form action="controller?action=delete_station" method="POST">
                            <input type="hidden" name="station_id" value="${station.id}">
                            <input type="submit" class="btn btn-danger" name="remove_station"
                                   value="<fmt:message key="admin.remove"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-center">
        <%--For displaying Previous link except for the 1st page --%>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=stations&page=${currentPage - 1}"
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
                                                     href="controller?action=stations&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=stations&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <form action="controller?action=set_station" method="POST">
        <input type="submit" class="btn btn-success" name="add_station" value="<fmt:message key="admin.addStation"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>