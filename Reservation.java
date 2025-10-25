import java.util.ArrayList;

public class Reservation {

    private Client client;
    private ArrayList<TrainConnection> connections;
    private Ticket ticket;

    public Reservation (Client client, ArrayList <TrainConnection> connections, Ticket ticket) {
        this.client = client;
        this.connections=connections;
        this.ticket=ticket;
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

}
