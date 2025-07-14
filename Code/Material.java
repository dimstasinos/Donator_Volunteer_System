public class Material extends Entity {

    //Ιδιωτικές και σταθερές μεταβλητές
    private final double level1;
    private final double level2;
    private final double level3;

    //constructor
    public Material(String name, String description, int id, double level1, double level2, double level3) {
        super(name, description, id);
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
    }

    /*Μέθοδος που επιστρέφει την τιμή
    του level1*/
    public double getLevel1() {
        return level1;
    }

    /*Μέθοδος που επιστρέφει την τιμή
    του level2*/
    public double getLevel2() {
        return level2;
    }

    /*Μέθοδος που επιστρέφει την τιμή
    του level3*/
    public double getLevel3() {
        return level3;
    }

    //Ηλοποίηση της μεθόδου
    public String getDetails() {
        return "Level1=" + getLevel1() + "\tLevel2=" + getLevel2() + "\tLevel3=" + getLevel3() + "\nMaterial";
    }
}