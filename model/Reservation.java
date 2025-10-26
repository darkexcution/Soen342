package model;

import java.util.ArrayList;

public class Reservation {

    private int reservationId;
    private Client client;
    private ArrayList<TrainConnection> connections;
    private Ticket ticket;

    public Reservation (Client client, ArrayList <TrainConnection> connections, Ticket ticket) {
        this.client = client;
        this.connections=connections;
        this.ticket=ticket;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<TrainConnection> getConnections() {
        return connections;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket=ticket;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation{reservationId=").append(reservationId)
                .append(", client=").append(client.getFirstName()).append(" ").append(client.getLastName())
                .append(", connections=[");
        for (TrainConnection c : connections) {
            sb.append(c.getRouteID()).append(", ");
        }
        if (!connections.isEmpty()) sb.setLength(sb.length() - 2);
        sb.append("], ticket=").append(ticket).append('}');
        return sb.toString();
    }
}
