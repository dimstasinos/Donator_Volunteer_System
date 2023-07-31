import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    //Δημιουργία αντικειμένων Scanner ώστε να δέχεται τιμές από το χρήστη
    Scanner inputString = new Scanner(System.in);
    Scanner inputInt = new Scanner(System.in);

    //Μέθοδος που αναγνωρίζει τον χρήστη
    public User Identity(Organization organization) throws CustomException {
        User user = null;
        int number;
        String phone;
        boolean search = false;
        try {
            System.out.println("Login");
            System.out.print("Enter your mobile phone (3 Digits): "); //Περιμένει να βάλει ο χρήστης το τηλέφωνο του
            number = inputInt.nextInt();
            System.out.println("----------------------------");
            if (number <= 0) {
                throw new CustomException("The mobile number is invalid.");
            } else if (number / 100 <= 0 || number / 100 >= 10) {
                throw new CustomException("The mobile number is invalid.");
            }
        } catch (InputMismatchException e) { //Διαχείρηση της εξαίρεσης που μπορεί να προκύψει
            throw new CustomException("----------------------------\nThe mobile number is invalid.");
        } finally {
            inputInt.nextLine();
        }

        phone = String.valueOf(number); //Μετατροπή του int σε String

        //Εύρεση του χρήστη
        if (phone.equals(organization.getAdmin().getPhone())) { //Έλεγχος εάν ο χρήστης είναι Admin
            user = organization.getAdmin(); //Αποθήκευση του χρήστη
            search = true;
        } else {
            for (var v : organization.donatorList) { //Έλεγχος εάν ο χρήστης είναι Donator
                if (phone.equals(v.getPhone())) {
                    user = v; //Αποθήκευση του χρήστη
                    search = true;
                    break;
                }
            }
            if (!search) {
                for (var v : organization.beneficiaryList) { //Έλεγχος εάν ο χρήστης είναι Beneficiary
                    if (phone.equals(v.getPhone())) {
                        user = v; //Αποθήκευση του χρήστη
                        search = true;
                        break;
                    }
                }
            }
        }

        //Εάν δεν βρεθεί ο χρήστης τον προτρέπει να εγγραφεί
        if (!search) {
            boolean error;
            do {
                error = false;
                try {
                    Register(organization, phone); //Καλεί την μέθοδο για την εγγραφή του χρήστη
                } catch (CustomException e) {
                    error = true;
                    System.out.println(e.getMessage());
                    System.out.println("----------------------------");
                }
            } while (error);
        }

        return user;
    }

    //Μέθοδος που εγγράφει έναν νέο χρήστη
    public void Register(Organization organization, String phone) throws CustomException {
        String choice, name, input;
        boolean error = false, back;
        int noPersons;

        do {
            back = false;
            System.out.print("Do you want to register (Y/N): "); //Μήνυμα για το αν θέλει να εγγραφεί
            choice = inputString.nextLine();
            if (choice.equals("Y") || choice.equals("y")) {
                System.out.println("----------------------------");
                System.out.println("Registration"); //Έναρξη εγγραφής
                System.out.println("Do you want to register as:\n1.Donator\n2.Beneficiary\n3.Back");
                System.out.print("choose: ");
                input = inputString.nextLine();
                System.out.println("----------------------------");
                if (input.equals("1")) { //Εγγραφή ως Donator
                    do {
                        try {
                            System.out.print("Name:");
                            name = inputString.nextLine();
                            if (name.matches(".*\\d.*")) {
                                throw new CustomException("Invalid name\nPlease enter a valid name");
                            }
                            organization.insertDonator(new Donator(name, phone)); //Εκχώρηση του Donator στον οργανισμό
                            System.out.println("You have register"); //Μήνυμα για την επιτυχίας της εγγραφής
                            System.out.println("----------------------------");
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (error);
                } else if (input.equals("2")) { //Εγγραφή ως Beneficiary
                    do {
                        try {
                            System.out.print("Name:");
                            name = inputString.nextLine();
                            if (name.matches(".*\\d.*")) {
                                throw new CustomException("Invalid name\nPlease enter a valid name");
                            }
                            try {                                                    //Διαχείριση των εξαιρέσεων από την λάθος είσοδο του χρήστη
                                System.out.print("Number of persons in the family:");
                                noPersons = inputInt.nextInt();
                            } catch (InputMismatchException e) {
                                throw new CustomException("The mobile number is invalid.");
                            }
                            organization.insertBeneficiary(new Beneficiary(name, phone, noPersons)); //Εκχώρηση του Donator στον οργανισμό
                            System.out.println("You have register"); //Μήνυμα για την επιτυχίας της εγγραφής
                            System.out.println("----------------------------");
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (error);
                } else if (input.equals("3")) {
                    back = true;
                } else {
                    throw new CustomException("[" + input + "]" + "is invalid\nYou must enter a valid number.");
                }
            } else if (choice.equals("N") || choice.equals("n")) {
                System.out.println("----------------------------");
            } else {
                throw new CustomException("----------------------------\nInvalid choice\nPlease select Y/N");
            }
        } while (back);

    }

    //Μέθοδος που εμφανίζει τα στοιχέια του χρήστη και τις πληροφορίες του
    public void Welcome(User user, Organization organization) {
        System.out.print("Welcome ");
        System.out.println(user.toString());
        System.out.println("Organization: " + organization.getName());
        System.out.println("----------------------------");
    }

    //Μέθοδος που εμφανίζει κατάλληλα το κεντρικό menu του Admin
    public String AdminMainMenu() throws CustomException {
        String input;
        String input_2;
        boolean back, error;

        do {
            back = false;
            System.out.println("1.View\n2.Monitor Organization\n3.Logout\n4.Exit");
            System.out.print("Choose: ");
            input = inputString.nextLine();
            System.out.println("----------------------------");
            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
                throw new CustomException("[" + input + "]" + " is invalid\nYou must enter a valid number.");
            }
            if (input.equals("4")) {
                do {
                    error = false;
                    try {
                        System.out.print("Do you want to exit (Y/N):");
                        input_2 = inputString.nextLine();
                        System.out.println("----------------------------");
                        if (input_2.equals("N") || input_2.equals("n")) {
                            back = true;
                        } else if (!input_2.equals("Y") && !input_2.equals("y")) {
                            throw new CustomException("Invalid choice\nPlease select Y/N");
                        }
                    } catch (CustomException e) {
                        error = true;
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                    }
                } while (error);
            }
        } while (back);
        return input;
    }

    //Μέθοδος που εμφανίζει τις δύο κατηγορίες των Entities
    public String ViewCategories() throws CustomException {
        String input;

        System.out.println("1.Material\n2.Services\n3.Back");
        System.out.print("Choose: ");
        input = inputString.nextLine();
        System.out.println("----------------------------");
        if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
            throw new CustomException("[" + input + "] is invalid\nYou must enter a valid number.");
        }
        return input;
    }

    //Μέθοδος που εμφανίζει τα Entities για έναν Admin
    public boolean ViewEntities(Organization organization, String input) throws CustomException {
        int count;
        int choice;
        boolean back = false;
        Entity entity;
        try {
            count = organization.listEntities(input); //Εμφανίζει τα Material ή τα Services
            System.out.println(count + ".Back");
            System.out.print("Choice: ");
            choice = inputInt.nextInt();
            if (choice == count) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                System.out.println("----------------------------");
                back = true;
            }
            if (choice < 1 || choice > count) { //Δημιουργία εξαίρεσης
                throw new CustomException("The input is invalid\nYou must enter a valid number");
            }
            if (choice != count) { //Εμφάνιση πληροφοριών για κάποιο Entity
                System.out.println("----------------------------");
                entity = organization.getEntity(input, choice); //Εύρεση Entity
                System.out.println(entity.toString());
                System.out.println("Quantity: " + organization.getQuantity(entity));
                System.out.println("----------------------------");
            }
        } catch (InputMismatchException e) {
            throw new CustomException("The input is invalid\nYou must enter a valid number");
        } finally {
            inputInt.nextLine();
        }


        return back;
    }

    //Μέθοδος που εμφανίζει τα Entities για έναν Donator
    public boolean ViewEntitiesDonator(Organization organization, String input, User user) throws CustomException {
        int count, choice, quantity;
        String select = "-1";
        boolean back = false, error;
        Entity entity;
        Donator donator;

        try {
            count = organization.listEntities(input); //Εμφανίζει τα Materials ή τα Services
            System.out.println(count + ".Back");
            System.out.print("Choice: ");
            choice = inputInt.nextInt();
            if (choice == count) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                System.out.println("----------------------------");
                back = true;
            }
            if (choice < 1 || choice > count) {
                throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
            }

            if (choice != count) { //Εμφάνιση πληροφοριών για κάποιο Entity
                System.out.println("----------------------------");
                entity = organization.getEntity(input, choice);
                System.out.println(entity.toString());
                System.out.println("Quantity: " + organization.getQuantity(entity));
                System.out.println("----------------------------");
                do {
                    error = false;
                    try {
                        System.out.println("Do you want to offer " + entity.getName() + "?"); //Προσθήκη δωρεάς από τον Donator
                        System.out.print("Choose (Y/N): ");
                        select = inputString.nextLine();
                        System.out.println("----------------------------");
                        if (!select.equals("Y") && !select.equals("y") && !select.equals("N") && !select.equals("n")) {
                            throw new CustomException("[" + select + "] is invalid\nYou must enter Y/N");
                        }
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                        error = true;
                    }
                } while (error);
                if (select.equals("Y") || select.equals("y")) {
                    do {
                        error = false;
                        try {
                            System.out.print("Quantity: "); //Προσθήκη της ποσότητας για κάποια δωρεά
                            quantity = inputInt.nextInt();
                            System.out.println("----------------------------");
                            if (quantity <= 0) {
                                throw new CustomException("The input is invalid\nYou must enter a valid number");
                            }
                            donator = organization.getDonator(user.getPhone()); //Εύρεση του Donator
                            donator.offersList.add(new RequestDonation(entity, quantity)); //Προσθήκη δωρεάς στην λίστα του Donator
                        } catch (InputMismatchException e) {
                            System.out.println("----------------------------");
                            System.out.println("The input is invalid\nYou must enter a valid number");
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        }
                    } while (error);
                }
            }
        } catch (InputMismatchException e) {
            throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
        } finally {
            inputInt.nextLine();
        }


        return back;
    }

    //Μέθοδος που εμφανίζει τα Entities για έναν Beneficiary
    public boolean ViewEntitiesBeneficiary(Organization organization, String input, User user) throws CustomException {
        int count;
        int choice;
        String select = "-1";
        boolean back = false, error;
        int quantity;
        Entity entity;
        Beneficiary beneficiary;
        try {
            count = organization.listEntities(input); //Εμφάνιση των Materials ή Entities
            System.out.println(count + ".Back");
            System.out.print("Choice: ");
            choice = inputInt.nextInt();
            if (choice == count) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                System.out.println("----------------------------");
                back = true;
            }
            if (choice < 1 || choice > count) {
                throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
            }
            if (choice != count) { //Εμφάνιση πληροφοριών για κάποιο Entity
                System.out.println("----------------------------");
                entity = organization.getEntity(input, choice);
                System.out.println(entity.toString());
                System.out.println("Quantity: " + organization.getQuantity(entity));
                System.out.println("----------------------------");
                do {
                    error = false;
                    try {
                        System.out.println("Do you want to receive " + entity.getName() + "?"); //Προσθήκη είδους που ζητάει ο Beneficiary
                        System.out.print("Choose (Y/N): ");
                        select = inputString.nextLine();
                        System.out.println("----------------------------");
                        if (!select.equals("Y") && !select.equals("y") && !select.equals("N") && !select.equals("n")) {
                            throw new CustomException("[" + select + "] is invalid\nYou must enter Y/N");
                        }
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                        error = true;
                    }
                } while (error);
                if (select.equals("Y") || select.equals("y")) {
                    do {
                        error = false;
                        try {
                            System.out.print("Quantity: "); //Προσθήκη της ποσότητας του Request
                            quantity = inputInt.nextInt();
                            System.out.println("----------------------------");
                            if (quantity < 0) {
                                throw new CustomException("The input is invalid\nYou must enter a valid number");
                            } else if (organization.getQuantity(entity) == 0) {
                                back = true;
                            } else {
                                beneficiary = organization.getBeneficiary(user.getPhone()); //Εύρεση του Beneficiary
                                beneficiary.requestsList.add(new RequestDonation(entity, quantity), organization, beneficiary); //Προσθήκη του Request στην requestList
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("----------------------------");
                            System.out.println("The input is invalid\nYou must enter a valid number");
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        }
                    } while (error);
                }
            }
        } catch (InputMismatchException e) {
            throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
        } finally {
            inputInt.nextLine();
        }


        return back;
    }

    //Μέθοδος που εμφανίζει κατάλληλα τις επιλογές του MonitorOrganization
    public String AdminMonitorOrganization() throws CustomException {
        String input;

        System.out.println("1.List Beneficiaries\n2.List Donators\n3.Reset Beneficiaries");
        System.out.println("4.Back");
        System.out.print("Choose: ");
        input = inputString.nextLine();
        System.out.println("----------------------------");
        if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
            throw new CustomException("[" + input + "]" + "is invalid\nYou must enter a valid number.");
        }

        return input;
    }

    //Μέθοδος που καθαρίζει την receivedList όλων των Beneficiary μετά από ερώτηση του χρήστη
    public void AdminResetBeneficiaries(Organization organization) throws CustomException {
        String input;

        System.out.println("Confirmation, do you want to proceed (Y/N): ");
        System.out.print("Choose: ");
        input = inputString.nextLine();
        System.out.println("----------------------------");
        if (input.equals("Y") || input.equals("y")) {
            organization.resetBeneficiariesLists();
        } else if (!input.equals("N") && !input.equals("n")) {
            throw new CustomException("[" + input + "]" + "is invalid\nYou must enter a valid number.");
        }
    }

    //Μέθοδος που εμφανίζει τους Beneficiaries του οργανισμού για έναν Admin
    public boolean AdminListBeneficiaries(Organization organization) throws CustomException {
        boolean back, back_1;
        int count;
        int input;
        Beneficiary beneficiary;
        boolean error;
        do {
            back = false;
            back_1 = false;
            try {
                count = organization.listBeneficiaries(); //Εμφάνιση των Beneficiaries
                System.out.println(count + ".Back");
                System.out.print("Choose: ");
                input = inputInt.nextInt();
                System.out.println("----------------------------");
                if (input == count) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                    back = true;
                }
                if (input < 1 || input > count) {
                    throw new CustomException("The input is invalid\nYou must enter a valid number");
                }
                if (input != count) {
                    beneficiary = organization.getBeneficiary(input); //Εύρεση του beneficiary
                    do {
                        error = false;
                        try {
                            back_1 = AdminListBeneficiariesActions(organization, beneficiary); //Μέθοδος με ενέργειες για έναν Beneficiary
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (error || !back_1);
                }
            } catch (InputMismatchException e) {
                throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
            } finally {
                inputInt.nextLine();
            }
        } while (back_1);

        return back;
    }

    //Μέθοδος που εμφανίζει τις ενέργειες που μπορούν να γίνουν σε έναν Beneficiary από έναν Admin
    public boolean AdminListBeneficiariesActions(Organization organization, Beneficiary beneficiary) throws CustomException {
        String input;
        boolean back = false;
        System.out.println(beneficiary.toString()); //Εμφάνιση του Beneficiary
        if (beneficiary.receivedList.rdEntities.size() != 0) {
            System.out.println("\nHe has received:"); //Εμφάνιση ειδών που έχει παραλάβει
            for (var v : beneficiary.receivedList.rdEntities) {
                System.out.println("-> " + v.getEntity().getName() + " [" + v.getQuantity() + "]");
            }
        } else {
            System.out.println("He has not taken anything");
        }

        //Ενέργειες που μπορούν να γίνουν στον Beneficiary
        System.out.println("\n1.Clear receivedList\n2.Remove Beneficiary");
        System.out.println("3.Back");
        System.out.print("Choose: ");
        input = inputString.nextLine();
        System.out.println("----------------------------");
        if (input.equals("3")) {
            back = true; //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
        } else if (input.equals("1")) {
            beneficiary.receivedList.rdEntities.clear(); //Καθαρίζει την receivedList του Beneficiary
            System.out.println("ReceivedList cleared");
            System.out.println("----------------------------");
        } else if (input.equals("2")) {
            organization.removeBeneficiary(beneficiary, organization.getAdmin()); //Διαγράφει τον Beneficiary από
            System.out.println("Beneficiary removed");                            //τον οργανισμό
            System.out.println("----------------------------");
            back = true;
        }
        if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
            throw new CustomException("[" + input + "] is not valid\nYou must enter a valid number");
        }

        return back;
    }

    //Μέθοδος που εμφανίζει τους Donators του οργανισμού για έναν Admin
    public boolean AdminListDonators(Organization organization) throws CustomException {
        boolean back, back_1;
        int count, input;
        Donator donator;
        boolean error;
        do {
            back_1 = false;
            back = false;
            try {
                count = organization.listDonators(); //Εμφάνιση των Donators
                System.out.println(count + ".Back");
                System.out.print("Choose: ");
                input = inputInt.nextInt();
                System.out.println("----------------------------");
                if (count == input) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                    back = true;
                }
                if (input < 1 || input > count) {
                    throw new CustomException("The input is invalid\nYou must enter a valid number");
                }
                if (count != input) {
                    donator = organization.getDonator(input); //Εύρεση του Donator
                    do {
                        error = false;
                        try {
                            back_1 = AdminListDonatorsActions(organization, donator); //Μέθοδος με ενέργειες για έναν Donator
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (error || !back_1);
                }
            } catch (InputMismatchException e) {
                throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
            } finally {
                inputInt.nextLine();
            }
        } while (back_1);

        return back;
    }

    //Μέθοδος που εμφανίζει τις ενέργειες που μπορούν να γίνουν σε έναν Donator από έναν Admin
    public boolean AdminListDonatorsActions(Organization organization, Donator donator) throws CustomException {
        String input;
        boolean back = false;
        System.out.println(donator.toString()); //Εμφάνιση του Donator
        if (donator.offersList.rdEntities.size() != 0) { //Εμφάνιση ειδών που θέλει να δώσει
            System.out.println("\nHe want to offer:");
            for (var v : donator.offersList.rdEntities) {
                System.out.println("-> " + v.getEntity().getName() + " [" + v.getQuantity() + "]");
            }


        } else {
            System.out.println("He has not offer anything");

        }
        System.out.println("\n1.Delete Donator");
        System.out.println("2.Back");
        System.out.print("Choose: ");
        input = inputString.nextLine();
        System.out.println("----------------------------");
        if (input.equals("2")) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
            back = true;
        } else if (input.equals("1")) {
            organization.removeDonator(donator, organization.getAdmin()); //Διαγράφει τον Donator από τον οργανισμό
            System.out.println("Donator deleted");
            System.out.println("----------------------------");
            back = true;
        }
        if (!input.equals("1") && !input.equals("2")) {
            throw new CustomException("[" + input + "] is not valid\nYou must enter a valid number");
        }


        return back;
    }

    //Μέθοδος που εμφανίζει κατάλληλα το κεντρικό menu ενός Donator
    public String DonatorMainMenu(Organization organization, User user) throws CustomException {
        String input, input_2;
        boolean back, error;
        Donator donator;

        do {
            back = false;
            System.out.println("1.Add Offer\n2.Show Offers\n3.Commit\n4.Logout\n5.Exit");
            System.out.print("Choose: ");
            input = inputString.nextLine();
            System.out.println("----------------------------");
            if (input.equals("1")) {
                back = DonatorAddOffer(organization, user); //Καλειται μέθοδος για το επόμενο επίπεδο του menu
            } else if (input.equals("2")) {
                do {
                    error = false;
                    try {
                        back = DonatorShowOffer(organization, user); //Καλειται μέθοδος για το επόμενο επίπεδο του menu
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                        error = true;
                    }
                } while (error);
            } else if (input.equals("3")) {
                donator = organization.getDonator(user.getPhone()); //Εύρεση του Donator
                donator.offersList.commit(organization); //Καλείτε μέθοδος για την εκτέλεση της δωρεάς
                back = true;
            } else if (input.equals("5")) {
                do {
                    error = false;
                    try {
                        System.out.print("Do you want to exit (Y/N):");
                        input_2 = inputString.nextLine();
                        System.out.println("----------------------------");
                        if (input_2.equals("N") || input_2.equals("n")) {
                            back = true;
                        } else if (!input_2.equals("Y") && !input_2.equals("y")) {
                            throw new CustomException("Invalid choice\nPlease select Y/N");
                        }
                    } catch (CustomException e) {
                        error = true;
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                    }
                } while (error);
            }
            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
                throw new CustomException("[" + input + "]" + " is invalid\nYou must enter a valid number.");
            }


        } while (back);

        return input;
    }

    //Μέθοδος που εμφανίζει το menu για την προσθήκη μιας δωρεάς
    public boolean DonatorAddOffer(Organization organization, User user) {
        boolean back, error, back_1;
        String input;

        do {
            error = false;
            back = false;
            back_1 = false;
            try {
                input = ViewCategories(); //Εμφάνιση κατηγοριών του Entity
                if (!input.equals("3")) {
                    do {
                        error = false;
                        try {
                            back_1 = ViewEntitiesDonator(organization, input, user); //Εμφάνιση ειδών για δωρεά
                        } catch (CustomException e) {                                //και εισαγωγή της ποσότητας
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (!back_1 || error);
                }
                if (input.equals("3")) {
                    back = true; //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
                error = true;
            }
        } while (error || back_1);

        return back;
    }

    //Μέθοδος που εμφανίζει τις δωρεές ειδών που θέλει να κάνει ο Donator
    public boolean DonatorShowOffer(Organization organization, User user) throws CustomException {
        boolean back = false, back_1;
        int count, input;
        Donator donator;

        do {
            back_1 = false;
            try {
                donator = organization.getDonator(user.getPhone()); //Εύρεση Donator
                count = donator.offersList.monitor(donator); //Εμφάνιση των ειδών που διατιθετε να δώσει

                System.out.println("\n" + count + ".Clear all offers");
                System.out.println(count + 1 + ".Commit");
                System.out.println(count + 2 + ".Back");
                System.out.print("Choose: ");
                input = inputInt.nextInt();
                System.out.println("----------------------------");
            } catch (InputMismatchException e) {
                throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
            } finally {
                inputInt.nextLine();
            }
            if (input == count + 2) {
                back = true; //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
            } else if (input < 1 || input > count + 2) {
                throw new CustomException("The input is invalid\nYou must enter a valid number");
            } else if (input < count) {
                back_1 = DonatorShowOfferActions(donator, input); //Εμφάνιση ενεργειών για κάποιο είδος προς δωρεά
            } else if (input == count) {
                donator.offersList.reset(); //Καθαρίζει την offerList του Donator
                System.out.println("Offers cleared");
                System.out.println("----------------------------");
                back = true;
            } else if (input == count + 1) {
                donator.offersList.commit(organization); //Καλείται μέθοδος για την εκτέλεση της δωρεάς
                back = true;
            }
        } while (back_1);

        return back;
    }

    //Μέθοδος που εμφανίζει τις ενέργειες που μπορούν να γίνουν σε ένα είδος προς δωρεά από τον Donator
    public boolean DonatorShowOfferActions(Donator donator, int select) {
        String input;
        boolean back = false, error, back_1;
        int quantity;
        RequestDonation requestDonation = donator.offersList.get(select); //Εύρεση του requestDonation
        do {
            try {
                do {
                    error = false;
                    back_1 = false;
                    back = false;
                    System.out.println("Offer: " + requestDonation.getEntity().getName() + " [" + requestDonation.getQuantity() + "]");
                    System.out.println("1.Delete offer\n2.Modify offer\n3.Back"); //Εμφάνιση της δωρεάς και των ενεργειών
                    System.out.print("Choose: ");
                    input = inputString.nextLine();
                    System.out.println("----------------------------");
                    if (input.equals("1")) {
                        donator.offersList.remove(requestDonation); //Αφαιρεί την RequestDonation από την λίστα του Donator
                        back = true;
                        back_1 = false;
                    } else if (input.equals("2")) {
                        do {
                            error = false;
                            back_1 = false;
                            try {
                                System.out.print("Quantity: ");
                                quantity = inputInt.nextInt();
                                System.out.println("----------------------------");
                                donator.offersList.modify(select, quantity); //Καλέιται μέθοδος ώστε να γίνει αλλαγή της ποσότητας
                                back_1 = true;                               //ενός Requestdonation
                                if (quantity <= 0) {
                                    throw new CustomException("The input is invalid\nYou must enter a valid number");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("----------------------------");
                                System.out.println("The input is invalid\nYou must enter a valid number");
                                System.out.println("----------------------------");
                                error = true;
                            } catch (CustomException e) {
                                System.out.println(e.getMessage());
                                System.out.println("----------------------------");
                                error = true;
                            } finally {
                                inputInt.nextLine();
                            }
                        } while (error);
                    } else if (input.equals("3")) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                        back = true;
                        back_1 = false;
                    }
                    if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
                        throw new CustomException("[" + input + "]" + " is invalid\nYou must enter a valid number.");
                    }
                } while (back_1);
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
                error = true;
            }
        } while (error);

        return back;
    }

    //Μέθοδος που εμφανίζει κατάλληλα το κεντρικό menu ενός Beneficiary
    public String BeneficiaryMainMenu(Organization organization, User user) throws CustomException {
        String input, input_2;
        boolean back, error;
        Beneficiary beneficiary;

        do {
            back = false;
            System.out.println("1.Add request\n2.Show requests\n3.Commit\n4.Logout\n5.Exit");
            System.out.print("Choose: ");
            input = inputString.nextLine();
            System.out.println("----------------------------");
            if (input.equals("1")) {
                back = BeneficiaryAddRequest(organization, user); //Καλειται μέθοδος για το επόμενο επίπεδο του menu
            } else if (input.equals("3")) {
                beneficiary = organization.getBeneficiary(user.getPhone()); //Εύρεση Beneficiary
                beneficiary.requestsList.commit(beneficiary, organization); //Καλείτε μέθοδος για την εκτέλεση του αιτήματος
                back = true;
            } else if (input.equals("2")) {
                back = BeneficiaryShowRequest(user, organization); //Καλειται μέθοδος για το επόμενο επίπεδο του menu
            } else if (input.equals("5")) {
                do {
                    error = false;
                    try {
                        System.out.print("Do you want to exit (Y/N):");
                        input_2 = inputString.nextLine();
                        System.out.println("----------------------------");
                        if (input_2.equals("N") || input_2.equals("n")) {
                            back = true;
                        } else if (!input_2.equals("Y") && !input_2.equals("y")) {
                            throw new CustomException("Invalid choice\nPlease select Y/N");
                        }
                    } catch (CustomException e) {
                        error = true;
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                    }
                } while (error);
            }
            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
                throw new CustomException("[" + input + "] is invalid\nPlease enter a valid number");
            }
        } while (back);

        return input;
    }

    //Μέθοδος που εμφανίζει το menu για την προσθήκη ενός Request
    public boolean BeneficiaryAddRequest(Organization organization, User user) {
        boolean back, error, back_1;
        String input;

        do {
            error = false;
            back = false;
            back_1 = false;
            try {
                input = ViewCategories(); //Εμφάνιση κατηγοριών του Entity
                if (!input.equals("3")) {
                    do {
                        error = false;
                        try {
                            back_1 = ViewEntitiesBeneficiary(organization, input, user); //Εμφάνιση των ειδών για αίτηση
                        } catch (CustomException e) {                                    //και εισαγωγή της ποσότητας
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (!back_1 || error);
                }
                if (input.equals("3")) {
                    back = true; //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
                error = true;
            }
        } while (error || back_1);

        return back;
    }

    //Μέθοδος που εμφανίζει των αιτημάτων που θέλει να κάνει o Beneficiary
    public boolean BeneficiaryShowRequest(User user, Organization organization) throws CustomException {
        boolean back = false, back_1;
        int count, input;
        Beneficiary beneficiary;
        do {
            back_1 = false;
            try {
                try {
                    beneficiary = organization.getBeneficiary(user.getPhone()); //Εύρεση του Beneficiary
                    count = beneficiary.requestsList.monitor(beneficiary); ///Εμφάνιση των αιτημάτων που θέλει να κάνει
                    System.out.println("\n" + count + ".Clear all requests");
                    System.out.println(count + 1 + ".Commit");
                    System.out.println(count + 2 + ".Back");
                    System.out.print("Choose: ");
                    input = inputInt.nextInt();
                    System.out.println("----------------------------");
                } catch (InputMismatchException e) {
                    throw new CustomException("----------------------------\nThe input is invalid\nYou must enter a valid number");
                }
                if (input == count + 2) {
                    back = true; //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                } else if (input < 1 || input > count + 2) {
                    throw new CustomException("The input is invalid\nYou must enter a valid number");
                } else if (input < count) {
                    back_1 = BeneficiaryShowRequestActions(beneficiary, input, organization); //Εμφάνιση ενεργειών για κάποιο αίτημα
                } else if (input == count) {
                    beneficiary.requestsList.reset(); //Καθαριζει την requestsList του Beneficiary
                    System.out.println("Requests cleared");
                    System.out.println("----------------------------");
                    back = true;
                } else if (input == count + 1) {
                    beneficiary.requestsList.commit(beneficiary, organization); //Καλέιται η μέθοδος για την εκτέλεση του αιτήματος
                    back_1 = false;
                    back = true;
                }
            } catch (CustomException e) {
                back_1 = true;
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
            } finally {
                inputInt.nextLine();
            }
        } while (back_1);
        return back;
    }

    //Μέθοδος που εμφανίζει τις ενέργειες που μπορούν να γίνουν σε ένα αίτημα του Beneficiary
    public boolean BeneficiaryShowRequestActions(Beneficiary beneficiary, int select, Organization organization) {
        String input;
        boolean back, error, back_1;
        int quantity;
        RequestDonation requestDonation = beneficiary.requestsList.get(select); //Εύρεση του requestDonation

        do {
            back_1 = false;
            back = false;
            error = false;
            try {
                System.out.println("Request: " + requestDonation.getEntity().getName() + " [" + requestDonation.getQuantity() + "]");
                System.out.println("1.Delete request\n2.Modify request\n3.Back"); //Εμφάνιση του αιτήματος και των ενεργειών
                System.out.print("Choose: ");
                input = inputString.nextLine();
                System.out.println("----------------------------");
                if (input.equals("1")) {
                    beneficiary.requestsList.remove(requestDonation); //Αφαιρεί του αιτήματος από την requestList του Beneficiary
                    System.out.println("Request deleted");
                    System.out.println("----------------------------");
                    back = true;
                } else if (input.equals("2")) {
                    do {
                        error = false;
                        try {
                            requestDonation = beneficiary.requestsList.get(select);
                            System.out.print("Quantity: ");
                            quantity = inputInt.nextInt();
                            System.out.println("----------------------------");
                            if (quantity <= 0) {
                                throw new CustomException("The input is invalid\nYou must enter a valid number");
                            }
                            try {
                                beneficiary.requestsList.modify(requestDonation, organization, beneficiary, quantity); //Καλέιτε μέθοδος ώστε να γίνει
                                back_1 = true;                                                                         //αλλαγή της ποσότητας ενός αιτήματος
                            } catch (CustomException msg) {
                                back_1 = true;
                                System.out.println(msg.getMessage());
                                System.out.println("----------------------------");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("----------------------------");
                            System.out.println("The input is invalid\nYou must enter a valid number");
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            inputInt.nextLine();
                            error = true;
                        }
                    } while (error);
                } else if (input.equals("3")) { //Πηγαίνει στο menu ένα επίπεδο προς τα πάνω
                    back = true;
                    back_1 = false;
                }
                if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
                    throw new CustomException("[" + input + "]" + " is invalid\nYou must enter a valid number.");
                }
            } catch (CustomException e) {
                error = true;
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
            }
        } while (back_1 || error);

        return back;
    }

}

