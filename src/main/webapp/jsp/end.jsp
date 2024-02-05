<%--
  Created by IntelliJ IDEA.
  User: loic-
  Date: 09/03/2023
  Time: 09:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="template/debut.jsp" %>
<!--Page de fin du jeu _ Pas encore adaptée-->
<div class="container login-container">
    <div class="col-md-12 login-form text-center">

        <div class="col-md-6 title2">Au revoir ${joueur.prenom} ${joueur.nom}</div>
        <div class="mt-5">
            <div class="form-group">
                <a href="<c:url value="Logout"/>">Déconnexion</a>
            </div>
        </div>

    </div>
</div>
<%@ include file="template/fin.jsp" %>
