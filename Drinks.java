import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Drinks extends FoodAndBeverage implements Serializable {
    private String temperature;
    private String size;
    private boolean ice;

    // -----------------------------------CONSTRUCTOR----------------------------------------
    // NO-ARG CONSTRUCTOR
    Drinks() {
        this("NotDefined", 0, 0);
    }

    // PARAMETERIZED CONSTRUCTOR
    Drinks(String foodName, double foodPrice, int stockQty) {
        super(foodName, foodPrice, stockQty);
    }

    Drinks(String foodName, double foodPrice, int stockQty, String temperature, String size) {
        super(foodName, foodPrice, stockQty);
        this.temperature = temperature;
        this.size = size;
    }

    Drinks(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty, String temperature,
            String size, boolean ice) {
        super(foodId, foodName, foodPrice, purchaseQty, stockQty);
        this.temperature = temperature;
        this.size = size;
        this.ice = ice;
    }
    // ------------------------------------METHOD-----------------------------------------

    // UPDATE METHOD
    public void setSize(String size) {
        this.size = size;
    }

    public void setIce(boolean ice) {
        this.ice = ice;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    // READ METHOD
    public String getSize() {
        return size;
    }

    public String getTemperature() {
        return temperature;
    }

    public boolean getIce() {
        return ice;
    }

    // DISPLAY METHOD
    public String displayToCust() {
        String iceStatus = ice ? "Yes" : "No";
        return super.displayToCust() +
                "\nIce           : " + iceStatus +
                "\nTemperature   : " + temperature +
                "\nSize          : " + size;
    }

    // CALCULATION METHOD
    public double calculatePrice() {
        double sizePrice;
        if (size.equalsIgnoreCase("Medium")) {
            sizePrice = 1.2;
        } else if (size.equalsIgnoreCase("Big")) {
            sizePrice = 2;
        } else {
            sizePrice = 0;
        }
        return (foodPrice + sizePrice) * purchaseQty;
    }

     public static ArrayList<Drinks> readFromFile(String filename) throws Exception {
        File file = new File(filename);
        ArrayList<Drinks> drinksList = new  ArrayList<Drinks>();

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                     String foodId = inputFile.nextLine();
                     String foodName= inputFile.nextLine();
                     double foodPrice= inputFile.nextDouble();
                     inputFile.nextLine();
                     int purchaseQty = inputFile.nextInt();
                     inputFile.nextLine();
                     int stockQty = inputFile.nextInt();
                     inputFile.nextLine();
                     String temperature = inputFile.nextLine();
                     String size = inputFile.nextLine();
                     boolean ice = inputFile.nextBoolean();
                     inputFile.nextLine();
                     drinksList.add(new  Drinks(foodId, foodName, foodPrice, purchaseQty, stockQty, temperature, size, ice));
                 }
           }
       }
       return drinksList;
    }

    public ArrayList<Drinks> getDrinksList() throws Exception{
        ArrayList<Drinks> drinksList = readFromFile("drinksFile.txt");
        return drinksList;
    }

    public static boolean writeIntoFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) {
        boolean write = false;

        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<drinksList.size(); i++){
                    output.write(drinksList.get(i).getFoodId() + "\n");
                    output.write(drinksList.get(i).getFoodName() + "\n");
                    output.write(drinksList.get(i).getFoodPrice() + "\n");
                    output.write(drinksList.get(i).getPurchaseQty() + "\n");
                    output.write(drinksList.get(i).getStockQty() + "\n");
                    output.write(drinksList.get(i).getTemperature() + "\n");
                    output.write(drinksList.get(i).getSize() + "\n");
                    output.write(drinksList.get(i).getIce() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.out.println("\nERROR WRITING TO THE FILE\n");
        }
        return write;
    }
    
    
    public void writePurchaseFnB(FoodAndBeverage fnb, Login login) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseFnb.txt", true))) {
            writer.write(login.getUsername() + "||" + fnb.getClass() + "||"
                    + fnb.getFoodName() + "||" + fnb.calculatePrice() + "||"
                    + fnb.getPurchaseQty());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drinksModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
        ArrayList<Drinks> drinksList = getDrinksList();
    
        while (cont) {
            System.out.println("==================================================");
            System.out.println("   Drinks Information Modification");
            System.out.println("==================================================");
            System.out.println("1. View drinks");
            System.out.println("2. Update existing drinks");
            System.out.println("3. Add a new drinks");
            System.out.println("4. Delete an existing drinks");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");
    
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    viewDrinks();
                } else if (userInput.equals("2")) {
                    updateDrinks(scanner);
                } else if (userInput.equals("3")) {
                    addDrinks(scanner);
                } else if (userInput.equals("4")) {
                    deleteDrinks(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4/#).\n");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }
    
    public void viewDrinks() throws Exception{
        ArrayList<Drinks> drinksList = getDrinksList();
        for (int i=0; i< drinksList.size(); i++){
            System.out.println(drinksList.get(i).toString() + "\n");
        }

    }
    //-----------------------------------------ADD DRINKS INFORMATION-------------------------------------------- 

    public void addDrinks(Scanner scanner) throws Exception{
        String foodName;
        double foodPrice;
        int stockQty;
        boolean added = false;
        String userInput;
        Drinks drinks = new Drinks();
        ArrayList<Drinks> drinksList = getDrinksList();

        System.out.println("==================================================");
        System.out.println("              Add Drinks Information");
        System.out.println("==================================================");
    
        System.out.print("Enter drinks name > ");
        foodName = scanner.nextLine();
    
        System.out.print("Enter drinks price > ");
        foodPrice = BackendStaff.validateDoubleInput(scanner, "Enter drinks price > ");
    
        System.out.print("Enter stock qty > ");
        stockQty = BackendStaff.validateIntegerInput(scanner, "Enter stock qty > ");
    
        System.out.print("Do you confirm? (Y-Yes/N-No) > ");
        userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
    
        if (userInput.equalsIgnoreCase("Y")) {
            Drinks tempDrinks = new Drinks(foodName, foodPrice, stockQty);
            drinksList.add(tempDrinks);
            added = writeIntoFile("drinksFile.txt", drinksList, drinks);
    
            if (added) {
                System.out.println("DRINKS HAS ADDED");
            } else {
                System.out.println("\nFAILED TO ADD DRINKS.\n");
            }
        } else {
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
    }
    
    //----------------------------------------------UPDATE DRINKS----------------------------------------------
    
    public void updateDrinks(Scanner scanner) throws Exception{
        String foodId;
        String foodName;
        double foodPrice;
        int stockQty = 0; 
        int index = 0;
        boolean found = false;
        boolean cont = true;
        String userInput;
        String userInput3;
        boolean updated = false;
        Drinks obj = new Drinks();
        String sign;
        String numericPart;
        boolean invalidFormat = false;
        String confirm;
        ArrayList<Drinks> drinksList = getDrinksList();

        System.out.println("==================================================");
        System.out.println("           Update Drinks Information");
        System.out.println("==================================================");

        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            foodId = scanner.nextLine();

		if (foodId.equals("#"))
                break;
                
            for (int i = 0; i < drinksList.size(); i++) {
                if (foodId.equals(drinksList.get(i).getFoodId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                do{
                    System.out.println("==================================================");
                    System.out.println("The field that can be updated :");
                    System.out.println("1. Drinks name ");
                    System.out.println("2. Food price");
                    System.out.println("3. Stock qty");
                    System.out.println("* Press # to exit");
                    System.out.println("==================================================");

                    do {
                        System.out.print("Enter option in number stated above > ");
                        userInput = scanner.nextLine();

                        if (userInput.equals("1")) {
                            System.out.print("New snacks name > ");
                            foodName = scanner.nextLine();
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                drinksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("DRINKS NAME HAS UPDATED.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE DRINKS NAME.\n");
                                }
                            }else{
                                 System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                                
                        } else if (userInput.equals("2")) {
                            System.out.print("New price > ");
                            foodPrice = BackendStaff.validateDoubleInput(scanner, "New price > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                drinksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("DRINKS PRICE HAS UPDATED.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE DRINKS PRICE.\n");
                                }
                            }else{
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                                
                        } else if (userInput.equals("3")) {
                            do{
                                System.out.print("Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > " );
                                userInput3 = scanner.nextLine();
                                if (userInput3.startsWith("+") || userInput3.startsWith("-")) {
                                    sign = userInput3.substring(0, 1);
                                    numericPart = userInput3.substring(1);
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nINVALID FORMAT. PLEASE ENTER IN FORMAT (+100/-100).\n");
                                        invalidFormat = true;
                                    }
                                } else {
                                    sign = "+";
                                    numericPart = userInput3;
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nINVALID FORMAT. PLEASE ENTER IN FORMAT (+100/-100).\n");
                                        invalidFormat = true;
                                    }
                                }
                            }while (invalidFormat == true);
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                boolean success = drinksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated && success){
                                    System.out.println("DRINKS STOCK QTY HAS UPDATED.");
                                }else{
                                    System.out.println("\nIFAILED TO UPDATE DRINKS STOCK QTY.\n");
                                }
                            }else{
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                        } else if (userInput.equals("#")) {
                            break;
                                
                        } else {
                            System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/#).\n");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = BackendStaff.validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                }while(cont==true);
            }else{
                System.out.println("\nDRINKS NOT FOUND. PLEASE SEARCH AGAIN.\n");
            }
            
        }while(!found);
 
    }
    
    //----------------------------------------------DELETE DRINKS----------------------------------------------
    
     public void deleteDrinks(Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        Drinks obj = new Drinks();
        int index = 0;
        ArrayList<Drinks> drinksList = getDrinksList();

        System.out.println("==================================================");
        System.out.println("            Delete Drinks Information");
        System.out.println("==================================================");

        do {
            System.out.print("Enter the drinks id to search the drinks (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < drinksList.size(); i++) {
                if (userInput.equals(drinksList.get(i).getFoodId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the drinks information as shown below? ");
                System.out.println(drinksList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        drinksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("drinksFile.txt", drinksList, obj);

                        if (deleted) {
                             System.out.println("DRINKS HAS REMOVED.");
                        }else{
                            System.out.println("\nFAILED TO REMOVE THE DRINKS.\n");
                        }

                    } else {
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("\nDRINKS NOT FOUND. PLEASE SEARCH AGAIN.\n");
            } 
        } while (!found);
    
    }

}
