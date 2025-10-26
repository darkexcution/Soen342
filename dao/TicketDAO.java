package dao;

import database.Database;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TicketDAO {

    public Ticket insert(Ticket ticket) {
        String sql = "INSERT INTO public.ticket (reservation_id, travel_class, total_price) VALUES (?, ?, ?)";

        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticket.getReservationId());
            stmt.setString(2, ticket.getTravelClass());
            stmt.setDouble(3, ticket.getTotalPrice());

            stmt.executeUpdate();

            // Get generated ticket ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setTicketId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticket;
    }
}
