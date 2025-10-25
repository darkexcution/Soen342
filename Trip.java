
import java.util.ArrayList;

public class Trip {

    private int tripId;
    private ArrayList<Reservation> reservations;

    public Trip (int tripId, ArrayList<Reservation> reservations) {
        this.tripId=tripId;
        this.reservations=reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int id) {
        tripId=id;
    }


    public ArrayList<Reservation> getReservations(){return reservations;}
}
