package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class clean {
    public static void delete(Connection conn) throws Exception {
        //delete all data in database
        Class.forName("com.mysql.cj.jdbc.Driver");

        String sql_ = "delete from users";
        conn.prepareStatement(sql_);
        String delete_users = "DELETE FROM users";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(delete_users);
        System.out.println("User deleted successfully!");

        String sql_1 = "delete from books";
        conn.prepareStatement(sql_1);
        String delete_books = "DELETE FROM books";
        Statement stmt1 = conn.createStatement();
        stmt1.executeUpdate(delete_books);
        System.out.println("Book deleted successfully!");

        String sql_2 = "delete from orders";
        conn.prepareStatement(sql_2);
        String delete_orders = "DELETE FROM orders";
        Statement stmt2 = conn.createStatement();
        stmt2.executeUpdate(delete_orders);
        System.out.println("Order deleted successfully!");
    }
}
