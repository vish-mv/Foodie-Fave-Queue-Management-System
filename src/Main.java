import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String[] Q1 = {"0", "0"};
    static String[] Q2 = {"0", "0", "0"};
    static String[] Q3 = {"0", "0", "0", "0", "0"};
    static int stock = 50;
    static int q1_count;
    static int q2_count;
    static int q3_count;
    static int price=650;
    static float income_q1;
    static float income_q2;
    static float income_q3;
    static ArrayList<FoodQueue> waiting_list=new ArrayList<>();
    static ArrayList<FoodQueue> foodQueues = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            DisplayMenu();
            System.out.println("Enter Your Choice");
            String choice = input.next();
            switch (choice) {
                case "100", "VFQ" -> View_all_Queues();//*
                case "101", "VEQ" -> View_all_Empty_Queues();//*
                case "102", "ACQ" -> Add_customer_to_a_Queue();
                case "103", "RCQ" -> Remove_a_customer_from_a_Queue();
                case "104", "PCQ" -> Remove_a_served_customer();
                case "105", "VCS" -> View_Customers_Sorted_in_alphabetical_order();//*
                case "106", "SPD" -> Store_Program_Data_into_file();//*
                case "107", "LPD" -> Load_Program_Data_from_file();//*
                case "108", "STK" -> View_Remaining_burgers_Stock();//*
                case "109", "AFS" -> Add_burgers_to_Stock();//*
                case "110", "IFQ" -> viewIncomeOfEachQueue();
                case "999", "EXT" -> {
                        System.out.println("Exiting the program.Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice.");
            }

        }
    }

    public static void DisplayMenu() {
        System.out.println();
        System.out.println("--------------------MENU--------------------");
                System.out.println("100 or VFQ View Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("110 or IFQ: View Income of each Queue");
        System.out.println("999 or EXT: Exit the Program");
    }

    public static void View_all_Queues() {
        System.out.println();
        System.out.println("--------------------ALL QUEUES-------------------");
        System.out.println();
        System.out.println("*****  ");
        System.out.println("*  Cashiers  *  ");
        System.out.println("*****  ");
        for (int i = 0; i < 4; i++) {
            if ((i > 1)) {
                System.out.print("    ");
            } else {
                if (Q1[i].equals("0")) {
                    System.out.print("X   ");
                } else {
                    System.out.print("O   ");
                }
            }
            if ((i > 2)) {
                System.out.print("    ");
            } else {
                if (Q2[i].equals("0")) {
                    System.out.print("X   ");
                } else {
                    System.out.print("O   ");
                }
            }
            if (Q3[i].equals("0")) {
                System.out.print("X   ");
            } else {
                System.out.print("O   ");
            }
            System.out.println();
        }
    }

    public static void View_all_Empty_Queues() {
        boolean q1_value = GetQueue1Value();
        if (!q1_value) {
            System.out.println("Queue 1 is empty");
        }
        boolean q2_value = GetQueue2Value();
        if (!q2_value) {
            System.out.println("Queue 2 is empty");
        }
        boolean q3_value = GetQueue3Value();
        if (!q3_value) {
            System.out.println("Queue 3 is empty");
        }
    }

    public static boolean GetQueue1Value() {
        int x = 0;
        q1_count = 0;
        for (String i : Q1) {
            if (i.equals("1")) {
                x = 1;
                q1_count++;
            }
        }
        return x == 1;
    }

    public static boolean GetQueue2Value() {
        int x = 0;
        q2_count = 0;
        for (String i : Q2) {
            if (i.equals("1")) {
                x = 1;
                q2_count++;
            }
        }
        return x == 1;
    }

    public static boolean GetQueue3Value() {
        int x = 0;
        q3_count = 0;
        for (String i : Q3) {
            if (i.equals("1")) {
                x = 1;
                q3_count++;
            }
        }
        return x == 1;
    }


    public static void Add_customer_to_a_Queue() {
        while (true) {
            Scanner input_2 = new Scanner(System.in);
            System.out.println("Enter Customer's First name : ");
            String first_name = input_2.next();
            System.out.println("Enter Customer's Last name : ");
            String last_name = input_2.next();
            int NoOfBurgers;
            while (true) {
                try {
                    System.out.println("Enter How many Burgers Customer wants : ");
                    NoOfBurgers = input_2.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Burger Value Has to be an Integer");
                    input_2.nextLine();
                }
            }
            Customer_adder(first_name,last_name,NoOfBurgers);
            System.out.println("Do you want to add another customer to the Queue?");
            System.out.println("Enter 'Y' for Add another Customer Enter any key to Continue to Menu");
            String continue_checker=input_2.next();
            if(!continue_checker.equalsIgnoreCase("y")){
                break;
            }
        }
    }
    public static void Customer_adder(String first_name,String last_name, int NoOfBurgers){
        Customer customer= new Customer(first_name,last_name,NoOfBurgers);
        //Go through the ques for find the space left in the queues
        GetQueue1Value();
        GetQueue2Value();
        GetQueue3Value();
        int best_queue=FindTheBestQueue(q1_count,q2_count,q3_count);
        if (best_queue==0){
                System.out.println("Sorry! All the queues are full You will be added to waiting list");
            FoodQueue foodQueue=new FoodQueue(customer,best_queue);
            waiting_list.add(foodQueue);
        } else if (best_queue==1) {
            System.out.println("Customer will be added to Queue 1");
            FoodQueue foodQueue=new FoodQueue(customer,best_queue);
            foodQueues.add(foodQueue);
            Q1[q1_count]="1";
            stock=stock-NoOfBurgers;
            int current_income=NoOfBurgers*price;
            income_q1+=current_income;

        }
        else if (best_queue==2) {
            System.out.println("Customer will be added to Queue 2");
            FoodQueue foodQueue=new FoodQueue(customer,best_queue);
            foodQueues.add(foodQueue);
            Q2[q2_count]="1";
            stock=stock-NoOfBurgers;
            int current_income=NoOfBurgers*price;
            income_q2+=current_income;
        }
        else if (best_queue==3) {
            System.out.println("Customer will be added to Queue 3");
            FoodQueue foodQueue=new FoodQueue(customer,best_queue);
            foodQueues.add(foodQueue);
            Q3[q3_count]="1";
            stock=stock-NoOfBurgers;
            int current_income=NoOfBurgers*price;
            income_q3+=current_income;
        }
        if(stock<=10){
            System.out.println("! Burger Stock is Low Only "+stock+" Burgers Remaining !");
        }
    }
    public static int FindTheBestQueue(int q1, int q2, int q3) {
        int q1_remain=2-q1;
        int q2_remain=3-q2;
        int q3_remain=5-q3;
        if (q1_remain==0 && q2_remain==0 && q3_remain== 0){
            return 0;
        }
        if (q1_remain >= q2_remain && q1_remain >= q3_remain) {
            return 1;
        } else if (q2_remain >= q1_remain && q2_remain >= q3_remain) {
            return 2;
        } else {
            return 3;
        }
    }
    public static void Remove_a_customer_from_a_Queue(){
        while(true) {
            Scanner input_3 = new Scanner(System.in);
            System.out.println("Enter Customer's First name : ");
            String first_name = input_3.next();
            System.out.println("Enter Customer's Last name : ");
            String last_name = input_3.next();
            String full_name = first_name + " " + last_name;
            int name_checker = 0;
            for (FoodQueue foodQueue : foodQueues) {
                if (full_name.equals(foodQueue.GetFullName())) {
                    int burger_value = foodQueue.GetBurgerNo();
                    int queue_number = foodQueue.getQueue_number();
                    foodQueues.remove(foodQueue);
                    System.out.println("Customer Removed Successfully");
                    stock = stock + burger_value;
                    GetQueue1Value();
                    GetQueue2Value();
                    GetQueue3Value();
                    if (queue_number == 1) {
                        Q1[q1_count - 1] = "0";
                        int current_income=burger_value*price;
                        income_q1=income_q1-current_income;
                    } else if (queue_number == 2) {
                        Q2[q2_count - 1] = "0";
                        int current_income=burger_value*price;
                        income_q2=income_q2-current_income;
                    } else if (queue_number == 3) {
                        Q3[q3_count - 1] = "0";
                        int current_income=burger_value*price;
                        income_q3=income_q3-current_income;
                    }
                    name_checker = 1;
                    break;
                }
            }
            if (name_checker == 0) {
                System.out.println("Invalid Name recheck!");
            }
            System.out.println("Do you want to add remove customer from the Queue?");
            System.out.println("Enter 'Y' for remove another Customer Enter any key to Continue to Menu");
            String continue_checker=input_3.next();
            if(!continue_checker.equalsIgnoreCase("y")){
                break;
            }
        }
    }
    public static void Remove_a_served_customer(){
        while (true) {
            Scanner input_3 = new Scanner(System.in);
            System.out.println("Enter Customer's First name : ");
            String first_name = input_3.next();
            System.out.println("Enter Customer's Last name : ");
            String last_name = input_3.next();
            String full_name = first_name + " " + last_name;
            int name_checker = 0;
            for (FoodQueue foodQueue : foodQueues) {
                if (full_name.equals(foodQueue.GetFullName())) {
                    int queue_number = foodQueue.getQueue_number();
                    foodQueues.remove(foodQueue);
                    System.out.println("Customer Removed Successfully");
                    GetQueue1Value();
                    GetQueue2Value();
                    GetQueue3Value();
                    if (queue_number == 1) {
                        Q1[q1_count - 1] = "0";
                    } else if (queue_number == 2) {
                        Q2[q2_count - 1] = "0";
                    } else if (queue_number == 3) {
                        Q3[q3_count - 1] = "0";
                    }
                    if(!waiting_list.isEmpty()){
                        for(FoodQueue waiting_customer:waiting_list){
                            String wait_first_name=waiting_customer.GetFirstName();
                            String wait_last_name=waiting_customer.GetLastName();
                            int wait_burger_count=waiting_customer.GetBurgerNo();
                            Customer_adder(wait_first_name,wait_last_name,wait_burger_count);
                        }
                    }
                    name_checker = 1;
                    break;
                }
            }
            if (name_checker == 0) {
                System.out.println("Invalid Name recheck!");
            }
            System.out.println("Do you want to add remove customer from the Queue?");
            System.out.println("Enter 'Y' for remove another Customer Enter any key to Continue to Menu");
            String continue_checker = input_3.next();
            if (!continue_checker.equalsIgnoreCase("y")) {
                break;
            }
        }
    }
    public static void View_Customers_Sorted_in_alphabetical_order(){
        ArrayList<String> customerNames = new ArrayList<>();
        for (FoodQueue foodQueue : foodQueues) {
            String fullName = foodQueue.GetFullName();
            customerNames.add(fullName);
        }
        sortNames(customerNames);
        for (String name : customerNames) {
            System.out.println(name);
        }
    }
    public static void sortNames(ArrayList<String> arr){
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j).compareToIgnoreCase(arr.get(j + 1)) > 0) {
                    String temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
    }
    public static void Store_Program_Data_into_file() {
        try {
            File file = new File("Stored_Data.txt");
            file.delete();
            boolean fileCreated = file.createNewFile();

            if (fileCreated) {
                FileWriter dataWriter = new FileWriter(file);

                dataWriter.write(String.join(",", Q1) + "\n");
                dataWriter.write(String.join(",", Q2) + "\n");
                dataWriter.write(String.join(",", Q3));

                dataWriter.close();
                System.out.println("Data saved successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void Load_Program_Data_from_file() {
        try {
            String filePath = "Stored_Data.txt";
            File file = new File(filePath);

            Scanner file_reader = new Scanner(file);
            int line_count = 1;

            while (file_reader.hasNextLine()) {
                String row_data = file_reader.nextLine();
                String customer_data = row_data.replaceAll(",","");
                System.out.print("Queue " + line_count + " : ");
                if (line_count == 1) {
                    for (int i = 0; i < Q1.length; i++) {
                        Q1[i] = Character.toString(customer_data.charAt(i));
                    }
                } else if (line_count == 2) {
                    for (int i = 0; i < Q2.length; i++) {
                        Q2[i] = Character.toString(customer_data.charAt(i));
                    }
                } else if (line_count == 3) {
                    for (int i = 0; i < Q3.length; i++) {
                        Q3[i] = Character.toString(customer_data.charAt(i));
                    }
                }

                for (int i = 0; i < customer_data.length(); i++) {
                    System.out.print(customer_data.charAt(i));
                    if (i < customer_data.length() - 1) {
                        System.out.print(",");
                    }
                }

                System.out.println();
                line_count++;
            }
            file_reader.close();
            System.out.println("Successfully Loaded");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void View_Remaining_burgers_Stock(){
        System.out.println("Remaining Burger stock : " +stock);
    }
    public static void  Add_burgers_to_Stock(){
        System.out.println();
        System.out.println("--------------------ADD BURGERS TO STOCK-------------------");
        System.out.println();

        Scanner input_2=new Scanner(System.in);
        System.out.println("Enter the number of Burgers to add to stock ");
        int Burger_No=input_2.nextInt();
        if (Burger_No<stock && (Burger_No+stock)<=50){
            stock=stock+Burger_No;
            System.out.println(Burger_No + " add the Burgers Stock.Total is " + stock);
        }else if (Burger_No>stock){
            System.out.println("You cant add more Burgers because stock is full or Can not exceed 50");
        }
        else {
            System.out.println("You cant add more.because stock is full");
        }

    }
    private static void viewIncomeOfEachQueue() {
        System.out.println("Queue 1 income : "+ income_q1+ " $");
        System.out.println("Queue 2 income : "+ income_q2+ " $");
        System.out.println("Queue 3 income : "+ income_q3+ " $");
        System.out.println("Total income : "+ (income_q1+income_q2+income_q3)+ " $");
    }
}



