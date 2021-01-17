package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        SQLQuery sql = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
                "name VARCHAR(50) NOT NULL ," +
                "lastname VARCHAR(70) NOT NULL," +
                "age TINYINT NOT NULL);");
        sql.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        SQLQuery sql = session.createSQLQuery("DROP TABLE IF EXISTS users");
        sql.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        transaction.commit();
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User");
        List users = query.list();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getHibernateConnect().openSession();
        Transaction transaction = session.beginTransaction();
        SQLQuery sql = session.createSQLQuery("TRUNCATE TABLE users");
        sql.executeUpdate();
        transaction.commit();
        session.close();
    }
}
