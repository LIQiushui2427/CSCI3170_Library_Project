package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class init_db {

    public static Connection initializeDatabase (Connection connection) throws Exception {

             Statement statement = connection.createStatement();
            // delete existing tables if they exist
            statement.executeUpdate("DROP TABLE IF EXISTS books");
            statement.executeUpdate("DROP TABLE IF EXISTS orders");
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            // Create the 'books' table
            String createBooksTable = "CREATE TABLE `books` (\n" +
                    "  `isbn` varchar(45) NOT NULL,\n" +
                    "  `title` varchar(45) NOT NULL,\n" +
                    "  `authors` varchar(50) NOT NULL,\n" +
                    "  `price` int NOT NULL,\n" +
                    "  `storage` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`isbn`),\n" +
                    "  UNIQUE KEY `isbn_UNIQUE` (`isbn`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            statement.executeUpdate(createBooksTable);

            // Create the 'orders' table
            String createOrdersTable = "CREATE TABLE `orders` (\n" +
                    "  `oid` varchar(45) NOT NULL,\n" +
                    "  `uid` varchar(45) NOT NULL,\n" +
                    "  `order_date` timestamp(6) NOT NULL,\n" +
                    "  `order_isbn` varchar(45) NOT NULL,\n" +
                    "  `order_quantity` int NOT NULL,\n" +
                    "  `shipping_status` varchar(45) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            statement.executeUpdate(createOrdersTable);

            // Create the 'users' table
            String createUsersTable = "CREATE TABLE `users` (\n" +
                    "  `uid` int unsigned NOT NULL,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  `address` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`uid`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            statement.executeUpdate(createUsersTable);
            System.out.println("Database initialized successfully!");
            return connection;
        }


    public static void load(String path1, String path2) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/library_system";
        String username = "root";
        String password = "12345678";
        Connection conn = DriverManager.getConnection(url,username,password);


        BufferedReader reader = new BufferedReader(new FileReader(path1));
        String line = null;
        while((line = reader.readLine()) != null){
            String[] data = line.split(",");
            String uid = data[0];
            String name = data[1];
            String address = data[2];
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (uid, name, address) VALUES (?, ?, ?)");
            stmt.setString(1, uid);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.executeUpdate();
        }
        reader.close();
        System.out.println("User initialized successfully!");

        String sql_1 = "insert into books (isbn, title, authors, price, storage) values(?, ?, ?, ?, ?)";
        conn.prepareStatement(sql_1);
        BufferedReader reader1 = new BufferedReader(new FileReader(path2));
        String line1 = null;
        while((line1 = reader1.readLine()) != null){
            String[] data = line1.split(",");
            String isbn = data[0];
            String title = data[1];
            //System.out.println(title);
            String authors = data[2];
            String price = data[3];
            String storage = data[4];
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (isbn, title, authors, price, storage) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, isbn);
            stmt.setString(2, title);
            stmt.setString(3, authors);
            stmt.setString(4, price);
            stmt.setString(5, storage);
            stmt.executeUpdate();
        }
        System.out.println("Book initialized successfully!");
    }
}
