import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientDAO {

    public Client insert(Client client) {
        String sql = "INSERT INTO public.client (first_name, last_name, age) VALUES (?, ?, ?)";
        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setInt(3, client.getAge());

            stmt.executeUpdate();

            // get the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
