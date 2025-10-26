package model;

import java.util.List;

public class ConnectionSorter {
    public static void sortByDuration(List<TrainConnection> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getDurationMinutes() > list.get(j + 1).getDurationMinutes()) {
                    TrainConnection temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }


    public static void sortByFirstClassPrice(List<TrainConnection> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getFirstClassTicketRate() > list.get(j + 1).getFirstClassTicketRate()) {
                    TrainConnection temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }


    public static void sortBySecondClassPrice(List<TrainConnection> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getSecondClassTicketRate() > list.get(j + 1).getSecondClassTicketRate()) {
                    TrainConnection temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}
