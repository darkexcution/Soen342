package dao;

import database.Database;
import model.Reservation;
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

//            try (PreparedStatement stmtConn = dbConn.prepareStatement(insertConnectionSql)) {
//                for (TrainConnection connection : reservation.getConnections()) {
//                    stmtConn.setInt(1, reservation.getReservationId());
//                    stmtConn.setString(2, connection.getRouteID());
//                    stmtConn.addBatch();
//                }
//                stmtConn.executeBatch(); // insert all at once
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservation;
    }
}
