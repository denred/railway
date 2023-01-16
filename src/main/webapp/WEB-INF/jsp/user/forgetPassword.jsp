<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<main class="content">
  <form class="form-wrapper" method="post" action="controller" id="login-form">
    <fieldset class="fieldset">
      <input type="hidden" name="action" value="sendForgetPasswordData">
      <legend class="title">
        <fmt:message key="user.forgotPassword"/>
      </legend>
      <label>
        <span><fmt:message key="user.email"/></span>
        <input type="text" name="email" class="input" required>
      </label>
      <label class="inputfield">
        <input class="btn submit" type="submit" value=<fmt:message key="submit"/> >
      </label>
    </fieldset>
  </form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
