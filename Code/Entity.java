public abstract class Entity {

    //Ιδιωτικές και σταθερές μεταβλητές
    private final String name;
    private final String description;
    private final int id;

    //constructor
    public Entity(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    /*Μέθοδος που επιστρέφει
    το όνομα του είδους*/
    public String getName() {
        return name;
    }

    /*Μέθοδος που επιστρέφει
    το Id του είδους*/
    public int getId() {
        return id;
    }

    /*Μέθοδος που επιστρέφει κατάλληλα μορφοποιημένα
    το όνομα, περιγραφή και Id του είδους*/
    public String getEntityInfo() {
        return "Name: " + name + "\nDescription: " + description + "\nID: " + id;
    }

    //Δήλωση μεθόδου
    public abstract String getDetails();

    /*Μέθοδος που επιστρέφει κατάλληλα μορφοποιημένα
    το σύνολο των διαθέσιμων πληροφοριών του είδους*/
    public String toString() {
        return getEntityInfo() + "\n" + getDetails();
    }


}
