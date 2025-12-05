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


    }
}


