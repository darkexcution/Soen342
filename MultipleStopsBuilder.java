import java.util.ArrayList;
import java.util.List;

public class MultipleStopsBuilder  {
    
    public static List <MultipleStopsMetrics> buildTrips (List <TrainConnection> connections, String departure, String arrival){
        List <MultipleStopsMetrics> trips = new ArrayList<>();

        //logic for direct connection
        for(TrainConnection conn: connections){
            if(conn.getDepartureCity().equalsIgnoreCase(departure)&&conn.getArrivalCity().equalsIgnoreCase(arrival)){
                MultipleStopsMetrics t = new MultipleStopsMetrics();
                t.addConnection(conn);
                trips.add(t);
            }
        }

        if (!trips.isEmpty()) {
            return trips;
        }

        //logic for a->b->c connection
        for (TrainConnection conn1: connections){
            if(conn1.getDepartureCity().equalsIgnoreCase(departure)){
                for (TrainConnection conn2: connections){
                    if(conn2.getArrivalCity().equalsIgnoreCase(arrival)){
                        if(conn1.getArrivalCity().equalsIgnoreCase(conn2.getDepartureCity())){
                            MultipleStopsMetrics t = new MultipleStopsMetrics();
                            t.addConnection(conn1);
                            t.addConnection(conn2);
                            trips.add(t);
                        }
                    }
                }

            }
        }

        //logic for a->b->c->d connection
        for( TrainConnection conn1: connections){
            if(conn1.getDepartureCity().equalsIgnoreCase(departure)){
                for(TrainConnection conn3 : connections){
                    if(conn3.getArrivalCity().equalsIgnoreCase(arrival)){
                        for(TrainConnection conn2: connections){
                            if(conn2.getDepartureCity().equalsIgnoreCase(conn1.getArrivalCity())&& conn2.getArrivalCity().equalsIgnoreCase(conn3.getDepartureCity())){
                                MultipleStopsMetrics t = new MultipleStopsMetrics();
                                t.addConnection(conn1);
                                t.addConnection(conn2);
                                t.addConnection(conn3);
                                trips.add(t);
                            }
                        }
                    }
                }
            }
        }
        return trips;
    }

    
}
