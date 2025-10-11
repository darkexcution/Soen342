
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {

    //Test
    public static void main(String[] args) {
        CSVLoader load = new CSVLoader();
        //load.displayAll(); //Show all connections

        List<TrainConnection> list = new ArrayList<>();
        list = load.getConnectionList(); //Get the list
        TrainConnection connection = list.get(11);
        //System.out.println(connection.toString()); //Show info connection
        //System.out.println(connection.getDuration()); //Show duration


        Scanner scanner = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Welcome to the Train Route Search and Scheduling System!");
        System.out.println("----------------------------------------------------------");
        System.out.println("\n");
        System.out.println("Console Menu");
        System.out.println("----------------------------------------------------------");

        System.out.println("1. Search Train Routes");
        System.out.println("2. Exit");
        System.out.println("----------------------------------------------------------");
        System.out.println("\n");

        System.out.print("Please enter your choice (1 or 2): ");
        int mainMenuChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\n");

        if (mainMenuChoice == 1) {

            System.out.println("Please enter your choice of filters or press \"Enter\" if you don't want to filter results:");
            System.out.println("----------------------------------------------------------");

            System.out.print("Departure City: ");
            String depCity = scanner.nextLine();
            depCity = depCity.isEmpty() ? null : depCity;

            System.out.print("Arrival City: ");
            String arrCity = scanner.nextLine();
            arrCity = arrCity.isEmpty() ? null : arrCity;

            System.out.print("Departure Time (HH:MM): ");
            String depTime = scanner.nextLine();
            depTime = depTime.isEmpty() ? null : depTime;

            System.out.print("Arrival Time (HH:MM): ");
            String arrTime = scanner.nextLine();
            arrTime = arrTime.isEmpty() ? null : arrTime;

            System.out.print("Train Type: ");
            String trainType = scanner.nextLine();
            trainType = trainType.isEmpty() ? null : trainType;

            System.out.print("Days of Operation: ");
            String daysOfOp = scanner.nextLine();
            daysOfOp = daysOfOp.isEmpty() ? null : daysOfOp;

            System.out.print("First Class Price: ");
            String firstPriceStr = scanner.nextLine();
            Double firstPrice = firstPriceStr.isEmpty() ? null : Double.parseDouble(firstPriceStr);

            System.out.print("Second Class Price: ");
            String secondPriceStr = scanner.nextLine();
            Double secondPrice = secondPriceStr.isEmpty() ? null : Double.parseDouble(secondPriceStr);

            List<TrainConnection> results = SearchConnection.searchConnection(
                    list,
                    depCity,
                    arrCity,
                    depTime,
                    arrTime,
                    trainType,
                    daysOfOp,
                    firstPrice,
                    secondPrice
            );

            if (results.isEmpty()) {

                if ( depCity != null && arrCity != null) {
                    MultipleStopsBuilder  tripBuilder = new MultipleStopsBuilder ();
                    List<MultipleStopsMetrics> trips = tripBuilder.buildTrips(list, depCity, arrCity);

                    if (!trips.isEmpty()) {
                        System.out.println("Would you like to sort the results?");
                        System.out.print("Enter 'y' for yes or 'n' for no: ");
                        String sortChoice = scanner.nextLine();

                        if (sortChoice.equalsIgnoreCase("y")) {
                            System.out.println("Sort results by:");
                            System.out.println("1: Duration");
                            System.out.println("2: First Class Price");
                            System.out.println("3: Second Class Price");
                            System.out.print("Enter choice (1-3): ");
                            String choice = scanner.nextLine();

                            switch (choice) {
                                case "1":
                                    trips.sort((t1, t2) -> {
                                        String[] d1 = t1.getTotalDuration().split(":");
                                        String[] d2 = t2.getTotalDuration().split(":");
                                        int minutes1 = Integer.parseInt(d1[0]) * 60 + Integer.parseInt(d1[1]);
                                        int minutes2 = Integer.parseInt(d2[0]) * 60 + Integer.parseInt(d2[1]);
                                        return minutes1 - minutes2;
                                    });
                                    
                                    break;
                                case "2":
                                    trips.sort((t1, t2) -> Double.compare(t1.getTotalPrice("first"), t2.getTotalPrice("first")));
                                    break;
                                case "3":
                                    trips.sort((t1, t2) -> Double.compare(t1.getTotalPrice("second"), t2.getTotalPrice("second")));
                                    break;
                                default:
                                    System.out.println("Invalid choice. Showing unsorted results:");
                            }


                            System.out.println("\n======= Multi-Connections Trips =======");
                            int count = 1;
                            for (MultipleStopsMetrics t : trips) {
                                System.out.println("Trip " + count + ":");
                                for (TrainConnection c : t.getConnections()) {
                                    System.out.println("  " + c.getDepartureCity() + " -> " + c.getArrivalCity()
                                        + " (" + c.getDepartureTime() + " - " + c.getArrivalTime() + ")");
                                }
                                System.out.println("  Total Duration on the Train: " + t.getTotalDuration());
                                System.out.println("  Total layover: " + t.getLayover());
                                System.out.println("  Total 1st Class Price: " + t.getTotalPrice("first"));
                                System.out.println("  Total 2nd Class Price: " + t.getTotalPrice("second"));
                                System.out.println();
                                count++;
                            }
    
                        }
                        if(sortChoice.equalsIgnoreCase("n")){
                            System.out.println("\n======= Multi-Connections Trips =======");
                            int count = 1;
                            for (MultipleStopsMetrics t : trips) {
                                System.out.println("Trip " + count + ":");
                                for (TrainConnection c : t.getConnections()) {
                                    System.out.println("  " + c.getDepartureCity() + " -> " + c.getArrivalCity()
                                        + " (" + c.getDepartureTime() + " - " + c.getArrivalTime() + ")");
                                }
                                System.out.println("  Total Duration on the Train: " + t.getTotalDuration());
                                System.out.println("  Total layover: " + t.getLayover());
                                System.out.println("  Total 1st Class Price: " + t.getTotalPrice("first"));
                                System.out.println("  Total 2nd Class Price: " + t.getTotalPrice("second"));
                                System.out.println();
                                count++;
                            }
                        }
                        else {
                        System.out.println("\nNo routes found (direct or connected).");
                        return;
                        }
                    }
                }
            }
            

            System.out.println("\nSearch results found: " + results.size());
            System.out.println("\n======= Train Connections =======");
            displayResultsAsTable(results);

            System.out.println("\n");

            if (!results.isEmpty()) {
                System.out.println("Would you like to sort the results?");
                System.out.print("Enter 'y' for yes or 'n' for no: ");
                String sortChoice = scanner.nextLine();

                if (sortChoice.equalsIgnoreCase("y")) {
                    System.out.println("Sort results by:");
                    System.out.println("1: Duration");
                    System.out.println("2: First Class Price");
                    System.out.println("3: Second Class Price");
                    System.out.print("Enter choice (1-3): ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            ConnectionSorter.sortByDuration(results);
                            System.out.println("\nDiaplaying results sorted by duration:");
                            break;
                        case "2":
                            ConnectionSorter.sortByFirstClassPrice(results);
                            System.out.println("\nDiaplaying results sorted by First Class Price:");
                            break;
                        case "3":
                            ConnectionSorter.sortBySecondClassPrice(results);
                            System.out.println("\nDiaplaying results sorted by Second Class Price:");
                            break;
                        default:
                            System.out.println("Invalid choice. Showing unsorted results:");
                    }
                    System.out.println("\n======= Train Connections =======");
                    displayResultsAsTable(results);

                } else {
                    System.out.println("\n======= Train Connections =======");
                    displayResultsAsTable(results);

                }


            } else if (mainMenuChoice == 2) {
                System.exit(0);
            }
        }


    }  

    public static void displayResultsAsTable(List<TrainConnection> results) {
        System.out.printf("%-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n",
                "RouteID", "Departure", "Arrival", "DepTime", "ArrTime",
                "TrainType", "Days", "1st Class", "2nd Class");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (TrainConnection c : results) {
            System.out.printf("%-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n",
                    c.getRouteID(),
                    c.getDepartureCity(),
                    c.getArrivalCity(),
                    c.getDepartureTime(),
                    c.getArrivalTime(),
                    c.getTrainType(),
                    c.getDaysOfOperation(),
                    c.getFirstClassTicketRate(),
                    c.getSecondClassTicketRate());
        }
    }

}


