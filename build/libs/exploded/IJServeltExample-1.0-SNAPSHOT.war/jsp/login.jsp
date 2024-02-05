<%@ page pageEncoding="UTF-8" %>
<%@include file="template/debut.jsp" %>
<div class="container login-container">
    <div class="col-md-12 login-form text-center">
        <div class="col-md-6 title1">Connexion</div>
        <%@ include file="template/alerts.jsp" %>
        <form action="Login" method="post">
            <div class="form-group">
                <input name="prenom" type="text" class="form-control"
                       placeholder="PRÉNOM" value="" required autofocus/>
            </div>
            <div class="form-group">
                <input name="nom" type="text" class="form-control"
                       placeholder="NOM" value="" required/>
            </div>
            <div class="form-group">
                <input type="submit" class="btnSubmit" value="Se connecter" name="connect"/>
            </div>
        </form>
    </div>
</div>
<%@include file="template/fin.jsp" %>