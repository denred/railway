<%@ tag body-content="empty" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<c:choose>
    <c:when test="${\"USER\" == sessionScope.user.role}">
        <%@include file="../views/userNavigationBar.jspf" %>
    </c:when>
    <c:when test="${\"ADMIN\" == sessionScope.user.role}">
        <%@include file="../views/adminNavigationBar.jspf" %>
    </c:when>
</c:choose>
