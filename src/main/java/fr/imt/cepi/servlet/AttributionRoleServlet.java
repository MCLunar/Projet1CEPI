package fr.imt.cepi.servlet;

import fr.imt.cepi.bean.Code;
import fr.imt.cepi.bean.Joueur;
import fr.imt.cepi.bean.Partie;
import fr.imt.cepi.servlet.listeners.AppContextListener;
import org.apache.commons.dbcp2.BasicDataSource;
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
import java.util.ArrayList;
import java.util.Collections;

@WebServlet({"/AttributionRole"})
public class AttributionRoleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(HomeServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/attributionRole.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // récupérations le code de la partie en session
        String code = (String) request.getSession().getAttribute("codePartie");

        // Quelques contrôles
        String errorMsg = null;
        if (code == null || code.equals("")) {
            errorMsg = "Pas de code de salle dans la wanting room";
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
                //Connexion à la page, puis on vérifie si la partie à 3 joueur
                // on compte le nombre de joueur déjà présent dans la partie
                connexion = AppContextListener.getConnection();
                ps = connexion.prepareStatement(
                        "select count(*) from jouer where codePartie = ? ");
                ps.setString(1, code);
                //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                rs = ps.executeQuery();

                rs.next(); // pointe la 1er ligne du résultat
                int nombreJoueur = rs.getInt(1);


                // s'il y a trois joueurs la partie peut commencer et on peut leur affecter à tous un rôle
                if (nombreJoueur == 3) {
                    // on créer une liste des rôles
                    ArrayList<String> listeRoles = new ArrayList();
                    listeRoles.add("State");
                    listeRoles.add("Community");
                    listeRoles.add("Company");

                    // on la désordonne pour avoir les rôles dans un ordre aléatoire et à chaque partie différent
                    Collections.shuffle(listeRoles);

                    connexion = AppContextListener.getConnection();
                    ps = connexion.prepareStatement(
                            "select idJoueur from jouer where codePartie = ? ");
                    ps.setString(1, code);
                    //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                    rs = ps.executeQuery();

                    // Création de la liste qui contiendra l'ID des joueurs de la partie
                    ArrayList<Integer> listeJoueurs = new ArrayList();
                    // remplissage de la liste à partir du résultat de la requête SQL
                    while (rs.next()) // parcours le rs ligne par ligne
                    {
                        listeJoueurs.add(rs.getInt(1));
                    }

                    // on affecte les rôles à chaque joueur
                    for (int i = 0; i < 3; i++) {
                        ps = connexion.prepareStatement(
                                "insert into jouer (roleJouer) values (?) where idJoueur = ? ");

                        ps.setString(1, listeRoles.get(i));
                        ps.setInt(2, listeJoueurs.get(i));
                        ps.execute();
                    }

                }

                // sinon on attend que d'autres joueurs rejoignent la partie
                else {
                    errorMsg = "en attente d'autres joueurs";
                }
                connexion = AppContextListener.getConnection();
                ps = connexion.prepareStatement(
                        "select roleJouer from jouer where idJoueur = ? ");
                HttpSession session = request.getSession();
                Joueur joueur= (Joueur) session.getAttribute("joueur");
                ps.setInt(1, joueur.getId());
                //executeQuery permet d'executer la requête SQL, et renvoie un boolean pour savoir s'il a bien executé la requête ie trouvé le joueur ou non
                rs = ps.executeQuery();
                rs.next();
                String roleJouer = rs.getString(1);

                // et on génère le résultat avec la page debut.jsp
                response.sendRedirect(" GameBoard" + roleJouer ); //nom à redéfinir pcq ça redirige vers la page de jeu

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


