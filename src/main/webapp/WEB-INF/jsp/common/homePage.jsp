<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<jsp:useBean id="date" scope="session" type="java.time.LocalDate"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="app.description"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<%-- Content --%>
<div class="container mt-4">
    <div class="d-flex flex-column">
        <div class="p-2 d-flex justify-content-center mb-4">
            <h3><fmt:message key="home.travel.banner"/></h3>
        </div>
        <div class="d-flex justify-content-center">
            <div class="row">
                <div class="col-auto">
                    <button onclick="fillDirections('Київ','Львів')" class="btn btn-info">
                        <fmt:message key="route.kyiv"/>
                        <span class="fas fa-arrows-alt-h"></span>
                        <fmt:message key="route.lviv"/>
                    </button>
                </div>
                <div class="col-auto">
                    <button onclick="fillDirections('Київ','Пшемисль')" class="btn btn-info">
                        <fmt:message key="route.kyiv"/>
                        <span class="fas fa-arrows-alt-h"></span>
                        <fmt:message key="route.pshemisl"/>
                    </button>
                </div>
                <div class="col-auto">
                    <button onclick="fillDirections('Київ','Харків')" class="btn btn-info">
                        <fmt:message key="route.kyiv"/>
                        <span class="fas fa-arrows-alt-h"></span>
                        <fmt:message key="route.kharkiv"/>
                    </button>
                </div>
                <div class="col-auto">
                    <button onclick="fillDirections('Київ','Дніпро')" class="btn btn-info">
                        <fmt:message key="route.kyiv"/>
                        <span class="fas fa-arrows-alt-h"></span>
                        <fmt:message key="route.dnipro"/>
                    </button>
                </div>
                <div class="col-auto">
                    <button onclick="fillDirections('Львів','Пшемисль')" class="btn btn-info">
                        <fmt:message key="route.lviv"/>
                        <span class="fas fa-arrows-alt-h"></span>
                        <fmt:message key="route.pshemisl"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<form action="controller?action=search_routes" method="POST">
    <div class="container">
        <div class="row">
            <div class="col d-flex justify-content-center mt-4">
                <div class="input-group w-25">
                    <span class="input-group-text"><fmt:message key="rout.from"/></span>
                    <label for="from"></label>
                    <input name="departure_station" type="text" class="form-control" id="from" required>
                </div>
                <div class="d-flex align-items-center mx-2">
                    <button type="button" onclick="invertDirections()">
                        <span class="fa-lg fas fa-arrows-alt-h"></span>
                    </button>
                </div>
                <div class="input-group w-25">
                    <span class="input-group-text"><fmt:message key="rout.to"/></span>
                    <label for="to"></label>
                    <input name="arrival_station" type="text" class="form-control" id="to" required>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col d-flex justify-content-center text-danger">${errorMessage}</div>
        </div>
        <div class="row">
            <div class="col d-flex justify-content-center mt-4">
                <div class="input-group w-25 mr-2">
                    <span class="input-group-text"><fmt:message key="date"/></span>
                    <label for="date"></label>
                    <input name="departure_date" type="date" class="form-control" id="date"
                           value="${date}" min="${date}">

                </div>
                <div class="input-group w-25 ml-4">
                    <span class="input-group-text"><fmt:message key="time"/></span>
                    <label for="select"></label>
                        <select id="select" class="form-control" type="time" name="departure_time">
                            <option value="00:00">00:00</option>
                            <option value="01:00">01:00</option>
                            <option value="02:00">02:00</option>
                            <option value="03:00">03:00</option>
                            <option value="04:00">04:00</option>
                            <option value="05:00">05:00</option>
                            <option value="06:00">06:00</option>
                            <option value="07:00">07:00</option>
                            <option value="08:00">08:00</option>
                            <option value="09:00">09:00</option>
                            <option value="10:00">10:00</option>
                            <option value="11:00">11:00</option>
                            <option value="12:00">12:00</option>
                            <option value="13:00">13:00</option>
                            <option value="14:00">14:00</option>
                            <option value="15:00">15:00</option>
                            <option value="16:00">16:00</option>
                            <option value="17:00">17:00</option>
                            <option value="18:00">18:00</option>
                            <option value="19:00">19:00</option>
                            <option value="20:00">20:00</option>
                            <option value="21:00">21:00</option>
                            <option value="22:00">22:00</option>
                            <option value="23:00">23:00</option>
                        </select>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="col text-center mt-4">
                <div>
                    <input class="btn btn-primary btn-block text-down mt-3 w-25" type="submit"
                           name="route_search"
                           value="<fmt:message key="rout.search"/>">
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fillDirections.js"></script>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>
