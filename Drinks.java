import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

     public static void readFromFile(String filename) throws Exception {
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
    }

    public static boolean writeIntoFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) {
        boolean write = false;
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

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
            System.err.println(RED + "\nERROR WRITING TO THE FILE: " + e.getMessage() + "\n" + RESET);
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

}
