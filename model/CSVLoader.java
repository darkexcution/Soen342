package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {

    private List<TrainConnection> connectionList;

    //Constructor that will start loading the CSV file into a list
    public CSVLoader() {
        connectionList = new ArrayList<>();
        loaderCSV("eu_rail_network.csv");
    }

    //Load the CSV and add model.TrainConnection to the list
    public void loaderCSV(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line=br.readLine();
            if (line.equalsIgnoreCase("Route ID,Departure City,Arrival City,Departure Time,Arrival Time,Train Type,Days of Operation,First Class ticket rate (in euro),Second Class ticket rate (in euro)")) {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    TrainConnection connection=new TrainConnection(values[0], values[1], values[2], values[3], values[4], values[5], values[6], Double.parseDouble(values[7]), Double.parseDouble(values[8]));
                    connectionList.add(connection);
                }
            }
            else {
                System.out.println("Column does not match");
            }
        } catch(IOException e) {
            System.err.println("File not found");
        }
    }

    //Display all connections in the list
    public void displayAll() {
        for(int i=0; i<connectionList.size();i++) {
            TrainConnection connection=connectionList.get(i);
            System.out.println(connection.toString());
        }
    }

    //Return the list for other uses
    public List<TrainConnection> getConnectionList() {
        return connectionList;
    }
}