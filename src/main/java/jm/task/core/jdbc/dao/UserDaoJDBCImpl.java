package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util;

    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    public void createUsersTable() {
        try (Connection connection = util.getConnect();
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS users(" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
                    " name VARCHAR(50) NOT NULL ," +
                    "lastname VARCHAR(70) NOT NULL," +
                    "age TINYINT NOT NULL);");
            System.out.println("Таблица 'users' создана !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnect();
             Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS users");
            System.out.println("Таблица 'users' успешна удалена !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnect();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO users(name, lastname, age) VALUES " +
                     "(?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnect();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);

            if (statement.executeUpdate() > 0) {
                System.out.printf("User с id: %d удален !\n", id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = util.getConnect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM users")) {

            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("Таблица users успешно очищена !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
