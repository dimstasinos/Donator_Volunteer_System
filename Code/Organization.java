import java.util.ArrayList;

public class Organization {

    //Ιδιωτική και σταθερή μεταβλητή
    private final String name;

    //Ιδιωτική μεταβλητή
    private Admin admin;

    /*Δημιουργία λίστας τύπου Entity που περιέχει
    τα είδη που διακινούνται στον οργανισμό*/
    ArrayList<Entity> entityList = new ArrayList<>();

    /*Δημιουργία λίστας τύπου Donator που περιέχει
    τους Donators του οργανισμού*/
    ArrayList<Donator> donatorList = new ArrayList<>();

    /*Δημιουργία λίστας τύπου Beneficiary που περιέχει
    τους Beneficiaries του οργανισμού*/
    ArrayList<Beneficiary> beneficiaryList = new ArrayList<>();
    RequestDonationList currentDonations = new RequestDonationList();

    //constructor
    public Organization(String name, Admin admin) {
        this.name = name;
        this.admin = admin;
    }

    /*Μέθοδος που επιστρέφει
    το όνομα του οργανισμού*/
    public String getName() {
        return name;
    }

    //Μέθοδος που θέτει έναν Admin στον οργανισμό
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    //Μέθοδος που επιστρέφει τον Admin του οργανισμού
    public Admin getAdmin() {
        return admin;
    }

