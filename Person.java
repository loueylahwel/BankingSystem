public abstract class Person {
    String first_name, last_name;
    int phone;
    Date birth_date;
    int cin;
    // Constructor
    public Person(String first_name, String last_name,int cin,int phone, Date date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.cin=cin;
        this.phone = phone;
        this.birth_date = date;
    }
}
