package console;

import model.*;

import java.util.*;

public class MainMenuUI {

    public static class SearchFilter {
        public String depCity;
        public String arrCity;
        public String depTime;
        public String arrTime;
        public String trainType;
        public String daysOfOp;
        public Double firstPrice;
        public Double secondPrice;
    }

    public static int displayMainMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Welcome to the Train Route Search and Scheduling System!");
        System.out.println("----------------------------------------------------------");
        System.out.println("\n");
        System.out.println("Main Menu");
        System.out.println("----------------------------------------------------------");

        System.out.println("1. Search Train Routes");
        System.out.println("2. View your Trips");
        System.out.println("3. Exit");
        System.out.println("----------------------------------------------------------");
        System.out.println("\n");

        System.out.print("Please enter your choice (1, 2 or 3): ");
        int mainMenuChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        return mainMenuChoice;
    }

    public static SearchFilter getSearchFilters() {

        Scanner scanner = new Scanner(System.in);
        SearchFilter filter = new SearchFilter();

        System.out.println("Please enter your choice of filters or press \"Enter\" if you don't want to filter results:");
        System.out.println("----------------------------------------------------------");

        System.out.print("Departure City: ");
        filter.depCity = scanner.nextLine();
        filter.depCity = filter.depCity.isEmpty() ? null : filter.depCity;

        System.out.print("Arrival City: ");
        filter.arrCity = scanner.nextLine();
        filter.arrCity = filter.arrCity.isEmpty() ? null : filter.arrCity;

        System.out.print("Departure Time (HH:MM): ");
        filter.depTime = scanner.nextLine();
        filter.depTime = filter.depTime.isEmpty() ? null : filter.depTime;

        System.out.print("Arrival Time (HH:MM): ");
        filter.arrTime = scanner.nextLine();
        filter.arrTime = filter.arrTime.isEmpty() ? null : filter.arrTime;

        System.out.print("Train Type: ");
        filter.trainType = scanner.nextLine();
        filter.trainType = filter.trainType.isEmpty() ? null : filter.trainType;

        System.out.print("Days of Operation: ");
        filter.daysOfOp = scanner.nextLine();
        filter.daysOfOp = filter.daysOfOp.isEmpty() ? null : filter.daysOfOp;

        System.out.print("First Class Price: ");
        String firstPriceStr = scanner.nextLine();
        filter.firstPrice = firstPriceStr.isEmpty() ? null : Double.parseDouble(firstPriceStr);

        System.out.print("Second Class Price: ");
        String secondPriceStr = scanner.nextLine();
        filter.secondPrice = secondPriceStr.isEmpty() ? null : Double.parseDouble(secondPriceStr);

        return filter;
    }

    public static List<MultipleStopsMetric> displayTrainConnections(SearchFilter filter, List<TrainConnection> list) {

        Scanner scanner = new Scanner(System.in);

        List<MultipleStopsMetric> results = SearchConnection.searchConnection(
                list,
                filter.depCity,
                filter.arrCity,
                filter.depTime,
                filter.arrTime,
                filter.trainType,
                filter.daysOfOp,
                filter.firstPrice,
                filter.secondPrice
        );

        if (results.isEmpty()) {

            System.out.print("\nNo direct connections found. Would you like to search for available indirect connections?");
            System.out.print("\nEnter 'y' for yes or 'n' for no: ");
            String displayIndirectConnectionsChoice = scanner.nextLine();

            if (displayIndirectConnectionsChoice.equals("y") && filter.depCity != null && filter.arrCity != null) {
                MultipleStopsBuilder tripBuilder = new MultipleStopsBuilder();
                results = tripBuilder.buildTrips(list, filter.depCity, filter.arrCity);

            } else {
                System.out.println("\nThank you for visiting the Train Route Search and Scheduling System!");
            }
        }
        if (!results.isEmpty()) {
            System.out.println("\nWould you like to sort the results?");
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
                        results.sort((t1, t2) -> {
                            String[] d1 = t1.getTotalTime().split(":");
                            String[] d2 = t2.getTotalTime().split(":");
                            int minutes1 = Integer.parseInt(d1[0]) * 60 + Integer.parseInt(d1[1]);
                            int minutes2 = Integer.parseInt(d2[0]) * 60 + Integer.parseInt(d2[1]);
                            return minutes1 - minutes2;
                        });

                        break;
                    case "2":
                        results.sort((t1, t2) -> Double.compare(t1.getTotalPrice("first"), t2.getTotalPrice("first")));
                        break;
                    case "3":
                        results.sort((t1, t2) -> Double.compare(t1.getTotalPrice("second"), t2.getTotalPrice("second")));
                        break;
                    default:
                        System.out.println("Invalid choice. Showing unsorted results:");
                }

            }
            if (results.get(0).getConnections().size()==1) {
                List<TrainConnection> result = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    result.add(results.get(i).getConnections().get(0));
                }
                displayDirectConnection(result);
            }
            else {
                results = layoverPolicy(results);
                displayIndirectConnection(results);
            }
            return results;
