import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    Drinks(String foodId, String foodName, double foodPrice, int purchaseQty) {
        super(foodId, foodName, foodPrice, purchaseQty);
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

    public String displayToReport(){
        return super.displayToCust();
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
        ArrayList<Drinks> drinksList = new ArrayList<Drinks>();

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                while (inputFile.hasNext()) {
                    String foodId = inputFile.nextLine();
                    String foodName = inputFile.nextLine();
                    double foodPrice = inputFile.nextDouble();
                    inputFile.nextLine();
                    int purchaseQty = inputFile.nextInt();
                    inputFile.nextLine();
                    int stockQty = inputFile.nextInt();
                    inputFile.nextLine();
                    String temperature = inputFile.nextLine();
                    String size = inputFile.nextLine();
                    boolean ice = inputFile.nextBoolean();
                    inputFile.nextLine();
                    drinksList.add(
                            new Drinks(foodId, foodName, foodPrice, purchaseQty, stockQty, temperature, size, ice));
                }
            }
        }
        return drinksList;
    }

    public ArrayList<Drinks> getDrinksList() throws Exception {
        ArrayList<Drinks> drinksList = readFromFile("drinksFile.txt");
        return drinksList;
    }

    public static boolean writeIntoFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) {
        boolean write = false;

        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for (int i = 0; i < drinksList.size(); i++) {
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

    public void writePurchaseFnB(FoodAndBeverage fnb, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseDrink.txt", true))) {
            writer.write(username + "||" +
                    fnb.getFoodId() + "||" + fnb.getClass() + "||"
                    + fnb.getFoodName() + "||" + fnb.calculatePrice() + "||"
                    + fnb.getPurchaseQty() + "||" + fnb.getFoodPrice());
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
                    for(int i=0; i<drinksList.size(); i++){
                        System.out.println(drinksList.get(i).toString());
                    }
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

            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
                    && !userInput.equals("4") && !userInput.equals("#"));
        }
    }
    
    //-----------------------------------------ADD DRINKS INFORMATION-------------------------------------------- 

    public void addDrinks(Scanner scanner) throws Exception {
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

    // ----------------------------------------------UPDATE
    // DRINKS----------------------------------------------

    public void updateDrinks(Scanner scanner) throws Exception {
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
            if (found == true) {
                do {
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
                            if (confirm.equalsIgnoreCase("Y")) {
                                drinksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("Food name has updated.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE DRINKS NAME.\n");
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
                                drinksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("Food price has updated.");
                                }else{
                                    System.out.println("\nFAILED TO UPDATE DRINKS PRICE.\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }

                        } else if (userInput.equals("3")) {
                            do {
                                System.out.print(
                                        "Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > ");
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
                            } while (invalidFormat == true);
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if (confirm.equalsIgnoreCase("Y")) {
                                boolean success = drinksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated && success){
                                    System.out.println("Food stock qty has updated.");
                                }else{
                                    System.out.println("\nIFAILED TO UPDATE DRINKS STOCK QTY.\n");
                                }
                            } else {
                                System.out.println("\nMODIFICATION CANCELLED.\n");
                            }
                        } else if (userInput.equals("#")) {
                            break;

                        } else {
                            System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/#).\n");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
                            && !userInput.equals("#"));
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
                System.out.println("\nDRINKS NOT FOUND. PLEASE SEARCH AGAIN.\n");
            }

        } while (!found);

    }

    // ----------------------------------------------DELETEDRINKS----------------------------------------------

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
            if (found == true) {
                System.out.println("Do you want to delete the drinks information as shown below? ");
                System.out.println(drinksList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");

                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if (userInput2.equalsIgnoreCase("Y")) {
                        drinksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("drinksFile.txt", drinksList, obj);

                        if (deleted) {
                             System.out.println("Drinks has been removed.");
                        }else{
                            System.out.println("\nFAILED TO REMOVE THE DRINKS.\n");
                        }

                    } else {
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }
                } else {
                    found = false;
                }

            } else {
                System.out.println("\nDRINKS NOT FOUND. PLEASE SEARCH AGAIN.\n");
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

    // ---------------------------------------ADDDRINKS-----------------------------------------------------------------------------
    // User can buy one or many different and same drinks
    // -----------------------------------------------------------------------------------------------------------------------------
    public ArrayList<FoodAndBeverage> addFnb(ArrayList<Drinks> drinksList, ArrayList<Snacks> snacksList,
            ArrayList<FoodAndBeverage> fnbList,
            Scanner scanner) throws Exception {
        Drinks drink;

        int addDrinks = 0;
        int drinkQty = 0;
        char size;
        char ice;
        int temperature = 0;

        boolean continueInputD = true;
        boolean continueInputDQ = true;
        boolean continueInputT = true;

        boolean validDrink = true;

        // -----------------------------------------------DisplayDrinks---------------------------------------------------------
        System.out.println("=========================================================");
        System.out.println("                       Drinks Available");
        System.out.println("=========================================================");
        System.out.println();
        for (int i = 0; i < drinksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(drinksList.get(i).toString());
            System.out.println();
            System.out.println("---------------------------------------------------------");
            System.out.println();
        }
        System.out.println("=========================================================");
        System.out.println("\nWhich drink would you like to add?\n\n");
        // ----------------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------SelectDrink----------------------------------------------------------
        do {

            do {

                try {
                    System.out.print("Enter your option > ");
                    addDrinks = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }

            } while (continueInputD);

            if (addDrinks > drinksList.size()) {
                System.out.println("Snack not found. Please try again. ");
                validDrink = false;
            } else {
                validDrink = true;
            }

        } while (!validDrink);
        // -----------------------------------------------------------------------------------------------------------------------

        drink = drinksList.get(addDrinks - 1);

        if (drink.getStockQty() != 0) {

            do {

                // -------------------------------------------------EnterQty--------------------------------------------------------
                do {
                    try {
                        System.out.print("Enter quantity > ");
                        drinkQty = scanner.nextInt();

                        continueInputDQ = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputDQ);

                if (!drink.checkStockQty(drinkQty)) {

                    // Purchase quantity > Stock Quatity
                    System.out.println("Sorry, we have only " + drink.getStockQty() + " for " + drink.getFoodName());
                    System.out.println("Please enter again. ");

                }

            } while (!drink.checkStockQty(drinkQty));
            // ----------------------------------------------------------------------------------------------------------------

            drink.calculateStockQty(drinkQty);

            drink.setPurchaseQty(drinkQty);

            System.out.println("\n============================");
            System.out.println("           Size");
            System.out.println("============================");
            System.out.println("1. Small - S");
            System.out.println("2. Medium - M");
            System.out.println("3. Big - B");

            // Select size
            do {

                System.out.print("Enter (S, M, B) > ");
                size = scanner.next().charAt(0);
                size = Character.toUpperCase(size);

            } while (size != 'S' && size != 'M' && size != 'B');

            switch (size) {
                case 'S':
                    drink.setSize("Small");
                    break;
                case 'M':
                    drink.setSize("Medium");
                    break;
                case 'B':
                    drink.setSize("Big");
                    break;
                default:
                    System.out.println("Invalid.");
                    break;
            }

            // Select temperature
            do {

                System.out.println("\n============================");
                System.out.println("        Temperature");
                System.out.println("============================");
                System.out.println("1. Cold");
                System.out.println("2. Hot");
                do {
                    try {
                        System.out.print("Enter (1 / 2) > ");
                        temperature = scanner.nextInt();

                        continueInputT = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputT);

            } while (temperature != 1 && temperature != 2);

            if (temperature == 1) {
                drink.setTemperature("Cold");

                // Select Iced
                System.out.print("Y - ICED, other - NO ICE > ");
                ice = scanner.next().charAt(0);

                if (ice == 'Y' || ice == 'y') {
                    drink.setIce(true);
                } else {
                    drink.setIce(false);
                }
            }

            fnbList.add(drink);

        } else {

            System.out.println("Sorry, we have out of stock for " + drink.getFoodName() + ".");

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

                        }

                    } while (!fnbList.get(fnbIndex).checkStockQty(editQty));

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
