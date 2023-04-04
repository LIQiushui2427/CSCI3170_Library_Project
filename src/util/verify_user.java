package util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class verify_user  {
    public static boolean verify(Connection conn, String UID)throws Exception{
        String check = "SELECT COUNT(*) FROM users WHERE UID = ?";
        PreparedStatement ps = conn.prepareStatement(check);
        ps.setString(1, UID);
        java.sql.ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 0) {
            System.out.println("User not found! return to main menu");
            return false;
        }
        return true;
    }
}
