package util;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class customer_operation {
    public static void searchBykey(Connection conn, String keyword) throws Exception{
        System.out.println("Search by title:");
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM books WHERE title LIKE '%" + keyword + "%'";
        java.sql.ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println("ISBN: " + rs.getString("isbn") + " Title: " + rs.getString("title") + " Authors: " + rs.getString("authors") + " Price: " + rs.getString("price"));
        }
        System.out.println("Search done!");
    }
    public static void searchByISBN(Connection conn, String keyword) throws Exception{
        System.out.println("Search by ISBN:");
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM books WHERE isbn LIKE '%" + keyword + "%'";
        java.sql.ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println("ISBN: " + rs.getString("isbn") + " Title: " + rs.getString("title") + " Authors: " + rs.getString("authors") + " Price: " + rs.getString("price"));
        }
        System.out.println("Search done!");
    }
    public static void searchByAuthor(Connection conn, String keyword) throws Exception{
        System.out.println("Search by author:");
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM books WHERE authors LIKE '%" + keyword + "%'";
        java.sql.ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println("ISBN: " + rs.getString("isbn") + " Title: " + rs.getString("title") + " Authors: " + rs.getString("authors") + " Price: " + rs.getString("price"));
        }
        System.out.println("Search done!");
    }
    public static void set_order(Connection conn, String uid, String oid, Timestamp date) throws Exception {
        while(set_order_util(conn,uid, oid, date) == 0){
            continue;
        }
    }
    public static int set_order_util(Connection conn, String uid, String oid, Timestamp date) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Last order item sent or failed. Please enter the ISBN of the book you want to order. Enter 0 to end order:");
        String isbn = scanner.next();
        if(isbn == "0")return 1;
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM books WHERE isbn = ?");
        ps.setString(1, isbn);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 0) {
            System.out.println("ISBN Not found, order ends!");
            return 1;
        } else {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM books WHERE isbn = " + isbn;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Got book. ISBN: " + rs.getString("isbn") + " Title: " + rs.getString("title") + " Authors: " + rs.getString("authors") + " Price: " + rs.getString("price") + "Storage: " + rs.getString("storage"));
            }
            System.out.println("Please enter the quantity of the book you want to order:");

            int quantity = scanner.nextInt();

            rs = stmt.executeQuery(sql);
            rs.next();

            int storage = rs.getInt("storage");
            if (quantity > storage) {
                System.out.println("Not enough storage, order ends!");
                return 1;
            } else {
                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO orders VALUES (?,?,?,?,?,?)");
                ps1.setString(1, oid);
                ps1.setString(2, uid);
                ps1.setTimestamp(3, date);
                ps1.setString(4, isbn);
                ps1.setInt(5, quantity);
                ps1.setString(6, "ordered");
                ps1.executeUpdate();
                System.out.println("Order successful! Details:" + "Order ID: " + oid + " ISBN: " + isbn + " Quantity: " + quantity);
                //update the book
                PreparedStatement ps2 = conn.prepareStatement("UPDATE books SET storage = ? WHERE isbn = ?");
                ps2.setInt(1, storage - quantity);
                ps2.setString(2, isbn);
                ps2.executeUpdate();
                System.out.println("Book storage of ISBN " + isbn + " updated to " + (storage - quantity));
                return 0;
            }
        }
    }
    public static void customer_operation(Connection conn, String uid) throws Exception{
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Please choose the following option:\n1 search book\n2 order book\n" +
                    "3 show your orders\n4 show all books\n5 show all users\n6 return to main menu");
            String option = scanner.next();
            switch (option){
                case "1": {
                    System.out.println("Please enter the book name(key) you want to search:");
                    String key = scanner.next();
                    customer_operation.searchBykey(conn, key);
                    customer_operation.searchByAuthor(conn, key);
                    customer_operation.searchByISBN(conn, key);
                    break;
                }
                case "2": {
                    int oid = new Random().nextInt(100000);
                    Timestamp order_date = new Timestamp(System.currentTimeMillis());
                    String oid_str = String.valueOf(oid);
                    System.out.println("Order placed! Your order ID in this session is: " + oid);
                    System.out.println("Please enter the book ISBN you want to order. You can view the available books first by enter 'view' in the console:");
                    String key = scanner.next();
                    if(key.equals("view")){
                        show.show_books(conn);
                    }
                    customer_operation.set_order(conn, uid, oid_str, order_date);
                    break;
                } case "3": {
                    System.out.println("uid:" + uid + "Loading your orders...");
                    show.show_orders(conn, uid);
                    break;
                } case "4": {
                    show.show_books(conn);
                    break;
                } case "5": {
                    show.show_users(conn);
                    break;
                }
                case "6": {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default:
                    System.out.println("Invalid option, please enter again!");
                    break;
            }
        }
    }

}
