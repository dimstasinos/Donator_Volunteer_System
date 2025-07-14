public class Admin extends User{

    //Ιδιωτική και σταθερή μεταβλητή
    private final boolean isAdmin;

    //constructor
    public Admin(String name, String phone) {
        super(name, phone);
        isAdmin=true;
    }

    /*Μέθοδος που επιστρέφει
    την τιμή της μεταβλητής isAdmin*/
    public boolean isAdmin() {
        return isAdmin;
    }

    //Yλοποίηση της μεθόδου
    public String getDetails(){
        return "Admin";
    }

}