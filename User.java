public abstract class User {

    //Ιδιωτικές και σταθερές μεταβλητές
    private final String name;
    private final String phone;

    //constructor
    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    //Μέθοδος που επιστρέφει το όνομα του χρήστη
    public String getName() {
        return name;
    }

    //Μέθοδος που επιστρέφει το τηλέφωνο του χρήστη
    public String getPhone() {
        return phone;
    }

    /*Μέθοδος που επιστρέφει κατάλληλα μορφοποιημένα
    το όνομα και τηλέφωνο του χρήστη*/
    public String getUserInfo() {
        return "Name: " + name + "\nMobile Phone: " + phone;
    }

    //Δήλωση μεθόδου
    public abstract String getDetails();

    /*Μέθοδος που επιστρέφει κατάλληλα μορφοποιημένα
    το σύνολο των διαθέσιμων πληροφοριών του χρήστη*/
    public String toString() {
        return getDetails() + "\n" + getUserInfo();
    }

}