//            System.out.println("\n======= Connections Trips =======");
//            int count = 1;
//            for (StopsMetrics t : results) {
//                System.out.println("Trip No.: " + count);
//                for (TrainConnection c : t.getConnections()) {
//                    System.out.println("  Route " + c.getRouteID() + ": "
//                            + c.getDepartureCity() + " -> " + c.getArrivalCity()
//                            + " (" + c.getDepartureTime() + " - " + c.getArrivalTime() + ")");
//                }
//                System.out.println("  Total Duration on the Train: " + t.getTotalDuration());
//                System.out.println("  Total layover: " + t.getLayover());
//                System.out.println("  Total 1st Class Price: " + t.getTotalPrice("first"));
//                System.out.println("  Total 2nd Class Price: " + t.getTotalPrice("second"));
//                System.out.println();
//                count++;
//            }
        } else {
            System.out.println("\nNo routes found (direct or connected).");
            System.out.println("\nThank you for visiting the Train Route Search and Scheduling System!");
            System.exit(0);
        }
//        } else {
//
//
//            System.out.println("\nSearch results found: " + results.size());
//            System.out.println("\n======= Train Connections =======");
//            displayResultsAsTable(results);
//
//            System.out.println("\n");
//
//            if (!results.isEmpty()) {
//                System.out.println("Would you like to sort the results?");
//                System.out.print("Enter 'y' for yes or 'n' for no: ");
//                String sortChoice = scanner.nextLine();
//
//                if (sortChoice.equalsIgnoreCase("y")) {
//                    System.out.println("Sort results by:");
//                    System.out.println("1: Duration");
//                    System.out.println("2: First Class Price");
//                    System.out.println("3: Second Class Price");
//                    System.out.print("Enter choice (1-3): ");
//                    String choice = scanner.nextLine();
//
//                    switch (choice) {
//                        case "1":
//                            ConnectionSorter.sortByDuration(results);
//                            System.out.println("\nDiaplaying results sorted by duration:");
//                            break;
//                        case "2":
//                            ConnectionSorter.sortByFirstClassPrice(results);
//                            System.out.println("\nDiaplaying results sorted by First Class Price:");
//                            break;
//                        case "3":
//                            ConnectionSorter.sortBySecondClassPrice(results);
//                            System.out.println("\nDiaplaying results sorted by Second Class Price:");
//                            break;
//                        default:
//                            System.out.println("Invalid choice. Showing unsorted results:");
//                    }
//                    System.out.println("\n======= Train Connections =======");
//                    displayResultsAsTable(results);
//
//                } else {
//                    System.out.println("\n======= Train Connections =======");
//                    displayResultsAsTable(results);
//
//                }
//            }
//        }
        return null;
    }

    public static List<MultipleStopsMetric> layoverPolicy(List<MultipleStopsMetric> results) {
        List<MultipleStopsMetric> trips = new ArrayList<>();

        for (int i = 0; i<results.size(); i++) { // Direct Connection, no layover
            if (results.get(i).getConnections().size()==1) {
                trips.add(results.get(i));
            }
            else if(results.get(i).getConnections().size()>1) { // Indirect connection
                List<TrainConnection> layoverConnection1 = results.get(i).getConnections();

                String[] time = layoverConnection1.get(0).getArrivalTime().split(":");
                int arrival1 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());
                time = results.get(i).getLayover().split(":");
                int layover1 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());

                if (arrival1>=360 && arrival1<1080) { // Between 6h and 18h
                    if (layover1<=120) { // Layover less than 2h
                        if (results.get(i).getConnections().size()==2) { // 1 Layover
                            trips.add(results.get(i));
                        }
                        else { // 2 Layover

                            time = layoverConnection1.get(1).getArrivalTime().split(":");
                            int arrival2 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());
                            time = results.get(i).getLayover().split(":");
                            int layover2 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());

                            if (arrival2>=360 && arrival2<1080) {
                                if (layover2 <= 120) {
                                    trips.add(results.get(i));
                                }
                            }
                            else {
                                if (layover2 <= 30) {
                                    trips.add(results.get(i));
                                }
                            }
                        }
                    }
                }
                else {
                    if (layover1<=30) {
                        if (results.get(i).getConnections().size()==2) {
                            trips.add(results.get(i));
                        }
                        else { // 2 Layover

                            time = layoverConnection1.get(1).getArrivalTime().split(":");
                            int arrival2 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());
                            time = results.get(i).getLayover().split(":");
                            int layover2 = Integer.parseInt(time[0].trim()) * 60 + Integer.parseInt(time[1].trim());

                            if (arrival2>=360 && arrival2<1080) {
                                if (layover2 <= 120) {
                                    trips.add(results.get(i));
                                }
                            }
                            else {
                                if (layover2 <= 30) {
                                    trips.add(results.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
        return trips;
    }

    public static void displayDirectConnection(List<TrainConnection> results) {
        System.out.println("Showing Direct Connections:");
        System.out.printf("%-8s %-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n", "Trip No.",
                "RouteID", "Departure", "Arrival", "DepTime", "ArrTime",
                "TrainType", "Days", "1st Class", "2nd Class");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int countResults = 1;
        for (TrainConnection c : results) {
            System.out.printf("%-8s %-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n",
                    countResults,
                    c.getRouteID(),
                    c.getDepartureCity(),
                    c.getArrivalCity(),
                    c.getDepartureTime(),
                    c.getArrivalTime(),
                    c.getTrainType(),
                    c.getDaysOfOperation(),
                    c.getFirstClassTicketRate(),
                    c.getSecondClassTicketRate());
            countResults++;
        }
    }

    public static void displayIndirectConnection(List<MultipleStopsMetric> results) {
        System.out.println("Showing Indirect Connections:");
        System.out.printf("%-8s %-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n", "Trip No.",
                "RouteID", "Departure", "Arrival", "DepTime", "ArrTime",
                "TrainType", "Days", "1st Class", "2nd Class");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int countResults = 1;
        for (int i = 0; i < results.size(); i++) {
            System.out.println("Trip No."+countResults);
            System.out.printf("\n%-15s %-23s %-23s %-15s %-15s %-20s %-20s %-15s %-15s%n",
                    "RouteID", "Departure", "Arrival", "DepTime", "ArrTime",
                    "TrainType", "Days", "1st Class", "2nd Class");
            ArrayList<TrainConnection> result = new ArrayList<>();
            result=results.get(i).getConnections();
            for (TrainConnection c : result) {
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
            countResults++;
            System.out.println("\nTotal Duration: " + results.get(i).getTotalTime());
            System.out.println("Total Time on the Train: " + results.get(i).getTotalDuration());
            System.out.println("Total layover: " + results.get(i).getLayover());
            System.out.println("Total 1st Class Price: " + results.get(i).getTotalPrice("first"));
            System.out.println("Total 2nd Class Price: " + results.get(i).getTotalPrice("second"));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
