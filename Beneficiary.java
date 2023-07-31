public class Beneficiary extends User{

    //Ιδιωτική μεταβλητή
    private int noPersons=1;

    /*Δημιουργία αντικειμένου της RequestDonationList που αναπαραστά τα
    είδη που έχει παραλάβει ο Beneficiary*/
    RequestDonationList receivedList = new RequestDonationList();

    /*Δημιουργία αντικειμένου της Requests που αναπαραστά
    τα είδη που θέλει να παραλάβει ο Beneficiary*/
    Requests requestsList = new Requests();

    //constructor
    public Beneficiary(String name, String phone, int noPersons) {
        super(name, phone);
        this.noPersons = noPersons;
    }

    /*Μέθοδος που επιστρέφει
    την τιμή της μεταβλητής noPersons*/
    public int getNoPersons() {
        return noPersons;
    }

    //Ηλοποίηση της μεθόδου
    public String getDetails(){
        return "Beneficiary";
    }
}