

public class TrainConnection {

    private String routeID;
    private String departureCity;
    private String arrivalCity;
    private String departureTime; //Not sure which type to use for time
    private String arrivalTime;
    private String trainType;
    private String daysOfOperation;
    private double firstClassTicketRate;
    private double secondClassTicketRate;

    public TrainConnection(String routeID, String departureCity, String arrivalCity, String departureTime, String arrivalTime, String trainType, String daysOfOperation, double firstClassTicketRate, double secondClassTicketRate) {
        this.routeID=routeID;
        this.departureCity=departureCity;
        this.arrivalCity=arrivalCity;
        this.departureTime=departureTime;
        this.arrivalTime=arrivalTime;
        this.trainType=trainType;
        this.daysOfOperation=daysOfOperation;
        this.firstClassTicketRate=firstClassTicketRate;
        this.secondClassTicketRate=secondClassTicketRate;
    }

    //Show all info on connection
    public String toString() {
        return (routeID+", "+departureCity+", "+arrivalCity+", "+departureTime+", "+arrivalTime+", "+trainType+", "+daysOfOperation+", "+firstClassTicketRate+", "+secondClassTicketRate);
    }

    public String getRouteID() {
        return routeID;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getDaysOfOperation() {
        return daysOfOperation;
    }

    public double getFirstClassTicketRate() {
        return firstClassTicketRate;
    }

    public double getSecondClassTicketRate() {
        return secondClassTicketRate;
    }
 }
