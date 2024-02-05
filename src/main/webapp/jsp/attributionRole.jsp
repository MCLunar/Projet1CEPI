<%--
  Created by IntelliJ IDEA.
  User: titou
  Date: 06/04/2023
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="template/debut.jsp" %>
<div class="container login-container">
  <div class="col-md-6 title1">Waiting Room</div>
  <%@ include file="template/alerts.jsp" %>
  <form action="AttributionRole" method="post">
    <div class="form-group">
      <input type="submit" class="btnSubmit" value="Commencer" name="connect"/>
    </div>
  </form>
  </div>
</div>
<%@include file="template/fin.jsp" %>