public class Main {

    public static void main(String[] args) {

        //Δημιουργία αντικειμένου Admin
        Admin admin = new Admin("Dimitris", "698");

        //Δημιουργία αντικειμένου Organization
        Organization organization = new Organization("CEID", admin);

        //Δημιουργία αντικειμένων Materials
        Material milk = new Material("Milk", "Cow's", 100, 2, 4, 6);
        Material sugar = new Material("Sugar", "Brown sugar", 101, 2, 4, 6);
        Material rice = new Material("Rice", "White", 102, 2, 4, 6);

        //Προσθήκη ειδών στον οργανισμό
        organization.addEntity(milk);
        organization.addEntity(sugar);
        organization.addEntity(rice);

        //Δημιουργία αντικειμένων Services
        Service MedicalSupport = new Service("MedicalSupport", "Medicines", 200);
        Service NurserySupport = new Service("NurserySupport", "School", 201);
        Service BabySitting = new Service("BabySitting", "For children", 202);

        //Προσθήκη ειδών στον οργανισμό
        organization.addEntity(MedicalSupport);
        organization.addEntity(NurserySupport);
        organization.addEntity(BabySitting);

        //Προσθήκη διαθέσιμων ειδών στον οργανισμό
        organization.currentDonations.add(new RequestDonation(milk, 6));
        organization.currentDonations.add(new RequestDonation(rice, 7));
        organization.currentDonations.add(new RequestDonation(sugar,2));
        organization.currentDonations.add(new RequestDonation(MedicalSupport, 7));
        organization.currentDonations.add(new RequestDonation(BabySitting, 2));
        organization.currentDonations.add(new RequestDonation(NurserySupport, 5));
        //Δημιουργία αντικειμένου Beneficiary
        Beneficiary beneficiary_1 = new Beneficiary("Konstantinos", "697",2);

        //Προσθήκη του Beneficiary στον οργανισμό
        organization.insertBeneficiary(beneficiary_1);

        //Προσθήκη ειδών που ζητά ο Beneficiary
        beneficiary_1.requestsList.add(new RequestDonation(milk, 3));
        beneficiary_1.requestsList.add(new RequestDonation(sugar, 2));
        beneficiary_1.requestsList.add(new RequestDonation(NurserySupport,7));

        //Δημιουργία αντικειμένου Beneficiary
        Beneficiary beneficiary_2 = new Beneficiary("Giorgos", "693", 6);

        //Προσθήκη του Beneficiary στον οργανισμό
        organization.insertBeneficiary(beneficiary_2);

        beneficiary_2.receivedList.add(new RequestDonation(rice,4));
        beneficiary_2.receivedList.add(new RequestDonation(MedicalSupport,3));
        //Δημιουργία αντικειμένου Donator
        Donator donator_1 = new Donator("Alexandros", "695");

        //Προσθήκη του Donator στον Οργανισμό
        organization.insertDonator(donator_1);

        //Προσθήκη ειδών που θέλει να δωρίσει ο Donator
        donator_1.offersList.add(new RequestDonation(sugar, 4));
        donator_1.offersList.add(new RequestDonation(BabySitting, 1));

        //Δημιουργία αντικειμένου Menu
        Menu menu = new Menu();

        User user = null;
        String input, input_2, input_4, input_6, input_7, input_8;
        boolean input_3, input_5;
        boolean error;

        do {
            input = "-1";
            input_6 = "-1";
            input_7 = "-1";
            do {
                error = false;
                try {
                    user = menu.Identity(organization); //Αναγνώρηση χρήστη
                } catch (CustomException e) {
                    System.out.println(e.getMessage());
                    System.out.println("----------------------------");
                    error = true;
                }
            } while (error || user == null);
            if (user instanceof Admin) { //Είσοδος ως Admin
                menu.Welcome(user, organization); //Χαιρετισμός του Admin
                do {
                    do {
                        input_2 = "-1";
                        input_4 = "-1";
                        error = false;
                        try {
                            input = menu.AdminMainMenu(); //Κεντρικό menu του Admin
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            System.out.println("----------------------------");
                            error = true;
                        }
                    } while (error);
                    if (input.equals("1")) {
                        do {
                            input_3 = false;
                            do {
                                error = false;
                                try {
                                    input_2 = menu.ViewCategories(); //Εμφάνισει δύο κατηγοριών (Materials, Entities)
                                } catch (CustomException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("----------------------------");
                                    error = true;
                                }
                            } while (error);
                            if (input_2.equals("1") || input_2.equals("2")) {
                                do {
                                    error = false;
                                    try {
                                        input_3 = menu.ViewEntities(organization, input_2); //Εμφανισει ειδών μιας κατηργορίας
                                    } catch (CustomException e) {
                                        System.out.println("----------------------------");
                                        System.out.println(e.getMessage());
                                        System.out.println("----------------------------");
                                        error = true;
                                    }
                                } while (error || !input_3);
                            }
                        } while (input_3);
                    } else if (input.equals("2")) {
                        do {
                            input_5 = false;
                            do {
                                error = false;
                                try {
                                    input_4 = menu.AdminMonitorOrganization(); //Επιλογές για την διαχείρηση του οργανισμού
                                } catch (CustomException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("----------------------------");
                                    error = true;
                                }
                            } while (error);
                            if (input_4.equals("1")) {
                                do {
                                    error = false;
                                    try {
                                        input_5 = menu.AdminListBeneficiaries(organization); //Εμφανίζει του Beneficiaries
                                    } catch (CustomException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("----------------------------");
                                        error = true;
                                    }
                                } while (error);
                            } else if (input_4.equals("2")) {
                                do {
                                    error = false;
                                    try {
                                        input_5 = menu.AdminListDonators(organization); //Εμφανίζει τους Donators
                                    } catch (CustomException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("----------------------------");
                                        error = true;
                                    }
                                } while (error);
                            } else if (input_4.equals("3")) {
                                do {
                                    error=false;
                                    input_5=false;
                                    try {
                                        menu.AdminResetBeneficiaries(organization); //Καθαρίζει την receivedList κάθε Beneficiary
                                    } catch (CustomException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("----------------------------");
                                        error = true;
                                    }
                                    input_5=true;
                                } while (error);
                            }
                        } while (input_5);
                    }
                } while (input_2.equals("3") || input_4.equals("4"));
            } else if (user instanceof Donator) { //Είσοδος ως Donator
                menu.Welcome(user, organization); //Χαιρετισμός του Donator
                do {
                    error = false;
                    input_6 = "-1";
                    try {
                        input_6 = menu.DonatorMainMenu(organization, user); //Κενρικό menu του Donator
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                        error = true;
                    }
                } while (error);
            } else if (user instanceof Beneficiary) { //Είσοδος ως Beneficiary
                menu.Welcome(user, organization); //Χαιρετισμός του Beneficiary
                do {
                    error = false;
                    input_7 = "-1";
                    try {
                        input_7 = menu.BeneficiaryMainMenu(organization, user); //Κεντρικό menu του Donator
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------");
                        error = true;
                    }
                } while (error);
            }

        } while (input.equals("3") || input_6.equals("4") || input_7.equals("4"));

        System.out.println("Exit......");
        System.out.println("----------------------------");


    }
}