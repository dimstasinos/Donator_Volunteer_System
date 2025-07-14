public class RequestDonation {

    Entity entity;
    private double quantity;

    //constructor
    public RequestDonation(Entity entity, double quantity) {
        this.entity = entity;
        this.quantity = quantity;
    }

    //Μέθοδος που επιστρέφει το Entity
    public Entity getEntity() {
        return entity;
    }

    //Μέθοδος που επιστρέφει το quantity
    public double getQuantity() {
        return quantity;
    }

    //Μέθοδος που θέτει νέα τιμή στο quantity
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
