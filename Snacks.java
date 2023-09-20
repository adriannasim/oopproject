import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    Snacks(String foodId, String foodName, double foodPrice, int purchaseQty) {
        super(foodId, foodName, foodPrice, purchaseQty);
    }

    // ------------------------------------METHOD-----------------------------------------
    // SETTER
    public void setPartyPack(boolean partyPack) {
        this.partyPack = partyPack;
    }

    // GETTER
    public boolean getPartyPack() {
        return partyPack;
    }

    // TO STRING
    public String toString() {
        String partyPackStatus = partyPack ? "true" : "false";
        return super.toString() + "\nParty pack: " + partyPackStatus;
    }

    public String displayToCust() {
        String partyPackStatus = partyPack ? "Yes" : "No";
        return super.displayToCust() +
                "\nParty Pack    : " + partyPackStatus;
    }

    public String displayToReport(){
        return String.format("Food Name \t Purchase Quantity \t Price(RM) \t Subtotal(RM)\n\n%-24s %-15s %6.2f %16.2f", foodName, purchaseQty, foodPrice, calculatePrice());
    }

    // CALCULATION METHOD
    public double calculatePrice() {
        return foodPrice * purchaseQty;
    }

    // WRITE PURCHASE INTO FILE
    public void writePurchaseFnB(FoodAndBeverage fnb, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseSnack.txt", true))) {
            writer.write(username + "||" +
                    fnb.getFoodId() + "||" + fnb.getClass() + "||"
                    + fnb.getFoodName() + "||" + fnb.getFoodPrice() + "||"
                    + fnb.getPurchaseQty() + "||" + fnb.calculatePrice());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // READ FROM FILE
    public static ArrayList<Snacks> readFromFile(String filename) throws Exception {
        File file = new File(filename);
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                while (inputFile.hasNext()) {
                    String foodId = inputFile.nextLine();
                    String foodName = inputFile.nextLine();
                    double foodPrice = Double.parseDouble(inputFile.nextLine());
                    int purchaseQty = Integer.parseInt(inputFile.nextLine());
                    int stockQty = Integer.parseInt(inputFile.nextLine());
                    boolean partyPack = Boolean.parseBoolean(inputFile.nextLine());
                    snacksList.add(new Snacks(foodId, foodName, foodPrice, purchaseQty, stockQty, partyPack));
                }
            }
        }
        return snacksList;
    }

    // WRITE INTO FILE
    public static boolean writeIntoFile(String filename, ArrayList<Snacks> snacksList) throws FileNotFoundException {
        boolean write = false;
        FileWriter fwrite = null;

        try {
            fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for (int i = 0; i < snacksList.size(); i++) {
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
        }finally {
            if (fwrite != null) {
                try {
                    fwrite.close();
                } catch (IOException e) {
                    
                }
            }
        }
        return write;
    }

    // GET SNACKS LIST
    public ArrayList<Snacks> getSnacksList() throws Exception {
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        snacksList = readFromFile("snacksFile.txt");
        return snacksList;
    }

    // SNACKS MODIFICATION (DRIVER)
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
                    for (int i = 0; i < snacksList.size(); i++) {
                        System.out.println(snacksList.get(i).toString());
                    }
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

            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
                    && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    // ----------------------------------------------VIEW SNACKS----------------------------------------------
    public void viewSnacks() throws Exception {
        ArrayList<Snacks> snacksList = getSnacksList();
        if (snacksList.size() == 0) {
            System.out.println("\nNO SNACKS IN THE RECORD.\n");
        }
        for (int i = 0; i < snacksList.size(); i++) {
            System.out.println(snacksList.get(i).toString() + "\n");
        }

    }

    // ----------------------------------------------ADD SNACKS----------------------------------------------
    public void addSnacks(Scanner scanner) throws Exception {
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

        if (userInput.equalsIgnoreCase("Y")) {
            Snacks tempSnacks = new Snacks(foodName, foodPrice, stockQty, partyPack);
            snacksList.add(tempSnacks);
            added = writeIntoFile("snacksFile.txt", snacksList);

            if (added) {
                System.out.println("\nSNACKS HAS ADDED\n");
            } else {
                System.out.println("\nFAILED TO ADD SNACKS\n");
            }

        } else {
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }

    }

    // ----------------------------------------------UPDATE SNACKS----------------------------------------------
    public void updateSnacks(Scanner scanner) throws Exception {
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
            if (found == true) {
                do {
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
                            if (confirm.equalsIgnoreCase("Y")) {
                                snacksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("\nSNACKS NAME HAS UPDATED\n");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE FOOD MENU.\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }

                        } else if (userInput.equals("2")) {
                            System.out.print("New price > ");
                            foodPrice = BackendStaff.validateDoubleInput(scanner, "New price > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if (confirm.equalsIgnoreCase("Y")) {
                                snacksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("\nSNACKS PRICE HAS UPDATED.\n");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE SNACKS PRICE\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }

                        } else if (userInput.equals("3")) {
                            do {
                                System.out.print(
                                        "Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > ");
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
                            } while (invalidFormat == true);

                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if (confirm.equalsIgnoreCase("Y")) {
                                boolean success = snacksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated && success){
                                    System.out.println("\nSNACKS STOCK QTY HAS UPDATED\n");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE SNACKS STOCK QTY.\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }

                        } else if (userInput.equals("4")) {
                            System.out.print("A party pack? (Y-Yes/N-No) > ");
                            userInput2 = BackendStaff.validateYNInput(scanner, "A party pack? (Y-Yes/N-No) > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");

                            if (confirm.equalsIgnoreCase("Y")) {
                                if (userInput2.equalsIgnoreCase("Y")) {
                                    snacksList.get(index).setPartyPack(true);
                                } else {
                                    snacksList.get(index).setPartyPack(false);
                                }
                                updated = writeIntoFile("snacksFile.txt", snacksList);
                                if(updated){
                                    System.out.println("\nPARTY PACK SETTING HAS UPDATED.\n");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE PARTY PACK SETTING.\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                        } else if (userInput.equals("#")) {
                            break;

                        } else {
                            System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4).\n");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
                            && !userInput.equals("4") && !userInput.equals("#"));
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = BackendStaff.validateYNInput(scanner,
                            "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")) {
                        cont = true;
                    } else {
                        cont = false;
                    }
                } while (cont == true);
            } else {
                System.out.println("\nSNACKS NOT FOUND. PLEAE SEARCH AGAIN.\n");
            }

        } while (!found);

    }

    // ----------------------------------------------DELETE SNACKS----------------------------------------------
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
            if (found == true) {
                System.out.println("Do you want to delete the snacks information as shown below? ");
                System.out.println();
                System.out.println(snacksList.get(index).toString());
                System.out.println();
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");

                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if (userInput2.equalsIgnoreCase("Y")) {
                        snacksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("snacksFile.txt", snacksList);

                        if (deleted) {
                             System.out.println("\nSNACKS HAS REMOVED.\n");
                        }else{
                             System.out.println("\nFAILED TO REMOVE THE SNACKS.\n");

                        }

                    } else {
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }
                } else {
                    found = false;
                }

            } else {
                System.out.println("The snacks is not found. Please search again.");
            }
        } while (!found);

    }

    // ==============================================================================================================
    // BUY FNB
    // ==============================================================================================================
    public ArrayList<FoodAndBeverage> buyFnb(ArrayList<FoodAndBeverage> fnbList, Scanner scanner)
            throws Exception {

        // --------------------------------------ScanFoodAndBeverageList----------------------------------------------
        Snacks snacks = new Snacks();
        ArrayList<Snacks> snacksList = snacks.getSnacksList();

        Drinks drinks = new Drinks();
        ArrayList<Drinks> drinksList = drinks.getDrinksList();
        // ------------------------------------------------------------------------------------------------------------

        boolean continueInput = true;
        boolean continueInputF = true;

        int userOpt1 = 0;
        // yesno1 - Continue to add more fnb, yesno2 - Continue to add more snacks,
        // yesno3 - Continue to add more drinks
        char yesno1, yesno2, yesno3;
        // cont1 - Make changes to fnb
        char cont1;

        int addFnb = 0;
        Snacks snackObj = new Snacks();
        Drinks drinkObj = new Drinks();

        do {
            // --------------------------------------SelectSnacksOrDrinks--------------------------------------------------
            System.out.println("1. Snacks");
            System.out.println("2. Drinks");

            do {
                try {
                    System.out.print("Enter your option > ");
                    addFnb = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);
            // ------------------------------------------------------------------------------------------------------------

            if (addFnb == 1) {

                // -----------------------------------------AddSnacks--------------------------------------------------------
                do {
                    fnbList = snackObj.addFnb(drinksList, snacksList, fnbList, scanner);
                    System.out.print("Would you like to buy more snacks? (Y - YES, OTHER - NO)  ");
                    yesno2 = scanner.next().charAt(0);
                } while (yesno2 == 'y' || yesno2 == 'Y');

                // Display Added Snacks
                System.out.println("\n============================");
                System.out.println("            Orders");
                System.out.println("============================");
                System.out.println();
                int z = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    System.out.println(z + ".");
                    System.out.println(fnbs.displayToCust());
                    System.out.println("Purchase Quantity >> " + fnbs.getPurchaseQty());
                    System.out.println("Total Amount      >> " + fnbs.calculatePrice());
                    z++;
                    System.out.println();
                    System.out.println("---------------------------------------------------------");
                    System.out.println();
                }
                // ----------------------------------------------------------------------------------------------------------

            } else if (addFnb == 2) {

                // -----------------------------------------AddDrinks--------------------------------------------------------
                do {
                    fnbList = drinkObj.addFnb(drinksList, snacksList, fnbList, scanner);
                    System.out.print("Would you like to buy more drinks? (Y - YES, OTHER - NO)  ");
                    yesno3 = scanner.next().charAt(0);
                } while (yesno3 == 'y' || yesno3 == 'Y');

                // Display Added Drinks
                System.out.println("\n============================");
                System.out.println("            Orders");
                System.out.println("============================");
                System.out.println();
                int y = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    System.out.println(y + ".");
                    System.out.println(fnbs.displayToCust());
                    System.out.println("Purchase Quantity >> " + fnbs.getPurchaseQty());
                    System.out.println("Total Amount      >> " + fnbs.calculatePrice());
                    y++;
                    System.out.println();
                    System.out.println("---------------------------------------------------------");
                    System.out.println();
                }
                // ----------------------------------------------------------------------------------------------------------

            } else {

                System.out.println("Invalid Input.");

            }

            System.out.print("Would you like to add more orders? (Y - YES, other - NO)  ");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');

        do {
            // --------------------------------OrderSummary------------------------------------------------------------------
            // Noted : Every Modification, display once order summary
            System.out.println("\n============================");
            System.out.println("        Order Summary");
            System.out.println("============================");
            System.out.println();
            int y = 1;
            for (FoodAndBeverage fnbs : fnbList) {
                System.out.println(y + ".");
                System.out.println(fnbs.displayToCust());
                System.out.println("Purchase Quantity >>  " + fnbs.getPurchaseQty());
                System.out.println("Total Amount      >>  " + fnbs.calculatePrice());
                y++;
                System.out.println();
                System.out.println("---------------------------------------------------------");
                System.out.println();
            }
            // -----------------------------------------------------------------------------------------------------------------

            // ------------------------------------------MakeChangesToOrder-----------------------------------------------------
            System.out.print("Would you like to make changes to orders? (Y - YES, other - NO) >> ");
            cont1 = scanner.next().charAt(0);

            if (cont1 == 'y' || cont1 == 'Y') {

                do {
                    System.out.println("=========================================================");
                    System.out.println("                       Modification");
                    System.out.println("=========================================================");
                    System.out.println("1. Delete orders ");
                    System.out.println("2. Edit orders ");
                    System.out.println();
                    System.out.println("=========================================================");
                    System.out.println();

                    do {
                        try {
                            System.out.print("Enter your option > ");
                            userOpt1 = scanner.nextInt();

                            continueInputF = false;
                        } catch (InputMismatchException ex) {
                            System.out.println("Incorrect Input. Please try agin. ");
                            scanner.nextLine();
                        }
                    } while (continueInputF);

                } while (userOpt1 != 1 && userOpt1 != 2);

                switch (userOpt1) {
                    case 1:
                        fnbList = dltFnb(fnbList, scanner);
                        break;
                    case 2:
                        fnbList = editFnb(drinksList, fnbList, scanner);
                        break;
                    default:
                        System.out.println("Invalid. ");
                        break;
                }

            }

        } while (cont1 == 'y' || cont1 == 'Y');
        // -----------------------------------------------------------------------------------------------------------------

        return fnbList;

    }

    // ------------------------------------------------ADDSNACKS-----------------------------------------------------------
    // User can buy one or many different and same snacks
    // ---------------------------------------------------------------------------------------------------------------------
    public ArrayList<FoodAndBeverage> addFnb(ArrayList<Drinks> drinksList, ArrayList<Snacks> snacksList,
            ArrayList<FoodAndBeverage> fnbList,
            Scanner scanner) throws Exception {

        Snacks snack;
        int addSnacks = 0;
        int snackQty = 0;

        boolean continueInputS = true;
        boolean continueInputSQ = true;

        boolean validSnack = true;
        boolean foundSnack = false;

        // ----------------------------------------------DisplaySnacks--------------------------------------------------------------
        System.out.println("=========================================================");
        System.out.println("                       Snacks Available");
        System.out.println("=========================================================");
        System.out.println();
        for (int i = 0; i < snacksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(snacksList.get(i).toString());
            System.out.println();
            System.out.println("---------------------------------------------------------");
            System.out.println();
        }
        System.out.println("=========================================================");
        System.out.println("\nWhich snack would you like to add?\n\n");
        // ------------------------------------------------------------------------------------------------------------------------

        // -----------------------------------------------SelectSnacks--------------------------------------------------------------
        do {

            do {
                try {
                    System.out.print("Enter your option > ");
                    addSnacks = scanner.nextInt();

                    continueInputS = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputS);

            if (addSnacks > snacksList.size()) {
                System.out.println("Snack not found. Please try again. ");
                validSnack = false;
            } else {
                validSnack = true;
            }

        } while (!validSnack);

        snack = snacksList.get(addSnacks - 1);
        // ------------------------------------------------------------------------------------------------------------------------

        if (snack.getStockQty() != 0) {

            do {

                // -------------------------------------------EnterQty-------------------------------------------------------------
                do {
                    try {
                        System.out.print("Enter quantity > ");
                        snackQty = scanner.nextInt();

                        continueInputSQ = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputSQ);

                if (!snack.checkStockQty(snackQty)) {

                    // Purchase quantity > stock quantity
                    System.out.print("Sorry, we have only " + snack.getStockQty() + " for " + snack.getFoodName());
                    System.out.print(". Please enter again.\n");

                }

            } while (!snack.checkStockQty(snackQty));

            // Added snack list has found the same item
            for (FoodAndBeverage fnbs : fnbList) {
                if (fnbs.getFoodId().equals(snack.getFoodId())) {
                    fnbs.calculateStockQty(snackQty);
                    fnbs.addPurchaseQty(snackQty);
                    foundSnack = true;
                }
            }

            // Added snack list does not found the same item
            if (!foundSnack) {
                snack.calculateStockQty(snackQty);
                snack.setPurchaseQty(snackQty);
                fnbList.add(snack);
            }
            // ---------------------------------------------------------------------------------------------------------------------

        } else {
            System.out.println("Sorry, we have out of stock for " + snack.getFoodName() + ".");
        }

        return fnbList;
    }

    // ---------------------------------------DLTFNB----------------------------------------------------------------------
    // User can buy delete one or many orders
    // -------------------------------------------------------------------------------------------------------------------
    public ArrayList<FoodAndBeverage> dltFnb(ArrayList<FoodAndBeverage> fnbList, Scanner scanner)
            throws Exception {
        int dltFnbOpt = 0;
        char confirmDlt;
        boolean continueInputD = true;
        boolean validDlt = true;

        do {

            // --------------------------------------SelectItem----------------------------------------------------------------
            do {
                try {
                    System.out.print("Select the item you would like to delete : ");
                    dltFnbOpt = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputD);

            if (dltFnbOpt > fnbList.size()) {
                System.out.println("Order not found. Please try again. ");
                validDlt = false;
            } else {
                validDlt = true;
            }

        } while (!validDlt);
        // --------------------------------------------------------------------------------------------------------------------

        System.out.println("\n============================");
        System.out.println("            Order");
        System.out.println("============================");
        System.out.println(fnbList.get(dltFnbOpt - 1).displayToCust());
        System.out.println("Purchase Quantity >> " + fnbList.get(dltFnbOpt - 1).getPurchaseQty());
        System.out.println("Total Amount      >> " + fnbList.get(dltFnbOpt - 1).calculatePrice());
        System.out.print("\nAre you sure to delete this order? (Y - YES, other - NO) >> ");
        confirmDlt = scanner.next().charAt(0);
        confirmDlt = Character.toUpperCase(confirmDlt);

        if (confirmDlt == 'Y') {
            fnbList.remove(dltFnbOpt - 1);
            System.out.println("Delete successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }

        return fnbList;

    }

    // --------------------------------------EDITFNB----------------------------------------------------------------------
    public ArrayList<FoodAndBeverage> editFnb(ArrayList<Drinks> drinksList, ArrayList<FoodAndBeverage> fnbList,
            Scanner scanner) throws Exception {

        int editFnbOpt = 0;
        int fnbIndex = 0;
        int editOpt = 0;

        boolean continueInput = true;
        boolean continueInputE = true;
        boolean continueInputEQ = true;
        boolean continueInputET = true;
        boolean validEdit = true;
        boolean isDrink = false;

        char confirmEdit;
        int editQty = 0;
        char editSize;
        char editIce;
        int editTemp = 0;
        Drinks tempDrink;

        do {

            do {
                try {
                    System.out.print("Select the item you would like to modify : ");
                    editFnbOpt = scanner.nextInt();

                    continueInputE = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputE);

            if (editFnbOpt > fnbList.size()) {
                System.out.println("Order not found. Please try again. ");
                validEdit = false;
            } else {
                validEdit = true;
            }

        } while (!validEdit);

        fnbIndex = editFnbOpt - 1;
        System.out.println("\n============================");
        System.out.println("            Order");
        System.out.println("============================");
        System.out.println(fnbList.get(fnbIndex).displayToCust());
        System.out.println("Purchase Quantity >> " + fnbList.get(fnbIndex).getPurchaseQty());
        System.out.println("Total Amount      >> " + fnbList.get(fnbIndex).calculatePrice());
        System.out.print("\nAre you sure to edit this order? (Y - YES, other - NO) >> ");
        confirmEdit = scanner.next().charAt(0);
        confirmEdit = Character.toUpperCase(confirmEdit);

        if (confirmEdit == 'Y') {
            System.out.println("1. Quantity");
            for (Drinks drink : drinksList) {
                if (fnbList.get(fnbIndex).getFoodId().equals(drink.getFoodId())) {
                    System.out.println("2. Size (For Drinks Only)");
                    System.out.println("3. Temperature (For Drinks Only)");
                    System.out.println("4. Ice (For Cold Drinks Only)");
                    isDrink = true;
                }
            }

            do {
                try {
                    System.out.print("Enter your option > ");
                    editOpt = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);

            if (editOpt == 1) {

                // Edit Qty
                if (fnbList.get(fnbIndex).getStockQty() != 0) {

                    do {

                        do {
                            try {
                                System.out.print("Enter quantity > ");
                                editQty = scanner.nextInt();

                                continueInputEQ = false;
                            } catch (InputMismatchException ex) {
                                System.out.println("Incorrect Input. Please try agin. ");
                                scanner.nextLine();
                            }
                        } while (continueInputEQ);

                        if (fnbList.get(fnbIndex).checkStockQty(editQty)) {

                            if (editQty > fnbList.get(fnbIndex).getPurchaseQty()) {

                                // Example: Get four items, and add one more item, become five items
                                int tempQty1 = fnbList.get(fnbIndex).getPurchaseQty();
                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                fnbList.get(fnbIndex).calculateStockQty(editQty - tempQty1);
                                System.out.println("Edit successfully. ");

                            } else if (editQty < fnbList.get(fnbIndex).getPurchaseQty()) {

                                // Example: Get four items, and put back one item, left three items
                                int tempQty2 = fnbList.get(fnbIndex).getPurchaseQty();
                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                fnbList.get(fnbIndex).addStockQty(tempQty2 - editQty);
                                System.out.println("Edit successfully. ");

                            } else {

                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                System.out.println("Edit successfully. ");

                            }

                        } else {
                            System.out.println("Sorry, we have only " + fnbList.get(fnbIndex).getStockQty() + " "
                                    + fnbList.get(fnbIndex).getFoodName());
                        }

                    } while (!fnbList.get(fnbIndex).checkStockQty(editQty));

                } else {
                    System.out.println("Sorry, we have out of stocks.");
                }

            } else if (editOpt == 2) {

                // Edit size
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    System.out.println("\n============================");
                    System.out.println("           Size");
                    System.out.println("============================");
                    System.out.println("1. Small - S");
                    System.out.println("2. Medium - M");
                    System.out.println("3. Big - B");

                    do {

                        System.out.print("Enter (S, M, B) > ");
                        editSize = scanner.next().charAt(0);
                        editSize = Character.toUpperCase(editSize);

                    } while (editSize != 'S' && editSize != 'M' && editSize != 'B');

                    switch (editSize) {
                        case 'S':
                            tempDrink.setSize("Small");
                            break;
                        case 'M':
                            tempDrink.setSize("Medium");
                            break;
                        case 'B':
                            tempDrink.setSize("Big");
                            break;
                        default:
                            System.out.println("Invalid.");
                            break;
                    }
                }

            } else if (editOpt == 3) {

                // Edit temperature
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    do {

                        System.out.println("\n============================");
                        System.out.println("        Temperature");
                        System.out.println("============================");
                        System.out.println("1. Cold");
                        System.out.println("2. Hot");
                        do {
                            try {
                                System.out.print("Enter (1 / 2) > ");
                                editTemp = scanner.nextInt();

                                continueInputET = false;
                            } catch (InputMismatchException ex) {
                                System.out.println("Incorrect Input. Please try agin. ");
                                scanner.nextLine();
                            }
                        } while (continueInputET);

                    } while (editTemp != 1 && editTemp != 2);

                    if (editTemp == 1) {

                        // Set cold
                        tempDrink.setTemperature("Cold");

                    } else if (editTemp == 2) {

                        // Set hot
                        tempDrink.setIce(false);
                        tempDrink.setTemperature("Hot");

                    }

                }

            } else if (editOpt == 4) {

                // Edit Iced
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    System.out.print("Y - ICED, other - NO ICED");
                    editIce = scanner.next().charAt(0);
                    editIce = Character.toUpperCase(editIce);

                    if (editIce == 'Y') {

                        if (tempDrink.getTemperature().equalsIgnoreCase("Cold")) {

                            tempDrink.setIce(true);

                        }

                    }

                }

            } else {
                System.out.println("Invalid Option.");
            }

        }

        return fnbList;

    }

}
