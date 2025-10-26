package model;

public class Ticket {

    private int ticketId;          // DB-generated
    private int reservationId;     // FK to Reservation table
    private String travelClass;    // "First" or "Second"
    private double totalPrice;     // Price for this client

    public Ticket(double totalPrice, String travelClass) {
        this.totalPrice = totalPrice;
        this.travelClass = travelClass;
    }

    // Getters and setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Ticket{ticketId=" + ticketId +
                ", travelClass='" + travelClass + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
