
import java.util.ArrayList;
import java.util.List;

public class Main {

    //Test
    public static void main(String[] args) {
        CSVLoader load = new CSVLoader();
        load.displayAll(); //Show all connections

        List<TrainConnection> list = new ArrayList<>();
        list=load.getConnectionList(); //Get the list
        TrainConnection connection = list.get(11);
        System.out.println(connection.toString()); //Show info connection
        System.out.println(connection.getDuration()); //Show duration
    }

}