    //Μέθοδος που προσθέτει ένα είδος στον οργανισμό
    public void addEntity(Entity entity) {
        boolean search = false;

        try { //Έλεγχος για το αν υπάρχει το συγκεκριμένο είδος στον οργανισμό
            for (var v : entityList) {
                if (v.getId() == entity.getId()) { //Εύρεση μέσω του Id
                    search = true;
                    break;
                }
            }
            if (!search) {
                entityList.add(entity); //Προσθήκη είδους εάν δεν υπάρχει αλλιώς προκύπτει εξαίρεση
            } else throw new CustomException("The " + entity.getName() + " already exists in the organization");
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που αφαιρεί είδος απο τον οργανισμό
    public void removeEntity(Entity entity, Admin admin) {
        try {
            if (admin.isAdmin()) { //Έλεγχος για το αν ο χρήστης είναι Admin αλλιώς προκύπτει εξαίρεση
                entityList.remove(entity); //Αφαίρεση είδους
            } else throw new CustomException("You do not have the necessary permissions to perform this action");
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που προσθέτει έναν Donator στον οργανισμό
    public void insertDonator(Donator donator) {
        boolean search_donator = false;
        boolean search_beneficiary = false;
        boolean search_admin = false;
        try { //Έλεγχος για το αν υπάρχει άλλος χρήστης με το ίδιο τηλέφωνο
            for (var v : donatorList) {
                if (v.getPhone().equals(donator.getPhone())) {
                    search_donator = true;
                    break;
                }
            }
            for (var t : beneficiaryList) { //Έλεγχος για το αν υπάρχει Beneficiary με το ίδιο τηλέφωνο
                if (t.getPhone().equals(donator.getPhone())) {
                    search_beneficiary = true;
                    break;
                }
            }
            if (donator.getPhone().equals(admin.getPhone())) {
                search_admin = true; //Έλεγχος του admin
            }
            if (!search_admin && !search_donator && !search_beneficiary) {
                donatorList.add(donator); //Προσθήκη του Donator εάν δεν υπάρχει αλλιώς προκύπτει εξαίρεση
            } else if (search_donator) { //Εξαιρέσεις με κατάλληλα μηνύματα
                throw new CustomException("The user already exists in the organization as Donator");
            } else if (search_beneficiary) {
                throw new CustomException("The user already exists in the organization as Beneficiary");
            } else if (search_admin) {
                throw new CustomException("The user already exists in the organization as Admin");
            }
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που αφαιρεί έναν Donator από τον οργανισμό
    public void removeDonator(Donator donator, Admin admin) {
        try {
            if (admin.isAdmin()) { //Έλεγχος για το αν ο χρήστης είναι Admin αλλιώς ποκύπτει εξαίρεση
                donatorList.remove(donator);
            } else throw new CustomException("You do not have the necessary permissions to perform this action");
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που προσθέτει έναν Beneficiary στον οργανισμό
    public void insertBeneficiary(Beneficiary beneficiary) {
        boolean search_donator = false;
        boolean search_beneficiary = false;
        boolean search_admin = false;
        try { //Έλεγχος για το αν υπάρχει άλλος Beneficiary με το ίδιο τηλέφωνο
            for (var v : beneficiaryList) {
                if (v.getPhone().equals(beneficiary.getPhone())) {
                    search_beneficiary = true;
                    break;
                }
            }
            for (var t : donatorList) { //Έλεγχος για το αν υπάρχει κάποιος Donator με το ίδιο τηλέφωνο
                if (t.getPhone().equals(beneficiary.getPhone())) {
                    search_donator = true;
                    break;
                }
            }
            if (beneficiary.getPhone().equals(admin.getPhone())) {
                search_admin = true; //Έλεγχος του Admin
            }
            if (!search_admin && !search_donator && !search_beneficiary) {
                beneficiaryList.add(beneficiary); //Προσθήκη του Beneficiary εάν δεν υπάρχει αλλιώς προκύπτει εξαίρεση
            } else if (search_donator) { //Εξαιρέσεις με κατάλληλα μηνύματα
                throw new CustomException("The user already exists in the organization as Donator");
            } else if (search_beneficiary) {
                throw new CustomException("The user already exists in the organization as Beneficiary");
            } else if (search_admin) {
                throw new CustomException("The user already exists in the organization as Admin");
            }
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που αφαιρεί έναν Beneficiary απο τον οργανισμό
    public void removeBeneficiary(Beneficiary beneficiary, Admin admin) {
        try {
            if (admin.isAdmin()) { //Έλεγχος για το αν ο χρήστης είναι Admin αλλιώς προκύπτει εξαίρεση
                beneficiaryList.remove(beneficiary);
            } else throw new CustomException("You do not have the necessary permissions to perform this action");
        } catch (CustomException e) { //Διαχείρηση εξαίρεσης
            System.out.println(e.getMessage());
        }
    }

    //Μέθοδος που εμφανίζει τα είδη του Οργανισμού με τις διαθέσιμες ποσότητες
    public int listEntities(String select) {
        int count = 1;

        //Εμφανίζει τα Materials
        if (select.equals("1")) {
            System.out.println("Materials");
            for (var v : entityList) {
                if(v instanceof Material){ //Εύρεση των Materials απο την λίστα
                    System.out.print(count + "." + v.getName() + " (");
                    System.out.println(getQuantity(v) + ")");
                    count++;
                }
            }
            //Εμφανίζει τα Services
        } else if (select.equals("2")) {
            System.out.println("Services");
            for (var v : entityList) {
                if (v instanceof Service) { //Εύρεση των Services από την λίστα
                    System.out.print(count + "." + v.getName() + " (");
                    System.out.println(getQuantity(v) + ")");
                    count++;
                }
            }
        }
        return count; //Επιστροφή της ποσότητας των ειδών που περιέχει η επιλεγμένη κατηγορία
    }

    //Μέθοδος εύρεσης ενός Material μέσα από ένα Entity
    public Material getMaterial(Entity entity) {
        Material material = null;

        for (var v : entityList) { //Εύρεση του entity
            if (entity.getId() == v.getId()) {
                material = (Material) v; //Αποθήκευση του Entity στο Material
            }
        }

        return material; //Επιστρέφει Material
    }

    //Μέθοδος εύρεσης ενός Entity μέσα απο τις επιλογές του χρήστη
    public Entity getEntity(String select, int choice) {
        Entity entity = null;
        int count = 1;

        //Εύρεση του Entity εάν αυτό ειναι τύπου Material
        if (select.equals("1")) {
            for (var v : entityList) {
                if (v instanceof Material) { //Εύρεση των Materials από την λίστα
                    if (choice == count) {
                        entity = v; //Αποθήκευση του Entity
                        break;
                    }
                    count++;
                }
            }
            //Εύρεση του Entity εάν αυτό είναι τύπου Service
        } else if (select.equals("2")) {
            for (var v : entityList) {
                if (v instanceof Service) { //Εύρεση των Services από την λίστα
                    if (choice == count) {
                        entity = v; //Αποθήκευση του Entity
                        break;
                    }
                    count++;
                }
            }
        }

        return entity; //Επιστροφή του Entity
    }

    //Μέθοδος εύρεσης της διαθέσιμης ποσότητας ενός Entity στον οργανισμό
    public double getQuantity(Entity entity) {
        double quantity = 0;
        boolean search = false;

        if (currentDonations.rdEntities.size() == 0) {
            quantity = 0;
        } else {
            for (var v : currentDonations.rdEntities) { //Εύρεση του Entity στην λίστα του οργανισμού μέσω του iD
                if (entity.getId() == v.getEntity().getId()) {
                    quantity = v.getQuantity(); //Αποθήκευση της ποσότητας
                    search = true;
                    break;
                }
            }
            if (!search) { //Εάν δεν βρεθέι αναθέτει τιμή 0
                quantity = 0;
            }
        }

        return quantity; //Επιστρέφει την ποσότητα
    }

    //Μέθοδος που εμφανίζει τους Beneficiaries του οργανισμού
    public int listBeneficiaries() {
        int count = 1;
        if (beneficiaryList.size() != 0) { //Εμφάνιση των beneficiaries με κατάλληλη μορφοποίηση
            for (var v : beneficiaryList) {
                System.out.println(count + "." + v.getName());
                count++;
            }
        } else { //Εμφάνιση μηνύματος εάν δεν υπάρχουν Beneficiaries
            System.out.println("There are no beneficiaries\n");

        }

        return count; //Επιστρέφει τον αριθμό των Beneficiaries
    }

    //Μέθοδος που επιστρέφει έναν Beneficiary ανάλογα με την επιλογή του χρήστη
    public Beneficiary getBeneficiary(int input) {
        Beneficiary beneficiary;
        beneficiary = beneficiaryList.get(input - 1); //Η λίστα ξεκινάει απο την θέση 0

        return beneficiary; //Επιστρέφει τον Beneficiary
    }

    //Μέθοδος που εμφανίζει τους Donators του οργανισμού
    public int listDonators() {
        int count = 1;
        if (donatorList.size() != 0) { //Εμφάνιση των donators με κατάλληλη μορφοποίηση
            for (var v : donatorList) {
                System.out.println(count + "." + v.getName());
                count++;
            }
        } else { //Εμφάνιση μηνύματος εάν δεν υπάρχουν Donators
            System.out.println("There are no donators\n");
        }

        return count;   //Επιστρέφει τον αριθμό των Donators
    }

    //Μέθοδος που επιστρέφει έναν Donator ανάλογα με την επιλογή του χρήστη
    public Donator getDonator(int input) {
        Donator donator;
        donator = donatorList.get(input - 1); //Η λίστα ξεκινάει απο την θέση 0

        return donator; //Επιστρέφει τον Donator
    }

    //Μέθοδος που επιστρέφει έναν Donator μέσω του τηλεφώνου του
    public Donator getDonator(String phone) {
        Donator donator = null;
        for (var v : donatorList) { //Ευρεση του Donator με την χρήση του τηλεφώνου
            if (phone.equals(v.getPhone())) {
                donator = v; //Αποθήκευση του Donator
            }
        }
        return donator; //Επιστρέφει τον Donator
    }

    //Μέθοδος που επιστρέφει έναν Beneficiary μέσω του τηλεφώνου του
    public Beneficiary getBeneficiary(String phone) {
        Beneficiary beneficiary = null;
        for (var v : beneficiaryList) { //Εύρεση του Beneficiary με την χρήση του τηλεφώνου
            if (phone.equals(v.getPhone())) {
                beneficiary = v; //Αποθήκευση του Beneficiary
            }
        }
        return beneficiary; //Επιστρέφει τον Beneficiary
    }

    /*Μέθοδος που καθαρίζει την receivedList κάθε
    Beneficiary που βρίσκεται στον οργανισμό*/
    public void resetBeneficiariesLists() {
        for (var v : beneficiaryList) {
            v.receivedList.rdEntities.clear();
        }
        System.out.println("Beneficiaries receivedList cleared."); //Μήνυμα ότι η ενέργεια ολοκληρώθηκε
        System.out.println("----------------------------");
    }


}

