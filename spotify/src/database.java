import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class database {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/spotify";
    private static final String USER = "postgres";
    private static final String PASSWORD = "138316";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Insert a new user record into the Users table
            System.out.println("Inserting new user into database...");
            String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "johndoe");
            stmt.setString(2, "johndoe@example.com");
            stmt.setString(3, "password123");
            stmt.executeUpdate();

            // Retrieve user data from the Users table
            System.out.println("Retrieving user data from database...");
            sql = "SELECT * FROM Users WHERE email=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "johndoe@example.com");
            rs = stmt.executeQuery();
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