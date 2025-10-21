
public class Client {

    private String name;
    //private String lastname; //Not sure if first and last name are seperated
    private int age;
    private int id;

    public Client (String name, int age, int id) {
        this.name = name;
        this.age=age;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getID() {
        return id;
    }
}
