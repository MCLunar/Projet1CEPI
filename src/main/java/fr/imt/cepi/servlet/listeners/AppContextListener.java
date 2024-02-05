package fr.imt.cepi.servlet.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;


import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class AppContextListener implements ServletContextListener {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:MyPool");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Initialisation du context");
        ServletContext ctx = servletContextEvent.getServletContext();

        String webAppPath = ctx.getRealPath("/");

        // Initialisation du pool de connexion
        try {
            Properties props = new Properties();
            props.load(new FileReader(webAppPath + "WEB-INF/db.properties"));
            setupDriver(props);
            getConnection();
            System.out.println("Test connection base de données : OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // initialisation de log4j2
        String log4jProp = webAppPath + "WEB-INF/log4j2.xml";
        try (LoggerContext ignored = Configurator.initialize(null,
                new ConfigurationSource(new FileInputStream(log4jProp)))) {
            System.out.println("Configuration log4j : OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            destroyDriver();
        } catch (Exception ignored) {
        }
    }

    /**
     * Crée un driver JDBC pour un pool de connexion nommé
     * "jdbc:apache:commons:dbcp:MyPool"
     */
    public static void setupDriver(Properties properties) throws Exception {

        ConnectionFactory cf = new DriverManagerConnectionFactory(properties.getProperty("url"), properties);
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
        ObjectPool<PoolableConnection> op = new GenericObjectPool<>(pcf);
        pcf.setPool(op);
        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
        Class.forName(properties.getProperty("driver"));
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.registerPool("MyPool", op);
        System.out.println("Initialisation pool de connection : OK");
    }

    public static void destroyDriver() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool("MyPool");
    }

}
