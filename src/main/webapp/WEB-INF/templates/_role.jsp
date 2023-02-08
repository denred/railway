<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<body>
<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <jsp:useBean id="user" scope="session" type="com.epam.redkin.railway.model.entity.User"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>
</body>
</html>
