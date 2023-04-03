package util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class verify_user  {
    public static boolean verify(Connection conn, String uid)throws Exception{
        String check = "SELECT COUNT(*) FROM users WHERE uid = ?";
        PreparedStatement ps = conn.prepareStatement(check);
        ps.setString(1, uid);
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
