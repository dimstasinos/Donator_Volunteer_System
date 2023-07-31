public class Donator extends User {

    /*Δημιουργία αντικειμένου της Offers
    που αναπαραστά τα είδη που θέλει
    να δωρήσει ο Donator*/
    Offers offersList= new Offers();

    //constructor
    public Donator(String name, String phone) {
        super(name, phone);
    }

    //Ηλοποίηση της μεθόδου
    public String getDetails(){
        return "Donator";
    }
}