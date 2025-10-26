package console;

import dao.ClientDAO;
import dao.ReservationDAO;
import dao.TripDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {

    //Test
    public static void main(String[] args) {

        CSVLoader load = new CSVLoader();

        List<TrainConnection> list = new ArrayList<>();
        list = load.getConnectionList();

        MainMenuUI mainMenuUI = new MainMenuUI();

        int mainMenuChoice = MainMenuUI.displayMainMenu();

        if (mainMenuChoice == 1) {

            MainMenuUI.SearchFilter filter = MainMenuUI.getSearchFilters();
            MainMenuUI.displayTrainConnections(filter, list);

            BookingUI bookingUI = new BookingUI();
            List<String> chosenTripRoute = bookingUI.askToBookTrip();

            //Display chosen trip route to client
            ArrayList<TrainConnection> allConnections = new ArrayList<>(list);
            ArrayList<TrainConnection> chosenConnections = new ArrayList<>();

            System.out.println("\nYour chosen trip route(s):");
            for (String chosenId : chosenTripRoute) {
                for (TrainConnection connection : allConnections) {
                    if (connection.getRouteID().equals(chosenId)) {
                        System.out.println(connection.toFormattedString()); // display nicely
                        chosenConnections.add(connection);                 // build the actual chosen trip
                        break; // stop inner loop once match is found
                    }
                }
            }

            Client[] clients = BookingUI.getClientDetails();

            bookingUI.createReservations(clients, chosenConnections);

        }
        else System.exit(0);




//        Client client1 = new Client("Doe", "John", 30, 1);
//        Client client2 = new Client("John", "Cena", 31, 2);
//
//
//        ArrayList<TrainConnection> route = new ArrayList<>();
//        route.add(list.get(6));
//        route.add(list.get(8));
//        Ticket ticket1 = new Ticket(1);
//        Reservation reservation1 = new Reservation(client1, route, ticket1);
//        Ticket ticket2 = new Ticket(2);
//        Reservation reservation2 = new Reservation(client2, route, ticket2);
//        ArrayList<Reservation> reservations = new ArrayList<>();
//
//        reservations.add(reservation1);
//        reservations.add(reservation2);
//
//
//        ClientDAO cd = new ClientDAO();
//        client1=cd.insert(client1);
//        client2=cd.insert(client2);
//        ReservationDAO rd = new ReservationDAO();
//        reservation1=rd.insert(reservation1);
//        reservation2=rd.insert(reservation2);
//        TripDAO td = new TripDAO();
//        Trip trip1 = new Trip(1, reservations);
//        trip1 = td.insert(trip1);



    }  



}


