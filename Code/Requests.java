import java.util.ArrayList;

public class Requests extends RequestDonationList {

    //Μέθοδος που ελέγχει αν o Beneficiary δικαιούτε τα είδη
    public void add(RequestDonation requestDonation, Organization organization, Beneficiary beneficiary) throws CustomException {
        boolean approval_1 = false, approval_2 = false;

        //Έλεγχος αν το requestDonation είναι Material
        if (requestDonation.getEntity() instanceof Material) {
            for (var v : organization.currentDonations.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) {
                    if (requestDonation.getQuantity() <= v.getQuantity()) {
                        approval_1 = true;    //Πρώτος έλεγχος για το αν η ποσότητα που
                    }                         //ζητάει o Beneficiary είναι διαθέσιμη στον οργανισμό

                }
            }
            if (!approval_1) { //Εάν αποτύχει ο πρώτος έλεγχος προκύπτει
                // εξαίρεση που εμφανίζει κατάλληλο μήνυμα
                throw new CustomException("The quantity you are requesting is not available");
            }

            //Δεύτερος έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
            try {
                validRequestDonation(organization, beneficiary, requestDonation);
            } catch (CustomException e) {
                System.out.println(e.getMessage()); //Διαχείρηση της εξαίρεσης που μπορεί να προκύψει
                System.out.println("----------------------------");
            }
            add(requestDonation); //Εάν περάσει και τους δύο ελέγχους καλείτε η add της RequestDonationList
        } else { //Εάν το requestDonation είναι Service
            for (var v : organization.currentDonations.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) {
                    if (requestDonation.getQuantity() <= v.getQuantity()) { //Έλεγχος για το αν η ποσότητα που
                        add(requestDonation);                               //ζητάει ο Beneficiary είναι
                        approval_2 = true;                                  //διαθέσιμη στον οργανισμό
                    }
                }
            }
            if (!approval_2) { //Εάν αποτύχει ο έλεγχος προκύπτει εξαίρεση που εμφανίζει κατάλληλο μήνυμα
                throw new CustomException("The quantity you are requesting is not available\nPlease enter another one");
            }
        }
    }

    //Μέθοδος που ελέγχει αν ο Beneficiary δικαιούτε την νέα ποσότητα που ζητάει
    public void modify(RequestDonation requestDonation, Organization organization, Beneficiary beneficiary, int quantity) throws CustomException {
        boolean approval_1 = false, approval_3 = false, approval_2 = true;

        //Έλεγχος αν το requestDonation είναι Material
        if (requestDonation.getEntity() instanceof Material) {
            for (var v : organization.currentDonations.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) { //Πρώτος έλεγχος για το αν η ποσότητα που
                    if (quantity <= v.getQuantity()) {                              //ζητάει ο Beneficiary είναι
                        approval_1 = true;                                          //διαθέσιμη στον οργανισμό
                    }
                }
            }
            if (!approval_1) {  //Εάν αποτύχει ο πρώτος έλεγχος προκύπτει εξαίρεση που εμφανίζει κατάλληλο μήνυμα
                throw new CustomException("The quantity you are requesting is not available\nPlease enter another one");
            }

            //Δεύτερος έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
            try {
                validRequestDonation(organization, beneficiary, requestDonation, quantity);
            } catch (CustomException e) {
                approval_2 = false;
                System.out.println(e.getMessage()); //Διαχείρηση της εξαίρεσης που μπορεί να προκύψει
                System.out.println("----------------------------");
            }
            if (approval_2) {
                requestDonation.setQuantity(quantity); //Εάν περάσει και τους δύο ελέγχους καλείτε μέθοδος για να αναθέσει
            }                                          //την νέα τιμή στο quantity
        } else { //Εάν το requestDonation είναι Service
            for (var v : organization.currentDonations.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) {
                    if (quantity <= v.getQuantity()) {  //Έλεγχος για το αν η ποσότητα που
                        //ζητάει ο Beneficiary είναι διαθέσιμη στον οργανισμό
                        requestDonation.setQuantity(quantity);
                        approval_3 = true;
                    }
                }
            }
            if (!approval_3) { //Εάν αποτύχει ο έλεγχος προκύπτει εξαίρεση που εμφανίζει κατάλληλο μήνυμα
                throw new CustomException("The quantity you are requesting is not available\nPlease enter another one");
            }
        }
    }

    //Μέθοδοι που ελέγχουν αν η ποσότητα που ζητάει ο Beneficiary είναι μέσα στο επιτρεπτό όριο ανάλογα με τον αριθμό των ατόμων της οικογένειας
    //Καλέιται στην add
    public void validRequestDonation(Organization organization, Beneficiary beneficiary, RequestDonation requestDonation) throws CustomException {
        boolean approval = false, search = false;
        double level;
        Material material = organization.getMaterial(requestDonation.getEntity());

        //Έλεγχος για το μέγεθος της οικογέγειας
        if (beneficiary.getNoPersons() == 1) {
            level = material.getLevel1(); //Αποθηκεύεται το level του Material
            for (var v : beneficiary.receivedList.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                    if (v.getQuantity() + requestDonation.getQuantity() <= level) {
                        approval = true;
                        search = true;
                    }
                }
            }
            if (!search) {
                if (requestDonation.getQuantity() <= level) {
                    approval = true;
                }
            }
        } else if (beneficiary.getNoPersons() >= 2 && beneficiary.getNoPersons() <= 4) {
            level = material.getLevel2(); //Αποθηκεύεται το level του Material
            for (var v : beneficiary.receivedList.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                    if (v.getQuantity() + requestDonation.getQuantity() <= level) {
                        approval = true;
                        search = true;
                    }
                }
            }
            if (!search) {
                if (requestDonation.getQuantity() <= level) {
                    approval = true;
                }
            }
        } else if (beneficiary.getNoPersons() >= 5) {
            level = material.getLevel3(); //Αποθηκεύεται το level του Material
            for (var v : beneficiary.receivedList.rdEntities) {
                if (requestDonation.getEntity().getId() == v.getEntity().getId()) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                    if (v.getQuantity() + requestDonation.getQuantity() <= level) {
                        approval = true;
                        search = true;
                    }
                }
            }
            if (!search) {
                if (requestDonation.getQuantity() <= level) {
                    approval = true;
                }
            }
        }
        if (!approval) { //Προκύπτει εξαίρεση με κατάλληλο μήνυμα εάν δεν δικαιούτε την ποσότητα
            throw new CustomException("You are no valid for this quantity");
        }

    }

    //Καλείται στην modify
    public void validRequestDonation(Organization organization, Beneficiary beneficiary, RequestDonation requestDonation, int quantity) throws CustomException {
        boolean approval = false;
        double level;
        Material material = organization.getMaterial(requestDonation.getEntity());

        //Έλεγχος για το μέγεθος της οικογέγειας
        if (beneficiary.getNoPersons() == 1) {
            level = material.getLevel1(); //Αποθηκεύεται το level του Material
            if (quantity <= level) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                approval = true;

            }
        } else if (beneficiary.getNoPersons() >= 2 && beneficiary.getNoPersons() <= 4) {
            level = material.getLevel2(); //Αποθηκεύεται το level του Material
            if (quantity <= level) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                approval = true;
            }
        } else if (beneficiary.getNoPersons() >= 5) {
            level = material.getLevel3(); //Αποθηκεύεται το level του Material
            if (quantity <= level) { //Έλεγχος για το αν δικαιούτε την ποσότητα που ζητάει ο Beneficiary
                approval = true;

            }
        }
        if (!approval) { //Προκύπτει εξαίρεση με κατάλληλο μήνυμα εάν δεν δικαιούτε την ποσότητα
            throw new CustomException("You are no valid for this quantity");
        }

    }

    //Μέθοδος που ενημερώνει τα currentDonations του οργανισμού ανάλογα με τα είδη που ζητάει ο Beneficiary
    public void commit(Beneficiary beneficiary, Organization organization) {
        boolean approval_1, approval_2;
        ArrayList<RequestDonation> approved = new ArrayList<>();

        //Ελέγχει αν ο Beneficiary δικαούτε την ποσότητα που ζητάει
        if (beneficiary.requestsList.rdEntities.size() != 0) {
            for (var v : beneficiary.requestsList.rdEntities) {
                approval_1 = false;
                approval_2 = true;
                if (v.getEntity() instanceof Material) {
                    for (var t : organization.currentDonations.rdEntities) {
                        if (v.getEntity().getId() == t.getEntity().getId()) {
                            try {
                                if (v.getQuantity() <= t.getQuantity()) {
                                    approval_1 = true;
                                }
                                validRequestDonation(organization, beneficiary, v);
                            } catch (CustomException e) {
                                approval_2 = false;
                            }
                            if (approval_1 && approval_2) {         //Εάν περάσει τους ελέγχους εμφανίζει κατάλληλο μήνυμα
                                beneficiary.receivedList.add(v);    //και αφαιρεί την ποσότητα από τον οργανισμό
                                approved.add(v);
                                t.setQuantity(t.getQuantity() - v.getQuantity());
                                System.out.println("-> " + v.getEntity().getName() + " (Request approved) \u2714\n");
                            }
                        }
                    }
                } else {
                    for (var k : organization.currentDonations.rdEntities) {
                        if (v.getEntity().getId() == k.getEntity().getId()) {
                            if (v.getQuantity() <= k.getQuantity()) {
                                beneficiary.receivedList.add(v);
                                k.setQuantity(k.getQuantity() - v.getQuantity()); //Εάν περάσει τους ελέγχουσ εμφανίζει κατάλληλο μήνυμα
                                approved.add(v);                                  //και αφαιρεί την ποσότητα από τον οργανισμό
                                System.out.println("-> " + v.getEntity().getName() + " (Request approved) \u2714\n");
                                approval_1 = true;
                                approval_2 = true;
                            }
                        }
                    }
                }
                //Εμφανίζει κατάλληλο μήνυμα ανάλογα με τον έλεγχο που απέτυχε
                if (!approval_1 && approval_2) {
                    System.out.println("-> " + v.getEntity().getName() + " (Request rejected) X\nThe quantity you are requesting is not available\n");
                } else if (approval_1 && !approval_2) {
                    System.out.println("-> " + v.getEntity().getName() + " (Request rejected) X\nYou are no valid for this quantity\n");
                } else if (!approval_1) {
                    System.out.println("-> " + v.getEntity().getName() + " (Request rejected) X\nThe quantity you are requesting is not available\nYou are no valid for this quantity\n");
                }
            }
        } else { //Εμφανίζει μήνυμα εάν δεν ζητάει τίποτα
            System.out.println("You have no request to commit");
        }

        for (var v : approved) {
            beneficiary.requestsList.rdEntities.remove(v); //Αφαίρεση όσον Requests πραγματοποιήθηκαν με επιτυχία
        }

        approved.clear();
        System.out.println("----------------------------");
    }

}


