package model;

import java.util.ArrayList;
import java.util.List;

public class SearchConnection {

    // Searches for train connections between two cities with the parameters set by the user.
    // returns a list of model.TrainConnection objects that match the criteria
    public static List<MultipleStopsMetrics> searchConnection(List<TrainConnection> connections,
                                                         String departureCity,
                                                         String arrivalCity,
                                                         String departureTime,
                                                         String arrivalTime,
                                                         String trainType,
                                                         String daysOfOperation,
                                                         Double firstClassTicketPrice,
                                                         Double secondClassTicketPrice) {
        List<MultipleStopsMetrics> results = new ArrayList<>();
        for (TrainConnection connection : connections) {
            boolean matchFound = true;

            if (departureCity != null && !connection.getDepartureCity().equalsIgnoreCase(departureCity)) matchFound = false;
            if (arrivalCity != null && !connection.getArrivalCity().equalsIgnoreCase(arrivalCity)) matchFound = false;
            if (departureTime != null && !connection.getDepartureTime().equals(departureTime)) matchFound = false;
            if (arrivalTime != null && !connection.getArrivalTime().equals(arrivalTime)) matchFound = false;
            if (trainType != null && !connection.getTrainType().equalsIgnoreCase(trainType)) matchFound = false;
            if (daysOfOperation != null && !connection.getDaysOfOperation().equalsIgnoreCase(daysOfOperation)) matchFound = false;
            if (firstClassTicketPrice != null && connection.getFirstClassTicketRate() != firstClassTicketPrice) matchFound = false;
            if (secondClassTicketPrice != null && connection.getSecondClassTicketRate() != secondClassTicketPrice) matchFound = false;

            if (matchFound) {
                MultipleStopsMetrics result = new MultipleStopsMetrics();
                result.addConnection(connection);
                results.add(result);
            }
        }
        
        return results;
    }
}
