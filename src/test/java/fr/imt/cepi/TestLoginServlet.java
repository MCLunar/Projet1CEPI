package fr.imt.cepi;

import fr.imt.cepi.servlet.LoginServlet;
import fr.imt.cepi.servlet.listeners.AppContextListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Exemple de test des Servlets avec mockito
 */
public class TestLoginServlet extends Mockito {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcherLogin;
    @Mock
    ServletContext context;
    @Mock
    HttpSession session;
    @Mock
    ServletConfig config;

    @Before
    public void setUp() throws Exception {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {

            when(request.getSession()).thenReturn(session);

            Properties props = new Properties();
            props.load(new FileReader("src/main/webapp/WEB-INF/db.properties"));
            AppContextListener.setupDriver(props);

            // Crée un user aaa s'il n'existe pas
            Connection con = AppContextListener.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM joueur where nom=?");
            ps.setString(1, "aaa");
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("INSERT INTO joueur(nom, prenom) VALUES (?, ?)");
            int i = 0;
            ps.setString(++i, "aaa");
            ps.setString(++i, "bbb");
            ps.executeUpdate();
        }

    }

    @Test
    public void testLoginServlet() throws Exception {

        LoginServlet servlet = new LoginServlet();
        servlet.init(config);
        when(servlet.getServletContext()).thenReturn(context);

        when(servlet.getServletContext().getRequestDispatcher("/jsp/login.jsp")).thenReturn(dispatcherLogin);

        // Avec des identifiants qui n'existent pas
        when(request.getParameter("nom")).thenReturn("nom");
        when(request.getParameter("prenom")).thenReturn("prenom");
        servlet.doPost(request, response);

        verify(dispatcherLogin).forward(request, response);
        verify(request, atLeast(1)).getParameter("nom");
        verify(request, atLeast(1)).getParameter("prenom");


        // Avec des identifiants qui existent
        when(request.getParameter("nom")).thenReturn("aaa");
        when(request.getParameter("prenom")).thenReturn("bbb");
        servlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect("Home");
        verify(request, atLeast(1)).getParameter("nom");
        verify(request, atLeast(1)).getParameter("prenom");
    }
}