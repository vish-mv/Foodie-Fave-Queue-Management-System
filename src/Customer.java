public class Customer {
    private String first_name;
    private String last_name;
    private int burgers_required;
    public Customer(String first_name, String last_name, int burgers_required) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.burgers_required = burgers_required;
    }
    public String getFullName(){
        String full_name = first_name+" "+last_name;
        return full_name;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public int getBurgerNo(){
        return burgers_required;
    }
}
