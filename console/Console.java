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
            List<MultipleStopsMetric> availableTrips = MainMenuUI.displayTrainConnections(filter, list);

            if (availableTrips!=null) {
                //BookingUI bookingUI = new BookingUI();
                MultipleStopsMetric chosenTripRoute = BookingUI.askToBookTrip(availableTrips);

                if  (chosenTripRoute!=null) {
                    ArrayList<TrainConnection> chosenConnections = new ArrayList<>();
                    System.out.println("\nYour chosen trip route(s):");
                    for (int i=0; i<chosenTripRoute.getConnections().size(); i++) {
                        chosenConnections.add(chosenTripRoute.getConnections().get(i));
                        System.out.println(chosenConnections.get(i).toFormattedString());
                    }

                    Client[] clients = BookingUI.getClientDetails();
                    BookingUI.createReservations(clients, chosenConnections);
                }
            }

//
//            //Display chosen trip route to client
//            ArrayList<TrainConnection> allConnections = new ArrayList<>(list);
//            ArrayList<TrainConnection> chosenConnections = new ArrayList<>();
//            System.out.println("\nYour chosen trip route(s):");
//            for (String chosenId : chosenTripRoute) {
//                for (TrainConnection connection : allConnections) {
//                    if (connection.getRouteID().equals(chosenId)) {
//                        System.out.println(connection.toFormattedString()); // display nicely
//                        chosenConnections.add(connection);                 // build the actual chosen trip
//                        break; // stop inner loop once match is found
//                    }
//                }
//            }

        }
        if (mainMenuChoice == 2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n=== View Your Trips ===");
            System.out.print("Enter your Client ID: ");
            int clientId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your Last Name: ");
            String lastName = scanner.nextLine();

            TripDAO tripDAO = new TripDAO();
            ReservationDAO reservationDAO = new ReservationDAO();
            ClientDAO clientDAO = new ClientDAO();

            // Fetch all reservations for this client
            ArrayList<Reservation> clientReservations = reservationDAO.getReservationsByClientIdAndLastName(clientId, lastName);

            if (clientReservations.isEmpty()) {
                System.out.println("No trips found for client ID " + clientId + " and last name " + lastName);
            } else {
                System.out.println("\nYour Trips:");
                int count = 1;
                for (Reservation res : clientReservations) {
                    System.out.println("\nTrip #" + count++);
                    System.out.println("Reservation ID: " + res.getReservationId());
                    System.out.println("Train Connections:");
                    for (TrainConnection c : res.getConnections()) {
                        System.out.println("  " + c.getDepartureCity() + " -> " + c.getArrivalCity() +
                                " | Dep: " + c.getDepartureTime() +
                                " | Arr: " + c.getArrivalTime() +
                                " | Train: " + c.getTrainType());
                    }
                    System.out.println("Travel Class: " + res.getTicket().getTravelClass());
                    System.out.println("Total Price: $" + res.getTicket().getTotalPrice());
                }
            }

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


