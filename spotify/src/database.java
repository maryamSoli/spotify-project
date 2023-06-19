import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class database {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/spotify";
    private static final String USER = "postgres";
    private static final String PASSWORD = "PostgreSQL";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Users";
            rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String email = rs.getString("email");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Username: " + username);
                System.out.println(", Email: " + email);
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}


