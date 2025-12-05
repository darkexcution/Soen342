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

            Client existingClient = clientDAO.getClientByNameAndAge(firstName, lastName, age);

            if (existingClient != null) {
                System.out.println("Existing client found: " +
                        existingClient.getFirstName() + " " +
                        existingClient.getLastName() + " (ID: " +
                        existingClient.getId() + ")");
                clients[i] = existingClient;
            } else {
                // Create new client and insert into DB
                Client newClient = new Client(firstName, lastName, age, 0);
                newClient = clientDAO.insert(newClient);
                System.out.println("New client added: " + newClient.getFirstName() + " " +
                        newClient.getLastName() + " (Assigned ID: " + newClient.getId() + ")");
                clients[i] = newClient;
            }

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


            // Check if the client already exists in the database
            Client existingClient = clientDAO.getClientByNameAndAge(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getAge()
            );

            if (existingClient != null) {
                client = existingClient;
                System.out.println("\nExisting client found: " +
                        client.getFirstName() + " " + client.getLastName() +
                        " (ID: " + client.getId() + ")");
            } else {
                // If not found, insert a new client
                client = clientDAO.insert(client);
                System.out.println("\nNew client added: " +
                        client.getFirstName() + " " + client.getLastName() +
                        " (Assigned ID: " + client.getId() + ")");
            }

            Ticket ticket = new Ticket(ticketRate, travelClass);

            Reservation reservation = new Reservation(client, new ArrayList<>(chosenConnections), ticket);
            reservation = reservationDAO.insert(reservation);

            ticket.setReservationId(reservation.getReservationId());
            ticket = ticketDAO.insert(ticket);

            reservation.setTicket(ticket);

            reservations.add(reservation);

            System.out.println("\n Ticket for " + client.getFirstName() + " " + client.getLastName());
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
