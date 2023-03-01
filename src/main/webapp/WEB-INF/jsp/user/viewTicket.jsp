<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
  <title><fmt:message key="admin.order.information"/></title>
  <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-md-12 text-center">
      <h4>[Train Name and Number] - [Departure Date]</h4>
      <h5>[From Station] to [To Station]</h5>
    </div>
  </div>

  <div class="row">
    <div class="col-md-6">
      <p>Passenger Name: [Passenger Name]</p>
      <p>Seat Number: [Seat Number]</p>
    </div>

    <div class="col-md-6 text-right">
      <img src="[QR Code Image]" alt="QR Code" class="img-thumbnail">
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-md-6">
      <p>Ticket Type: [Standard/First Class]</p>
      <p>Fare: $[Ticket Price]</p>
      <p>Booking ID: [Booking Reference Number]</p>
    </div>

    <div class="col-md-6 text-right">
      <p><strong>Instructions:</strong></p>
      <ul>
        <li>Show this ticket to the train conductor for validation</li>
        <li>Board the train at least 15 minutes before the departure time</li>
        <li>Keep your ID proof ready for verification</li>
      </ul>
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-md-12 text-center">
      <p>Powered by [Railway Company Name]</p>
    </div>
  </div>
</div>

</body>
</html>
