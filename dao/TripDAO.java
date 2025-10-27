package dao;

import database.Database;
import model.Reservation;
import model.Trip;

import java.sql.*;
import java.util.ArrayList;

public class TripDAO {

    public Trip insert(Trip trip) {
        String sql = "INSERT INTO public.trip (reservation_ids) VALUES (?)";
        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ArrayList<Reservation> list = trip.getReservations();
            Integer[] ticket=new Integer[list.size()];
            for (int i=0; i<ticket.length; i++) {
                ticket[i] = list.get(i).getTicket().getTicketId();
            }
            Array tickets = dbConn.createArrayOf("integer", ticket);
            stmt.setArray(1, tickets);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    trip.setTripId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trip;
    }
}

