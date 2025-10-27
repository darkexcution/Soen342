package model;

import java.util.ArrayList;

public class Trip {

    private int tripId;
    private ArrayList<Reservation> reservations;

    public Trip (ArrayList<Reservation> reservations) {
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
