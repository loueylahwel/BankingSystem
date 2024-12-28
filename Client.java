public class Client extends Perrson{
    String address,mail,password;
    public Client(String first_name,String last_name,String adress,String password,String mail,int phone,Date date,int cin){
        super(first_name, last_name, cin, phone, date);
        this.address = adress;
        this.mail = mail;
        this.password = password;
    }
}