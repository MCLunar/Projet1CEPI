<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="template/debut.jsp" %>
<!--Page qui permet à l'utilisateur connecté de choisir entre Guest et Host (Le premier à se connecter est host)-->
<div class="container login-container">
    <div class="col-md-12 login-form text-center">

        <div class="col-md-6 title2">Bonjour ${joueur.prenom} ${joueur.nom}</div>
        <div class="mt-5">
            <div class="form-group">
                <a type="button" class="btnSubmit" href="<c:url value="Host"/>">Hôte</a>
            </div>
            <div class="form-group">
                <a type="button" class="btnSubmit" href="<c:url value="Guest"/>">Invité</a>
            </div>
            <div class="form-group">
                <a href="<c:url value="Logout"/>">Déconnexion</a> <!--Retour à la page d'accueil-->
            </div>
        </div>

    </div>
</div>
<%@ include file="template/fin.jsp" %>
