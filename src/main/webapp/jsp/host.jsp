<%--
  Created by IntelliJ IDEA.
  User: loic-
  Date: 02/03/2023
  Time: 09:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="template/debut.jsp" %>
<!--Page host permettant la génération d'un code aléatoire pour autentifier la partie-->
<div class="container login-container">
    <div class="row">
        <div class="col-md-12 login-form text-center">
            <h1>Hôte</h1>
            <div class="mt-5">

                <div class="form-group">
                    <a type="button" class="btnSubmit" onclick="getCode()"/>Création du nouveau code</a>
                    <!--Création aléatoire du code pour rejoindre la salle de jeu-->
                </div>
                <form action="<c:url value="Host"/>" method="post">
                    <div class="form-group">
                        <input name="code" type="String" class="form-control donttouch"
                               placeholder="CODE" value="" readonly/> <!--Le code générer aléatoirement-->
                    </div>
                    <div class="form-group">
                        <a class="join btnSubmit" href="<c:url value="AttributionRole"/>"/>
                        REJOINDRE LA SIMULATION</a>
                        <!--Permet de rejoindre la simulation-->
                    </div>
                    <div class="form-group mt-5">
                        <a class="btnSubmit" href="<c:url value="Guest"/>">Invité</a>
                        <!--Permet de changer de profil au cas où l'utilisateur s'est trompé-->
                    </div>
                    <div class="form-group mt-3">
                        <a href="<c:url value="Logout"/>">Déconnexion</a> <!--Retour à la page d'accueil-->
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function getCode() {
        $.get("<c:url value="GenCode"/>", function (code) {
            $(".result").html(code);
            console.log(code);
            $("input[name=code]").val(code);
        });
    }
</script>
<%@ include file="template/fin.jsp" %>

