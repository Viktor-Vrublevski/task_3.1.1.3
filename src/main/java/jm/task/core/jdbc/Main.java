package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;



public class Main {
    public static void main(String[] args) {
       UserService service = new UserServiceImpl();


        service.createUsersTable();

        service.saveUser("Борис", "Смирнов", (byte) 20);
        service.saveUser("Алексей", "Петров", (byte) 19);
        service.saveUser("Тамара", "Иванова", (byte) 23);
        service.saveUser("Ольга", "Прокапович", (byte) 30);

        service.getAllUsers().forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();

        Util.getHibernateConnect().close();
    }
}
