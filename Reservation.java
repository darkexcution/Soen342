public class Reservation {
    String travellerName;
    int travellerAge;
    String travellerId;
    TrainConnection connection;
    Ticket ticket;

    Ticket getTicket() {
        return ticket;
    }

    String getTravellerName() {
        return travellerName;
    }

}
