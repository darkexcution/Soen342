import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL  = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (URL == null || USER == null || PASS == null) {
            throw new IllegalStateException("DB credentials not set in environment variables.");
        }
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
