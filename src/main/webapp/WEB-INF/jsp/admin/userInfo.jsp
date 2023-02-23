<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<fmt:setBundle basename="exceptionMessages" var="excMsg"/>

<html>
<head>
    <title><fmt:message key="admin.user.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<tags:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.user.list"/>
</h3>
<div class="container mt-4">
    <div class="row">
        <form class="was-validated" id="search" action="controller?action=users" method="post">
            <div class="row g-3 align-items-end">
                <div class="col-auto">
                    <label for="email" class="col-form-label"><fmt:message key="user.email"/></label>
                    <input type="text" id="email" name="emailField" value="${emailField}"
                           class="form-control"
                           pattern="^[a-zA-Z0-9._%+-]*@?[a-zA-Z0-9.-]*\.?[a-zA-Z]{1,}$">
                    <div class="invalid-feedback">
                        <fmt:message bundle="${excMsg}" key="validation.user.email"/>
                    </div>
                </div>
                <div class="col-auto">
                    <label for="firstName" class="col-form-label"><fmt:message key="user.first_name"/></label>
                    <input type="text" id="firstName" name="firstNameField" value="${firstNameField}"
                           class="form-control" pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                    <div class="invalid-feedback">
                        <fmt:message bundle="${excMsg}" key="validation.user.name"/>
                    </div>
                </div>
                <div class="col-auto">
                    <label for="lastName" class="col-form-label"><fmt:message key="user.last_name"/></label>
                    <input type="text" id="lastName" name="lastNameField" value="${lastNameField}"
                           class="form-control" pattern="[a-zA-Zа-яА-яёЁ\u0400-\u052F\u2DE0-\u2DFF\uA640-\uA69F']{1,60}">
                    <div class="invalid-feedback">
                        <fmt:message bundle="${excMsg}" key="validation.user.name"/>
                    </div>
                </div>
                <div class="col-auto">
                    <label for="birthDate" class="col-form-label"> <fmt:message key="user.birth_date"/></label>
                    <input type="date" id="birthDate" name="birthDate" value="${birthDate}" class="form-control"
                           pattern="\d{4}-\d{2}-\d{2}">
                    <div class="invalid-feedback"><fmt:message bundle="${excMsg}" key="validation.user.date"/></div>
                </div>
                <div class="col-auto">
                    <label for="phoneNumber" class="col-form-label"><fmt:message key="user.phone"/></label>
                    <input type="tel" id="phoneNumber" name="phoneField" value="${phoneField}"
                           class="form-control" pattern="\+?[3]?[8]?[0]?-?[0-9]{2}-?[0-9]{3}-?[0-9]{2}-?[0-9]{2}">
                    <div class="invalid-feedback">
                        <fmt:message bundle="${excMsg}" key="validation.user.phone"/>
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-filter"></i> <fmt:message key="route.filter"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
    <div class="row">${errorMessage}</div>
    <div class="row">
        <table class="table table-bordered table-hover">
            <thead class="text-center">
            <tr class="text-center">
                <th class="align-content-center text-center" style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 15%">
                    <fmt:message key="user.email"/>
                </th>
                <th style="width: 13%">
                    <fmt:message key="user.first_name"/>
                </th>
                <th style="width: 13%">
                    <fmt:message key="user.last_name"/>
                </th>
                <th style="width: 20%">
                    <fmt:message key="user.birth_date"/>
                </th>
                <th style="width: 15%">
                    <fmt:message key="user.phone"/>
                </th>
                <th class="text-center" style="width: 15%; align-self: center;"><span><fmt:message
                        key="admin.blockStatus"/></span></th>
                <th style="width: 18%"></th>
            </tr>
            </thead>
            <tbody class="text-center" style="align-content: center">
            <c:forEach var="user" items="${user_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.birthDate}</td>
                    <td>${user.phone}</td>
                    <td><fmt:message key="${user.blocked}"/></td>
                    <td>
                        <div class="row">
                            <div class="col-sm-6">

                                <c:choose>
                                    <c:when test="${user.blocked == false}">
                                        <form class="m-0" action="controller?action=block" method="POST">
                                            <input type="hidden" name="user_id" value="${user.userId}">
                                            <input type="hidden" name="block_status" value="true">
                                            <input type="image"
                                                   src="${pageContext.request.contextPath}/img/icons8-denied-50.png"
                                                   alt=""
                                                   style="width: 25px">
                                        </form>
                                    </c:when>
                                    <c:when test="${user.blocked == true}">
                                        <form class="m-0" action="controller?action=block" method="POST">
                                            <input type="hidden" name="user_id" value="${user.userId}">
                                            <input type="hidden" name="block_status" value="false">
                                            <input type="image"
                                                   src="${pageContext.request.contextPath}/img/icons8-user-50.png"
                                                   alt=""
                                                   style="width: 25px">
                                        </form>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="col-sm-6">
                                <a href="#"><img src="${pageContext.request.contextPath}/img/icons8-delete-user-male-50.png" alt=""
                                                 style="width: 25px"></a>
                            </div>

                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination currentPage="${currentPage}" lastPage="${last_page}" numPages="${noOfPages}"
                     url="controller?action=users"/>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>