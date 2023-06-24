import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpotifyDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/spotify";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Create user_accounts table if it doesn't exist
            System.out.println("Creating user_accounts table if it doesn't exist...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user_accounts (" +
                    "id INT(11) NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "email VARCHAR(100) NOT NULL UNIQUE," +
                    "password VARCHAR(100) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("user_accounts table created successfully!");

            // Create playlists table if it doesn't exist
            System.out.println("Creating playlists table if it doesn't exist...");
            sql = "CREATE TABLE IF NOT EXISTS playlists (" +
                    "id INT(11) NOT NULL AUTO_INCREMENT," +
                    "title VARCHAR(100) NOT NULL," +
                    "description TEXT," +
                    "created_by INT(11)," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (created_by) REFERENCES user_accounts(id)" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("playlists table created successfully!");

            // Create liked_songs table if it doesn't exist
            System.out.println("Creating liked_songs table if it doesn't exist...");
            sql = "CREATE TABLE IF NOT EXISTS liked_songs (" +
                    "id INT(11) NOT NULL AUTO_INCREMENT," +
                    "user_id INT(11) NOT NULL," +
                    "song_id INT(11) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (user_id) REFERENCES user_accounts(id)," +
                    "FOREIGN KEY (song_id) REFERENCES songs(id)" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("liked_songs table created successfully!");

        } catch (SQLException e) {
            // Handle errors for JDBC
            e.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
