package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String USER = "root";
    private static final String PASSWORD = "1111";
    private static final String URL = "jdbc:mysql://localhost:3306/usersdb?serverTimezone=Europe/Moscow";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Util() {

    }

    public Connection getConnect() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not fount: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static SessionFactory getHibernateConnect() {
        if (sessionFactory == null) {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection-driver_class", DRIVER);
            prop.setProperty("hibernate.connection.url", URL);
            prop.setProperty("hibernate.connection.username", USER);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

            Configuration configuration = new Configuration().addProperties(prop);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory(
                    new StandardServiceRegistryBuilder().applySettings(prop).build());
        }
        return sessionFactory;
    }

}
