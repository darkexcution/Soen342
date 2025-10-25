
public class Client {

    private String firstName;
    private String lastName;
    private int age;
    private int id;

    public Client (String firstName, String lastName, int age, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age=age;
        this.id=id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
