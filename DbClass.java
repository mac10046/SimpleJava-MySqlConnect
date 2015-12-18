
/**
 *
 * @author Abdeali Chandanwala
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbClass {

    public static Connection conn = null;

    public static Connection getConn() {
        try {
            if (conn == null || conn.isValid(1)) {
                if (DbClass.Connect()) {
                    return conn;
                } else {
                    return null;
                }
            }
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DbClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void setConn(Connection conn) {
        DbClass.conn = conn;
    }

    public static boolean Connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return false;
        }
        Connection lconn = null;
        try {
            lconn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/scripdata", "root", "makarand@bp");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }

        if (lconn != null) {
            conn = lconn;
        } else {
            return false;
        }
        return true;
    }

    public static void closeConnection() throws SQLException {
        DbClass.conn.close();
    }

    public static ResultSet execute(String Qry) throws SQLException {
        Statement st = DbClass.getConn().createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(Qry);
        if (rs.first()) {
            rs.beforeFirst();
            return rs;
        } else {
            return null;
        }

    }

    public static boolean isDbConnected() {
        ResultSet Rs = null;
        try {
            return DbClass.conn.isValid(1);
        } catch (SQLException ex) {
            Logger.getLogger(DbClass.class.getName()).log(Level.SEVERE, "MySQL Connection is Closed for some reason", ex);
            return false;
        }
    }

}
