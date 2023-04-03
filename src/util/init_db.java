package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class init_db {
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
