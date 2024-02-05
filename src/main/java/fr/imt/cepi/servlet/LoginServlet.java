package fr.imt.cepi.servlet;

import fr.imt.cepi.bean.Joueur;
import fr.imt.cepi.servlet.listeners.AppContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // récupérations des paramètres de la requêtes : ici les champs input du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");

        // Quelques contrôles
        String errorMsg = null;
        if (nom == null || nom.equals("")) {
            errorMsg = "Le nom est obligatoire";
        }
        if (prenom == null || prenom.equals("")) {
            errorMsg = "Le prénom est obligatoire";
        }
        // S'il y a des erreurs, on met le message en attribut de la requête et on renvoie sur la page de login
        if (errorMsg != null) {
            request.setAttribute("errorMessage", errorMsg);
            getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } else {
            // Sinon on recherche le joueur en base de données
            Connection connexion = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                //Connexion à la page, puis on check si le joueur qui se connecte est déjà dans la base de données
                connexion = AppContextListener.getConnection();
                ps = connexion.prepareStatement(
                        "select * from joueur where nomJoueur = ? and prenomJoueur=? ");
                ps.setString(1, nom);
                ps.setString(2, prenom);
                //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                rs = ps.executeQuery();
                //s'il n'a pas trouvé, ça renvoie un False et le cas est traité après
                if (!rs.next()) {
                    //créer le joueur dans la base de données
                    ps = connexion.prepareStatement(
                            "insert into joueur (nomJoueur, prenomJoueur) values (?, ?) ");
                    ps.setString(1, nom);
                    ps.setString(2, prenom);
                    ps.execute();
                    //mtn que le joueur a été créé, on peut bien le sélectionner
                    ps = connexion.prepareStatement(
                            "select * from joueur where nomJoueur = ? and prenomJoueur=? ");
                    ps.setString(1, nom);
                    ps.setString(2, prenom);
                    rs = ps.executeQuery();
                    //tjr un if dans le cas où ça a pas marché
                    if (!rs.next()) {
                        // Sinon, log de l'erreur et renvoi sur la vue login.jsp avec un message d'erreur
                        logger.error("Action impossible ");
                        request.setAttribute("errorMessage", "Erreur technique : veuillez contacter l'administrateur de l'application");
                        getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                        return;
                    }
                }
                // On l'indique dans le log en créant notre joueur
                Joueur joueur = new Joueur(rs.getString("nomJoueur"), rs.getString("prenomJoueur"),
                        rs.getInt("idJoueur"));
                logger.info("Joueur trouvé :" + joueur);
                // Puis on met l'objet utilisateur dans la session
                HttpSession session = request.getSession();
                session.setAttribute("joueur", joueur);
                // et on génère le résultat avec la page debut.jsp
                response.sendRedirect("Home");

            } catch (SQLException e) {
                // Sinon, log de l'erreur et renvoi sur la vue login.jsp avec un message d'erreur
                logger.error("Problème d'accès à la base de données : ", e);
                request.setAttribute("errorMessage", "Erreur technique : veuillez contacter l'administrateur de l'application.");
                getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
