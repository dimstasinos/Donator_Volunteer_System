public class Offers extends RequestDonationList {

    /*Μέθοδος που Ενημερώνει τα currentDonations του οργανισμού
    με τις προσφορές που περιέχονται στη λίστα rdEntities*/
    public void commit(Organization organization) {
        boolean search;


        if (rdEntities.size() != 0) {
            if (organization.currentDonations.rdEntities.size() == 0) {
                organization.currentDonations.rdEntities.addAll(rdEntities);
            } else {
                for (var v : rdEntities) {
                    search = false;
                    for (var t : organization.currentDonations.rdEntities) {  //Έλεγχος για το αν υπάρχει στην currentDonation
                        if (v.getEntity().getId() == t.getEntity().getId()) { //το RequestDonation

                            t.setQuantity(t.getQuantity() + v.getQuantity()); //Ανάθεση νέας τιμής στην ποσότητα
                            search = true;
                        }
                    }
                    if (!search) { //Εάν δεν υπάρχει προσθέτει το RequestDonation
                        organization.currentDonations.rdEntities.add(v);
                    }
                }
            }
            rdEntities.clear(); //Καθαρισμός λίστας και εμφάνιση μηνύματος ότι η ενέργεια ολοκληρώθηκε
            System.out.println("Your offers have been delivered");
            System.out.println("----------------------------");
        } else{ //Εμφάνιση μηνύματος εάν η λίστα έχει μέγεθος 0
            System.out.println("You have no offers to deliver");
            System.out.println("----------------------------");
        }

    }

}
