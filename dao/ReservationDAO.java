package dao;

import database.Database;
import model.CSVLoader;
import model.Reservation;
import model.Ticket;
import model.TrainConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDAO {

    public Reservation insert(Reservation reservation) {
        String sql = "INSERT INTO public.reservation (client_id, route_ids) VALUES (?, ?)";
        //String insertConnectionSql = "INSERT INTO public.reservation_connection (reservation_id, route_id) VALUES (?, ?)";

        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ArrayList<TrainConnection> connections = reservation.getConnections();
            String[] routeArray = new String[connections.size()];
            for (int i = 0; i < connections.size(); i++) {
                routeArray[i] = connections.get(i).getRouteID();
            }
            Array sqlArray = dbConn.createArrayOf("text", routeArray);

            stmt.setInt(1, reservation.getClient().getID());
            stmt.setArray(2, sqlArray);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    reservation.setReservationId(generatedId);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservation;
    }
    public ArrayList<Reservation> getReservationsByClientIdAndLastName(int clientId, String lastName) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.reservation_id, r.route_ids, t.ticket_id, t.travel_class, t.total_price " +
                "FROM reservation r " +
                "JOIN client c ON r.client_id = c.client_id " +
                "JOIN ticket t ON t.reservation_id = r.reservation_id " +
                "WHERE c.client_id = ? AND c.last_name = ?";

        try (Connection dbConn = Database.getConnection();
             PreparedStatement stmt = dbConn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            stmt.setString(2, lastName);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int resId = rs.getInt("reservation_id");
                Array routeArray = rs.getArray("route_ids");
                String[] routeIds = (String[]) routeArray.getArray();

                // Fetch TrainConnection objects
                ArrayList<TrainConnection> connections = new ArrayList<>();
                for (String routeId : routeIds) {
                    TrainConnection c = getConnectionById(routeId);
                    connections.add(c);
                }
                String priceStr = rs.getString("total_price");
                double price = priceStr != null ? Double.parseDouble(priceStr) : 0.0;
                Ticket ticket = new Ticket(rs.getInt("total_price"), rs.getString("travel_class"));
                Reservation res = new Reservation(null, connections, ticket);
                res.setReservationId(resId);
                reservations.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public TrainConnection getConnectionById(String routeId) {
        CSVLoader loader = new CSVLoader();
        for (TrainConnection c : loader.getConnectionList()) {
            if (c.getRouteID().equals(routeId)) {
                return c;
            }
        }
        return null;
    }


}
