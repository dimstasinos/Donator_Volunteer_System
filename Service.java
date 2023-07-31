public class Service extends  Entity{

    //constructor
    public Service(String name, String description, int id) {
        super(name, description, id);
    }

    //Ηλοποίηση της μεθόδου
    public String getDetails(){
        return "Service";
    }

}