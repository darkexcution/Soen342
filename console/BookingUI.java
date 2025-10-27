package console;

import dao.ClientDAO;
import dao.ReservationDAO;
import dao.TicketDAO;
import dao.TripDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingUI {

    // Ask the user if they want to book a trip or not and if so, which one
    public static MultipleStopsMetric askToBookTrip(List<MultipleStopsMetric> availableTrips) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Trip Booking ===");
        System.out.println("Please enter the number (without the dot (.)) from the previous searched connection if you want to book a trip (enter 0 if you do not want to): (for example: for Trip No.: 1, enter 1)");
        int routeNo = scanner.nextInt();
        if (routeNo < 0 || routeNo > availableTrips.size()) {
            System.out.println("Invalid route number");
            return null;
        }
        if (routeNo==0) {
            return null;
        }
        return availableTrips.get(routeNo-1);
//        Scanner scanner = new Scanner(System.in);
//        List<String> selectedRouteIds = new ArrayList<>();

//        System.out.println("\nDo you want to book a trip?");
//        System.out.print("Enter 'y' for yes or 'n' for no: ");
//        String askToBook = scanner.nextLine();
//
//        if (!askToBook.equalsIgnoreCase("y")) {
//            return selectedRouteIds; // empty list = user chose not to book
//        }
//        System.out.println("\nWill your trip include stops in-between the source and destination?");
//        System.out.print("Enter 'y' for yes or 'n' for no: ");
//        String hasStops = scanner.nextLine();
//
//        if (hasStops.equalsIgnoreCase("y")) {
//            System.out.println("Enter the route IDs for each leg of your trip, separated by commas (e.g., R101,R203):");
//            String routeIdsInput = scanner.nextLine();
//            String[] routeIds = routeIdsInput.split(",");
//            for (String id : routeIds) {
//                selectedRouteIds.add(id.trim());
//            }
//        } else {
//            System.out.print("Enter the route ID for your direct trip: ");
//            String routeId = scanner.nextLine();
//            selectedRouteIds.add(routeId.trim());
//        }
        //return selectedRouteIds;
    }

    // Get the number of client and their info
    public static Client[] getClientDetails() {

        Scanner scanner = new Scanner(System.in);
        ClientDAO clientDAO = new ClientDAO();

        System.out.print("Enter the number of travellers: ");
        int numClients = Integer.parseInt(scanner.nextLine());

        Client[] clients = new Client[numClients];

        System.out.println("Please fill in the traveller information for each person: first name, last name, and age.");

        for (int i = 0; i < numClients; i++) {
            System.out.println("\nTraveller " + (i+1) + ":");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            clients[i] = new Client(firstName, lastName, age, 0);

//            Client should be inserted into database and given an id (by postgres) after the reservation is made:
//            client = clientDAO.insert(client);
//
//            System.out.println("Added client: " + client.getFirstName() + " " + client.getLastName() +
//                    " (ID assigned: " + client.getID() + ")");
        }
        return clients;
    }

    // Create the reservation and trip to be inserted in the database
    public static List<Reservation> createReservations(Client[] clients, List<TrainConnection> chosenConnections) {

        Scanner scanner = new Scanner(System.in);
        ReservationDAO reservationDAO = new ReservationDAO();
        TicketDAO ticketDAO = new TicketDAO();
        ClientDAO clientDAO = new ClientDAO();
        TripDAO tripDAO = new TripDAO();

        // Show the trip route summary
        System.out.println("\nBooking reservation for trip with " + chosenConnections.size() + " leg(s):");
        for (TrainConnection leg : chosenConnections) {
            System.out.println(leg.toFormattedString());
        }

        // Ask for travel class once (applies to all clients)
        System.out.print("\nChoose travel class (1 for First Class, 2 for Second Class): ");
        int classChoice = Integer.parseInt(scanner.nextLine());
        String travelClass;
        double ticketRate = 0;

        if (classChoice == 1) {
            travelClass = "First Class";
            for (TrainConnection leg : chosenConnections) {
                ticketRate += leg.getFirstClassTicketRate();
            }
        } else {
            travelClass = "Second Class";
            for (TrainConnection leg : chosenConnections) {
                ticketRate += leg.getSecondClassTicketRate();
            }
        }

        ArrayList<Reservation> reservations = new ArrayList<>();

        for (Client client : clients) {

            client = clientDAO.insert(client);

            // Console test output
            //System.out.println("Added client: " + client.getFirstName() + " " + client.getLastName() + " (ID assigned: " + client.getID() + ")");

            Ticket ticket = new Ticket(ticketRate, travelClass);

            Reservation reservation = new Reservation(client, new ArrayList<>(chosenConnections), ticket);
            reservation = reservationDAO.insert(reservation);

            ticket.setReservationId(reservation.getReservationId());
            ticket = ticketDAO.insert(ticket);

            reservation.setTicket(ticket);

            reservations.add(reservation);

            System.out.println("\n🎟️ Ticket for " + client.getFirstName() + " " + client.getLastName());
            System.out.println("----------------------------------------");
            System.out.println("Name: " + client.getFirstName() + " " + client.getLastName());
            System.out.println("Age: " + client.getAge());
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Trip Route:");
            for (TrainConnection leg : chosenConnections) {
                System.out.println("  " + leg.getDepartureCity() + " → " + leg.getArrivalCity() +
                        " | Train: " + leg.getTrainType() +
                        " | Departure: " + leg.getDepartureTime() +
                        " | Arrival: " + leg.getArrivalTime());
            }
            System.out.println("Class: " + travelClass);
            System.out.println("Total Price: $" + ticketRate);
            System.out.println("----------------------------------------");
        }

        Trip trip = new Trip(reservations);
        trip=tripDAO.insert(trip);
        return reservations;
    }

}
