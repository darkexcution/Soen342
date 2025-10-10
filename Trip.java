import java.util.ArrayList;
import java.util.List;

public class Trip {

    List <TrainConnection> connections;
    

    public Trip(){
        this.connections = new ArrayList<>();
    }

    public void addConnection(TrainConnection conn){
        connections.add(conn);
    }
    public List<TrainConnection> getConnections() {
        return connections;
    }

    public String getTotalDuration(){
        int total=0;
        for(int i =0; i<connections.size(); i++){
            TrainConnection c =connections.get(i);
            
            String time=c.getDuration();
            String[] timeParts = time.split(":");

            int hours = Integer.parseInt(timeParts[0].trim());
            int minutes = Integer.parseInt(timeParts[1].trim());
            int times = hours*60 + minutes;
             total+=times;

            if(i<connections.size()-1){
                TrainConnection next =connections.get(i+1);
                
                int arrival = Integer.parseInt(c.getArrivalTime().substring(0, 2)) * 60 + Integer.parseInt(c.getArrivalTime().substring(3, 5));
                int departure = Integer.parseInt(next.getDepartureTime().substring(0, 2)) * 60 + Integer.parseInt(c.getDepartureTime().substring(3, 5));
                
                if(arrival>departure){
                    arrival += 1440;
                }
                int layover= arrival-departure;

                total+=layover;
            }
        
        }
        String hour = Integer.toString(total/60); // If same format
        String minute = Integer.toString(total%60); 
        return (hour+":"+minute);
    }

    

    public double getTotalPrice(String classType){
        double total=0.0;
        for(TrainConnection conn : connections){
            if("first".equalsIgnoreCase(classType)){
                total += conn.getFirstClassTicketRate();
            }else
            total += conn.getSecondClassTicketRate();
        }
        
        return total;
    }

    public String toString(){
        StringBuilder sb= new StringBuilder();
        for(TrainConnection c: connections){
            sb.append(c.getRouteID()).append("-->");
        }
        return sb.toString();
    }
    
}