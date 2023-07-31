import java.util.ArrayList;

public class RequestDonationList {

    //Λίστα που περιέχει αντικέιμενα τύπου RequestDonation
    ArrayList<RequestDonation> rdEntities = new ArrayList<>();

    //Μέθοδος η οποία τοποθετεί ένα συγκεκριμένο requestDonation στην rdEntities
    public void add(RequestDonation requestDonation) {
        boolean search = false;

        if (rdEntities.size() == 0) {       //Αν το μέγεθος της λίστας
            rdEntities.add(requestDonation);//είναι 0 προσθέτει το RequestDonation
        } else {
            for (var v : rdEntities) { //Έλεγχος για το αν το RequestDonation υπάρχει στην λίστα
                if (v.getEntity().getId() == requestDonation.getEntity().getId()) {
                    v.setQuantity(v.getQuantity() + requestDonation.getQuantity());
                    search = true;  //Προσθέτει την επιπλέον ποσότητα
                }
            }
            if (!search) { //Αν δεν βρεθεί το αντικέιμενο,
                           //προσθέτει στην λίστα το RequestDonation
                rdEntities.add(requestDonation);
            }
        }
    }

    /*Μέθοδος που εμφανίζει τα είδη που βρίσκονται στην λίστα μαζί
    με τις ποσότητες τους για έναν Donator*/
    public int monitor(Donator donator) {
        int count = 1;
        if (rdEntities.size() != 0) {
            System.out.println(donator.getName() + " offers:"); //Εμφάνιση των ειδών
            for (var v : rdEntities) {                          //με κατάλληλη μορφωποίηση
                System.out.println(count + "." + v.getEntity().getName() + " [" + v.getQuantity() + "]");
                count++;
            }
        } else { //Εμφάνιση μηνύματος εάν η λίστα έχει μέγεθος 0
            System.out.println("You are not offering something");
        }
        return count; //Επιστρέφει τον αριθμό των ειδών που περιέχει η λίστα
    }

    /*Μέθοδος που εμφανίζει τα είδη που βρίσκονται στην λίστα μαζί
    με τις ποσότητες τους για έναν Beneficiary*/
    public int monitor(Beneficiary beneficiary) {
        int count = 1;
        if (rdEntities.size() != 0) {
            System.out.println(beneficiary.getName() + " requests:"); //Εμφάνιση των ειδών με
            for (var v : rdEntities) {                                //κατάλληλη μορφωποίηση
                System.out.println(count + "." + v.getEntity().getName() + " [" + v.getQuantity() + "]");
                count++;
            }
        } else { //Εμφάνιση μηνύματος εάν η λίστα έχει μέγεθος 0
            System.out.println("You are not requesting something");
        }
        return count; //Επιστρέφει τον αριθμό των ειδών που περιέχει η λίστα
    }

    /*Μέθοδος που επιστρέφει ένα RequestDonation
    ανάλογα με την επιλογή του χρήστη*/
    public RequestDonation get(int input) {
        return rdEntities.get(input - 1); //Η λίστα ξεκινάει απο την θέση 0,
    }                                     //επιστροφή του RequestDonation

    //Μέθοδος που διαγράφει ένα RequestDonation από την λίστα
    public void remove(RequestDonation requestDonation) {
        rdEntities.remove(requestDonation);
    }

    /*Μέθοδος που τροποποιεί την ποσότητα ενός
    RequestDonation που βρίσκεται στην λίστα*/
    public void modify(int select, int quantity) {        //Επιλέγει ένα RequestDonation από την λίστα
        rdEntities.get(select - 1).setQuantity(quantity); //και καλεί μέθοδο ώστε να θέσει την καινούργια ποσότητα
    }

    //Μέθοδος που καθαρίζει την λίστα
    public void reset() {
        rdEntities.clear();
    }

}
