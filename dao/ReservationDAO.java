package dao;

import database.Database;
import model.Reservation;
import model.Ticket;
import model.TrainConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDAO {

    public Reservation insert(Reservation reservation) {
        String sql = "INSERT INTO public.reservation (route_id, first_name, last_name, client_id) VALUES (?, ?, ?, ?)";
        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ArrayList <TrainConnection> list = reservation.getConnections();
            String[] route=new String[list.size()];
            for (int i =0; i<route.length; i++) {
                route[i]=list.get(i).getRouteID();
            }
            Array routes = dbConn.createArrayOf("text", route);
            stmt.setArray(1, routes);
            stmt.setString(2, reservation.getClient().getFirstName());
            stmt.setString(3, reservation.getClient().getLastName());
            stmt.setInt(4, reservation.getClient().getID());

            stmt.executeUpdate();

            // get the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Ticket ticket = new Ticket(rs.getInt(1));
                    reservation.setTicket(ticket);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservation;
    }
}

