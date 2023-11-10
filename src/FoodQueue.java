public class FoodQueue {
    private int queue_number;
    private Customer customer;

    public FoodQueue(Customer customer,int queue_number){
        this.customer=customer;
        this.queue_number=queue_number;
    }

    public String GetFullName(){
        return customer.getFullName();
    }
    public String GetFirstName(){
        return customer.getFirst_name();
    }
    public String GetLastName(){
        return customer.getLast_name();
    }
    public int GetBurgerNo(){
        return customer.getBurgerNo();
    }
    public int getQueue_number(){
        return queue_number;
    }
}
