<%--
  Created by IntelliJ IDEA.
  User: loic-
  Date: 02/03/2023
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="template/debut.jsp" %>
<!--Page d'accès au profil Guest-->
<div class="container login-container">
    <div class="row">
        <div class="col-md-12 login-form text-center">


            <h1>Invité</h1>
            <div class="mt-5">
                <form action="<c:url value="Guest"/>" method="post">
                    <div class="form-group">
                        <input name="code" type="String" class="form-control"
                               placeholder="CODE" value="" required autofocus/> <!--Insertion du code permettant de rejoindre la partie créer par l'host-->
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="REJOINDRE LA SIMULATION" name="connect"/>
                    </div>
                    <div class="form-group mt-3">
                        <a href="<c:url value="Logout"/>">Déconnexion</a> <!--Retour à la page d'accueil-->
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="template/fin.jsp" %>
