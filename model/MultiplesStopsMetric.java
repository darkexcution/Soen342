package model;

import java.util.ArrayList;

public class MultiplesStopsMetric {

    ArrayList<TrainConnection> connections;


    public MultiplesStopsMetric() {
        this.connections = new ArrayList<>();
    }

    public void addConnection(TrainConnection conn) {
        connections.add(conn);
    }

    public ArrayList<TrainConnection> getConnections() {
        return connections;
    }

    public String getTotalDuration() {
        int total = 0;
        for (int i = 0; i < connections.size(); i++) {
            TrainConnection c = connections.get(i);

            String time = c.getDuration();
            String[] timeParts = time.split(":");

            int hours = Integer.parseInt(timeParts[0].trim());
            int minutes = Integer.parseInt(timeParts[1].trim());
            int times = hours * 60 + minutes;
            total += times;

        }
        String hour = Integer.toString(total / 60); // If same format
        String minute = Integer.toString(total % 60);
        if (total % 60 < 10) {
            return (hour + ":0" + minute);
        } else {
            return (hour + ":" + minute);
        }
    }

    public int getDurationMinutes() {
        int total = 0;
        String time = getTotalDuration();
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0].trim());
        int minutes = Integer.parseInt(timeParts[1].trim());
        int times = hours * 60 + minutes;
        total += times;
        return total;
    }

    public String getLayover() {

        int total = 0;
        for (int i = 0; i < connections.size(); i++) {
            TrainConnection c = connections.get(i);
            if (i < connections.size() - 1) {
                TrainConnection next = connections.get(i + 1);

                int arrival = Integer.parseInt(c.getArrivalTime().substring(0, 2)) * 60 + Integer.parseInt(c.getArrivalTime().substring(3, 5));
                int departure = Integer.parseInt(next.getDepartureTime().substring(0, 2)) * 60 + Integer.parseInt(c.getDepartureTime().substring(3, 5));

                if (arrival < departure) {
                    arrival += 1440;
                }
                int layover = arrival - departure;

                total += layover;
            }

        }


        String hour = Integer.toString(total / 60); // If same format
        String minute = Integer.toString(total % 60);
        if (total % 60 < 10) {
            return (hour + ":0" + minute);
        } else {
            return (hour + ":" + minute);
        }

    }

    public String getTotalTime() {

        String[] durationParts = getTotalDuration().split(":");
        int durationMinutes = Integer.parseInt(durationParts[0]) * 60 + Integer.parseInt(durationParts[1]);


        String[] layoverParts = getLayover().split(":");
        int layoverMinutes = Integer.parseInt(layoverParts[0]) * 60 + Integer.parseInt(layoverParts[1]);

        int totalMinutes = durationMinutes + layoverMinutes;

        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        if (totalMinutes % 60 < 10) {
            return (hour + ":0" + minute);
        } else {
            return (hour + ":" + minute);
        }
    }


    public double getTotalPrice(String classType) {
        double total = 0.0;
        for (TrainConnection conn : connections) {
            if ("first".equalsIgnoreCase(classType)) {
                total += conn.getFirstClassTicketRate();
            } else
                total += conn.getSecondClassTicketRate();
        }

        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TrainConnection c : connections) {
            sb.append(c.getRouteID()).append("-->");
        }
        return sb.toString();
    }

}