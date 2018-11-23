import com.sun.xml.internal.rngom.parse.host.Base;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class BaseDao {
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/visualonline";
    private static String user="root";
    private static String password="";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException{
        if (rs!=null) {
            rs.close();
        }
        if (stmt!=null) {
            stmt.close();
        }
        if (conn!=null) {
            conn.close();
        }
    }
}