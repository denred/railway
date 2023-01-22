<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.user.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>

<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.user.list"/>
</h3>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 100%;">
            <thead class="text-center">
            <tr class="text-center">

                <form id="search" action="controller?action=users" method="post">
                    <th class="align-content-center text-center" style="width: 1%"><fmt:message key="order"/></th>
                    <th style="width: 15%">
                        <div class="input-group">
                            <div class="form-outline">
                                <input type="search" class="search-focus form-control"
                                       onchange="this.form.submit()"
                                       placeholder="<fmt:message key="user.email"/>"
                                       name="email"
                                       value="${email}">
                            </div>
                        </div>
                    </th>
                    <th style="width: 13%">
                        <div class="input-group">
                            <div class="form-outline">
                                <input type="search" class="search-focus form-control"
                                       onchange="this.form.submit()"
                                       placeholder="<fmt:message key="user.first_name"/>"
                                       name="first_name"
                                       value="${first_name}">
                            </div>
                        </div>
                    </th>
                    <th style="width: 13%">
                        <div class="input-group">
                            <div class="form-outline">
                                <input type="search" class="search-focus form-control"
                                       onchange="this.form.submit()"
                                       placeholder="<fmt:message key="user.last_name"/>"
                                       name="last_name"
                                       value="${last_name}">
                            </div>
                        </div>
                    </th>
                    <th style="width: 20%">
                        <div class="input-group">
                            <div class="form-outline">
                                <input type="search" class="search-focus form-control"
                                       onchange="this.form.submit()"
                                       placeholder="<fmt:message key="user.birth_date"/>"
                                       name="birth_date"
                                       value="${birth_date}">
                            </div>
                        </div>

                    <th style="width: 15%">
                        <div class="input-group">
                            <div class="form-outline">
                                <input type="search" class="search-focus form-control"
                                       onchange="this.form.submit()"
                                       placeholder="<fmt:message key="user.phone"/>"
                                       name="phone"
                                       value="${phone}">
                            </div>
                        </div>
                    </th>
                    <th class="text-center" style="width: 15%; align-self: center;"><span><fmt:message key="admin.blockStatus"/></span></th>
                    <th style="width: 18%"></th>

                </form>
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
                    <td><span>
                        <div class="row">
                            <div class="col-sm-6">

                                <c:choose>
                                    <c:when test="${user.blocked == false}">
                                        <form action="controller?action=block" method="POST">
                                            <input type="hidden" name="user_id" value="${user.userId}">
                                            <input type="hidden" name="block_status" value="true">
                                            <input type="image" src="../../../img/icons8-denied-50.png" alt=""
                                                   style="width: 25px">
                                        </form>
                                    </c:when>
                                    <c:when test="${user.blocked == true}">
                                        <form action="controller?action=block" method="POST">
                                            <input type="hidden" name="user_id" value="${user.userId}">
                                            <input type="hidden" name="block_status" value="false">
                                            <input type="image" src="../../../img/icons8-user-50.png" alt=""
                                                   style="width: 25px">
                                        </form>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="col-sm-6">
                                <a href="#"><img src="../../../img/icons8-delete-user-male-50.png" alt=""
                                                 style="width: 25px"></a>
                            </div>
                        </div>
                    </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="d-flex justify-content-center">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="controller?action=users&page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=users&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=users&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
<script src="../../../js/xxsProtectionScript.js"></script>
</body>
</html>