<%--
  Created by IntelliJ IDEA.
  User: loic-
  Date: 02/03/2023
  Time: 09:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="template/debut.jsp" %>
<!--Plateau de jeu qui met en commun tous les joueurs et le jeu final-->
<div class="container login-container">
    <div class="col-md-12 login-form text-center">
        <h1>Game Board</h1>
        <div class="mt-5">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <ul class="list-group">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                ${joueur.prenom} ${joueur.nom} - Entreprise
                                <span class="badge badge-primary badge-pill">Nombre de tours effectués</span>
                            </li>
                        </ul>
                    </div>
                    <div class="col">
                        <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                            <div class="btn-group">
                                <button type="button" class="btn btn-danger">Action</button> <!--Répertorie les actions disponibles-->
                                <button type="button" class="btn btn-danger dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item acheterUnTerrain" href="#"
                                       data-html="true"
                                       data-popover-content="#acheterUnTerrain"
                                       data-toggle="popover"
                                       data-trigger="hover">Acheter un terrain</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item vendreUnTerrain" href="#"
                                       data-html="true"
                                       data-popover-content="#vendreUnTerrain"
                                       data-toggle="popover"
                                       data-trigger="hover">Vendre un terrain</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item donsADesAssociations" href="#"
                                       data-html="true"
                                       data-popover-content="#donsADesAssociations"
                                       data-toggle="popover"
                                       data-trigger="hover">Dons à des associations</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item accroissement" href="#"
                                       data-html="true"
                                       data-popover-content="#accroissement"
                                       data-toggle="popover"
                                       data-trigger="hover">Accroissement</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item fermeture" href="#"
                                       data-html="true"
                                       data-popover-content="#fermeture"
                                       data-toggle="popover"
                                       data-trigger="hover">Fermeture</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="w-100"></div>
                    <div class="col">
                        <div class="progress mt-5">
                            <div class="progress-bar progress-bar-animated bg-warning"
                                 role="progressbar" aria-valuenow="75" aria-valuemin="0"
                                 aria-valuemax="100" style="width: 75%"></div>
                        </div>
                        <div class="progress mt-5">
                            <div class="progress-bar progress-bar-animated bg-success"
                                 role="progressbar" aria-valuenow="65" aria-valuemin="0"
                                 aria-valuemax="100" style="width: 65%"></div>
                        </div>
                        <div class="progress mt-5">
                            <div class="progress-bar progress-bar-animated "
                                 role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                 aria-valuemax="100" style="width: 20%"></div>
                        </div>
                        <div class="progress mt-5">
                            <div class="progress-bar progress-bar-animated bg-danger"
                                 role="progressbar" aria-valuenow="90" aria-valuemin="0"
                                 aria-valuemax="100" style="width: 90%"></div>
                        </div>
                    </div>
                    <div class="col">
                        <div>
                            <input type="text" id="username" placeholder="Username"/>
                            <button type="button" onclick="client.connect();">Connect</button>
                        </div>
                        <textarea readonly="true" rows="10" cols="80" id="log"></textarea>
                        <div>
                            <input type="text" size="51" id="msg" placeholder="Message"/>
                            <button type="button" onclick="client.send();">Send</button>
                        </div>
                        </body>
                        <script src="js/chat.js"></script>
                        <script type="application/javascript">
                            const client = new ChatClient();
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<div class="hidden d-none" id="acheterUnTerrain">
    <div class="popover-body">
        Collectivités : <br/><br/>
        Etat : <br/><br/>
        Entreprise : <br/>
        - Economie : -15 <br/>
        - Faune : -10
    </div>
</div>
<div class="hidden d-none" id="vendreUnTerrain">
    <div class="popover-body">
        Collectivités : <br/><br/>
        Etat : <br/><br/>
        Entreprise : <br/>
        - Economie : +25 <br/>
        - Faune : +20
    </div>
</div>
<div class="hidden d-none" id="donsADesAssociations">
    <div class="popover-body">
        Collectivités : <br/>
        - Economie : +5 <br/>
        - Satisfication : +10 <br/><br/>
        Etat : <br/><br/>
        Entreprise : <br/>
        - Economie : -5 <br/>
        - Satisfication : +10 <br/>
        - Faune : +5
    </div>
</div>
<div class="hidden d-none" id="accroissement">
    <div class="popover-body">
        Collectivités : <br/>
        - Satisfication : -5 <br/><br/>
        Etat : <br/><br/>
        Entreprise : <br/>
        - Economie : -15 <br/>
        - Satisfaction : +10 <br/>
        - Faune : -10
    </div>
</div>
<div class="hidden d-none" id="fermeture">
    <div class="popover-body">
        Collectivités : <br/>
        - Satisfication : +5 <br/><br/>
        Etat : <br/><br/>
        Entreprise : <br/>
        - Economie : -10 <br/>
        - Satisfication : -10 <br/>
        - Faune : +5
    </div>
</div>
<%@ include file="template/fin.jsp" %>

<script>$(function(){

$("[data-toggle=popover]").popover({
html : true,
content: function() {
var content = $(this).attr("data-popover-content");
return $(content).children(".popover-body").html();
},
title: function() {
var title = $(this).attr("data-popover-content");
return $(title).children(".popover-heading").html();
}
});

});</script>

