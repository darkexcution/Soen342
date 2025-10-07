import java.util.ArrayList;
import java.util.List;

public class SearchConnection {
    public static List<TrainConnection> searchConnection(List<TrainConnection> connections,
                                               String departureCity,
                                               String arrivalCity,
                                               String departureTime,
                                               String arrivalTime,
                                               String trainType,
                                               String daysOfOperation,
                                               Double firstClassTicketPrice,
                                               Double secondClassTicketPrice) {
        List<TrainConnection> results = new ArrayList<>();
        for (TrainConnection tc : connections) {
            boolean matchFound = true;

            if (departureCity != null && !tc.getDepartureCity().equalsIgnoreCase(departureCity)) matchFound = false;
            if (arrivalCity != null && !tc.getArrivalCity().equalsIgnoreCase(arrivalCity)) matchFound = false;
            if (departureTime != null && !tc.getDepartureTime().equals(departureTime)) matchFound = false;
            if (arrivalTime != null && !tc.getArrivalTime().equals(arrivalTime)) matchFound = false;
            if (trainType != null && !tc.getTrainType().equalsIgnoreCase(trainType)) matchFound = false;
            if (daysOfOperation != null && !tc.getDaysOfOperation().equalsIgnoreCase(daysOfOperation)) matchFound = false;
            if (firstClassTicketPrice != null && tc.getFirstClassTicketRate() != firstClassTicketPrice) matchFound = false;
            if (secondClassTicketPrice != null && tc.getSecondClassTicketRate() != secondClassTicketPrice) matchFound = false;

            if (matchFound) results.add(tc);
        }

        return results;
    }
}
