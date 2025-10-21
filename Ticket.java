
public class Ticket {

    //private static int IDincrement; //To have unique ID, need database
    private int ticketID;
    //private Reservation reservation; //If its ticket that has reservation but not sure

    public Ticket(int ticketID) {
        this.ticketID=ticketID;
    }

    public int getTicketID() {
        return ticketID;
    }
}
