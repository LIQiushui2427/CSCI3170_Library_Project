package util;

import java.sql.Connection;
import java.sql.Statement;

public class show {
    public static void show_books(Connection conn) {
        String sql = "select * from books";
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("isbn")+ ", " + rs.getString("title") + ", " + rs.getString("authors") + ", " + rs.getString("storage"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            }
        }

        public static void show_users(Connection conn){
            try{Statement stmt = conn.createStatement();
            String sql_ = "SELECT * FROM users";
            java.sql.ResultSet rs = stmt.executeQuery(sql_);
            while(rs.next()){
                System.out.println("UserId: " + rs.getString("uid") + " Username: " +rs.getString("name") +" address: " + rs.getString("address"));
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
            }
        }
        public static void show_orders(Connection conn, String uid){
            try{Statement stmt = conn.createStatement();
            String sql_ = "SELECT * FROM orders WHERE uid = " + uid;
            java.sql.ResultSet rs = stmt.executeQuery(sql_);
            while(rs.next()){
                System.out.println("Order ID: " + rs.getString("oid") +
                        " Order isbn:" + rs.getString("order_isbn")
                        + " Date: " + rs.getString("order_date") +
                        " Quantity: " + rs.getString("order_quantity"));
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        }
}
