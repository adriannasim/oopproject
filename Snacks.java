import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Snacks extends FoodAndBeverage implements Serializable {
    private boolean partyPack;

    // -----------------------------------CONSTRUCTOR----------------------------------------
    // NO-ARG CONSTRUCTOR
    Snacks() {

    }

    // PARAMETERIZED CONSTRUCTOR
    Snacks(boolean partyPack) {

    }

    Snacks(String foodName, double foodPrice, int stockQty, boolean partyPack) {
        super(foodName, foodPrice, stockQty);
        this.partyPack = partyPack;
    }

    Snacks(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty, boolean partyPack) {
        super(foodId, foodName, foodPrice, purchaseQty, stockQty);
        this.partyPack = partyPack;
    }

    // ------------------------------------METHOD-----------------------------------------
    // UPDATE METHOD
    public void setPartyPack(boolean partyPack) {
        this.partyPack = partyPack;
    }

    // READ METHOD
    public boolean getPartyPack() {
        return partyPack;
    }

    // DISPLAY METHOD
    public String toString() {
        String partyPackStatus = partyPack ? "true" : "false";
        return super.toString() + "\nParty pack: " + partyPackStatus;
    }

    public String displayToCust() {
        String partyPackStatus = partyPack ? "Yes" : "No";
        return super.displayToCust() +
                "\nParty Pack    : " + partyPackStatus;
    }

    // CALCULATION METHOD
    public double calculatePrice() {
        return foodPrice * purchaseQty;
    }

    public void writePurchaseFnB(FoodAndBeverage fnb, Login login) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseFnb.txt", true))) {
            writer.write(login.getUsername() + "||" + fnb.getClass() + "||"
                    + fnb.getFoodName() + "||" + fnb.calculatePrice() + "||"
                    + fnb.getPurchaseQty() + "||" + partyPack);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Snacks> readFromFile(String filename) throws Exception {
        File file = new File(filename);
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();

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
                     boolean partyPack = inputFile.nextBoolean();
                     inputFile.nextLine();
                     snacksList.add(new Snacks(foodId, foodName, foodPrice, purchaseQty, stockQty, partyPack));
                 }
           }
       }
       return snacksList;
    }

     public static boolean writeIntoFile(String filename, ArrayList<Snacks> snacksList)  throws FileNotFoundException {
        boolean write = false;

        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<snacksList.size(); i++){
                    output.write(snacksList.get(i).getFoodId() + "\n");
                    output.write(snacksList.get(i).getFoodName() + "\n");
                    output.write(snacksList.get(i).getFoodPrice() + "\n");
                    output.write(snacksList.get(i).getPurchaseQty() + "\n");
                    output.write(snacksList.get(i).getStockQty() + "\n");
                    output.write(snacksList.get(i).getPartyPack() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("\nERROR WRITING TO THE FILE.\n");
        }
        return write;
    }

    public ArrayList<Snacks> getSnacksList() throws Exception{
        ArrayList<Snacks> snacksList = readFromFile("snacksFile.txt");
        return snacksList;
    }

    public void snacksModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
        ArrayList<Snacks> snacksList = getSnacksList();

        while (cont) {
            System.out.println("==================================================");
            System.out.println("   Snacks Information Modification");
            System.out.println("==================================================");
            System.out.println("1. View snacks");
            System.out.println("2. Update existing snacks");
            System.out.println("3. Add a new snacks");
            System.out.println("4. Delete an existing snacks");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");

    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    viewSnacks();
                } else if (userInput.equals("2")) {
                    updateSnacks(scanner);
                } else if (userInput.equals("3")) {
                    addSnacks(scanner);
                } else if (userInput.equals("4")) {
                    deleteSnacks(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE ENETER (1/2/3/4/#).\n");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    public void viewSnacks() throws Exception{
        ArrayList<Snacks> snacksList = getSnacksList();
        for (int i=0; i< snacksList.size(); i++){
            System.out.println(snacksList.get(i).toString() + "\n");
        }

    }

    //-----------------------------------------ADD SNACKS INFORMATION-------------------------------------------- 

    public void addSnacks(Scanner scanner) throws Exception{
        String foodName;
        double foodPrice;
        int stockQty = 0;
        boolean partyPack = false;
        boolean added = false;
        String userInput;
        ArrayList<Snacks> snacksList = getSnacksList();
    
        System.out.println("==================================================");
        System.out.println("              Add Snacks Information");
        System.out.println("==================================================");

        System.out.print("Enter snacks name > ");
        foodName = scanner.nextLine();
    
        System.out.print("Enter snacks price > ");
        foodPrice = BackendStaff.validateDoubleInput(scanner, "Enter snacks price > ");
    
        System.out.print("Enter stock qty > ");
        stockQty = BackendStaff.validateIntegerInput(scanner, "Enter stock qty > ");
    
        System.out.print("Is it a party pack? (Y-Yes/N-No) > ");
        userInput = BackendStaff.validateYNInput(scanner, "Is it a party pack? (Y-Yes/N-No) > ");
            
        if (userInput.equalsIgnoreCase("Y")) {
            partyPack = true;
        } else {
            partyPack = false;
        } 
    
        System.out.print("Do you confirm? (Y-Yes/N-No) > ");
        userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            Snacks tempSnacks = new Snacks(foodName, foodPrice, stockQty, partyPack);
            snacksList.add(tempSnacks);
            added = writeIntoFile("snacksFile.txt", snacksList);
    
            if (added) {
                System.out.println("SNACKS HAS ADDED");
            } else {
                System.out.println("\nFAILED TO ADD SNACKS\n");
            }

        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
        
    }

    //----------------------------------------------UPDATE SNACKS------------------------------------------------

    public void updateSnacks(Scanner scanner) throws Exception{
        String foodId;
        String foodName;
        double foodPrice;
        int stockQty = 0; 
        int index = 0;
        boolean found = false;
        boolean cont = true;
        String userInput;
        String userInput2;
        String confirm;
        boolean updated = false;
        boolean invalidFormat = false;
        String sign;
        String numericPart;
         ArrayList<Snacks> snacksList = getSnacksList();

        System.out.println("==================================================");
        System.out.println("           Update Snacks Information");
        System.out.println("==================================================");

        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            foodId = scanner.nextLine();
            
            if (foodId.equals("#"))
                break;

            for (int i = 0; i < snacksList.size(); i++) {
                if (foodId.equals(snacksList.get(i).getFoodId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                do{
                    System.out.println("==================================================");
                    System.out.println("The field that can be updated :");
                    System.out.println("1. Snacks name ");
                    System.out.println("2. Food price");
                    System.out.println("3. Stock qty");
                    System.out.println("4. Make it a party pack or vice versa");
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
                                snacksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("SNACKS NAME HAS UPDATED");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE FOOD MENU.\n");
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
                                snacksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("SNACKS PRICE HAS UPDATED.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE SNACKS PRICE\n");
                                }
                            }else{
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                            
                        } else if (userInput.equals("3")) {
                            do{
                                System.out.print("Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > " );
                                userInput2 = scanner.nextLine();
                                if (userInput2.startsWith("+") || userInput2.startsWith("-")) {
                                    sign = userInput2.substring(0, 1);
                                    numericPart = userInput2.substring(1);
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nINVALID FORMAT. PLEASE ENETER IN FORMAT (+100/-100).\n");
                                        invalidFormat = true;
                                    }
                                } else {
                                    sign = "+";
                                    numericPart = userInput2;
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nINVALID FORMAT. PLEASE ENETER IN FORMAT (+100/-100).\n");
                                        invalidFormat = true;
                                    }
                                }
                            }while (invalidFormat == true);
                       
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                boolean success = snacksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated && success){
                                    System.out.println("SNACKS STOCK QTY HAS UPDATED");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE SNACKS STOCK QTY.\n");
                                }
                            }else{
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                            
                        } else if (userInput.equals("4")) {
                            System.out.print("A party pack? (Y-Yes/N-No) > ");
                            userInput2 = BackendStaff.validateYNInput(scanner, "A party pack? (Y-Yes/N-No) > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");

                            if (confirm.equalsIgnoreCase("Y")){
                                if(userInput2.equalsIgnoreCase("Y")){
                                    snacksList.get(index).setPartyPack(true);
                                }else{
                                    snacksList.get(index).setPartyPack(false);
                                }
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("PARTY PACK SETTING HAS UPDATED.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE PARTY PACK SETTING.\n");
                                }
                            }else{
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                        } else if (userInput.equals("#")) {
                            break;
                            
                        } else {
                             System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4).\n");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = BackendStaff.validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                }while(cont==true);
            }else{
                System.out.println("\nSNACKS NOT FOUND. PLEAE SEARCH AGAIN.\n");
            }
            
        }while(!found);
 
    }
    
    //----------------------------------------------DELETE SNACKS------------------------------------------------
    
    public void deleteSnacks(Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        int index = 0;
        ArrayList<Snacks> snacksList = getSnacksList();
        
        System.out.println("==================================================");
        System.out.println("              Delete Snacks Information");
        System.out.println("==================================================");
        
        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < snacksList.size(); i++) {
                if (userInput.equals(snacksList.get(i).getFoodId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the snacks information as shown below? ");
                System.out.println();
                System.out.println(snacksList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        snacksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("snacksFile.txt", snacksList);

                        if (deleted) {
                             System.out.println("SNACKS HAS REMOVED.");
                        }else{
                             System.out.println("\nFAILED TO REMOVE THE SNACKS.\n");

                        }

                    } else {
                         System.out.println("\nMODIFICATION CANCELLED.\n");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("\nSNACKS NOT FOUND. PLEASE SEARCH AGAIN.");
            } 
        } while (!found);
    
    }

}
