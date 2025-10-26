package model;

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

    public String getDuration() {
        int departure = Integer.parseInt(departureTime.substring(0, 2)) * 60 + Integer.parseInt(departureTime.substring(3, 5));
        int arrival = Integer.parseInt(arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(arrivalTime.substring(3, 5));
        int duration=0;

        if (arrivalTime.substring(5).equals(" (+1d)")) {
            arrival=arrival+1440;
        }
        
        duration=arrival-departure;
        String hour = Integer.toString(duration/60); // If same format
        String minute = Integer.toString(duration%60); 
        if (duration%60<10) {
            return (hour+":0"+minute);
        }
        else {
            return (hour+":"+minute);
        }
    }

    // Returns minutes in integer
    public int getDurationMinutes() {
        String durationStr = getDuration();
        String[] timeParts = durationStr.split(":");

        int hours = Integer.parseInt(timeParts[0].trim());
        int minutes = Integer.parseInt(timeParts[1].trim());

        return hours * 60 + minutes;
    }


    //Show all info on connection
    public String toString() {
        return (routeID+", "+departureCity+", "+arrivalCity+", "+departureTime+", "+arrivalTime+", "+trainType+", "+daysOfOperation+", "+firstClassTicketRate+", "+secondClassTicketRate);
    }

    public String toFormattedString() {
        return "Route " + routeID + ": " +
                departureCity + " → " + arrivalCity +
                " | " + departureTime + " → " + arrivalTime +
                " | " + trainType +
                " | Days: " + daysOfOperation +
                " | 1st Class: $" + firstClassTicketRate +
                " | 2nd Class: $" + secondClassTicketRate;
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
