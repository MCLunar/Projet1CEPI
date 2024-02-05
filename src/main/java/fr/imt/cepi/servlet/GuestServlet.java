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

@WebServlet({"/Guest"})
public class GuestServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(HomeServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/guest.jsp").forward(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // récupérations des paramètres de la requêtes : ici les champs input du formulaire
        String code = request.getParameter("code");

        // Quelques contrôles
        String errorMsg = null;
        if (code == null || code.equals("")) {
            errorMsg = "code à rentrer pour rejoindre une salle";
        }
        // S'il y a des erreurs, on met le message en attribut de la requête et on renvoie sur la page de login
        if (errorMsg != null) {
            request.setAttribute("errorMessage", errorMsg);
            getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } else {
            // Sinon on recherche le code dans la base de données
            Connection connexion = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                //Connexion à la page, puis on check si le code affiché existe
                connexion = AppContextListener.getConnection();
                ps = connexion.prepareStatement(
                        "select * from partie where codePartie=? ");
                ps.setString(1, code);
                //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                rs = ps.executeQuery();
                //s'il n'a pas trouvé (cas où la salle n'existe pas), ça renvoie un False et le cas est traité après
                if (!rs.next()) {
                    request.setAttribute("errorMessage", "La salle que vous avez rentré n'existe pas, veuillez rentrer un nouveau code");
                    getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                    return;
                    }
                //cas où le code existe, il faut ajouter le joueur dans la table Jouer
                else {

                    rs.close(); // il faut "fermé" le pointeur car on en utilise un autre après
                    ps.close(); // il faut "fermé" le pointeur car on en utilise un autre après

                    //on ajoute dans la table jouer l'id du joueur et le code de sa partie pour faire le lien
                    ps = connexion.prepareStatement(
                            "insert into jouer (idJoueur,codePartie, roleJouer) values (?,?, ?) ");

                    // Puis on récupère l'objet joueur dans la session
                    HttpSession session = request.getSession();
                    session.setAttribute("codePartie",code);
                    Joueur joueur= (Joueur) session.getAttribute("joueur");
                    ps.setInt(1, joueur.getId());
                    ps.setString(2, code);
                    ps.setString(3, "non defini");


                    //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                    ps.execute();
                }
                // et on génère le résultat avec la page debut.jsp
                response.sendRedirect(" AttributionRole "); //nom à redéfinir pcq ça redirige vers la page de jeu

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
