
import java.util.*;
import java.time.LocalDate;

public class DriverJQ {

    public static void main(String[] args) throws Exception {
        viewReport();
        viewReportDDrink();
        viewReportTicket();
        
        // Purchase.makePurchase(1, new Login("username", "password"));
    }

    public static void viewReport() throws Exception {

        ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
        ArrayList<String> snackCust = Purchase.snackCust;
        ArrayList<Double> snackTotalPrice = Purchase.snackTotalPrice;

        int i = 0;
        for (Snacks snack : purchaseSnack) {
            System.out.printf("%d. ", i + 1);
            System.out.println(snack.displayToReport());
            System.out.println(snackCust.get(i));
            System.out.println(snackTotalPrice.get(i));
            i++;
            System.out.println("------------------------------------------------");
        }

    }

    public static void viewReportDDrink() throws Exception {
        ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");
        ArrayList<String> drinkCust = Purchase.drinkCust;
        ArrayList<Double> drinkTotalPrice = Purchase.drinkTotalPrice;

        int i = 0;
        for (Drinks drink : purchaseDrink) {
            System.out.printf("%d. ", i + 1);
            System.out.println(drink.displayToReport());
            System.out.println(drinkCust.get(i));
            System.out.println(drinkTotalPrice.get(i));
            i++;
            System.out.println("------------------------------------------------");

        }
    }

    public static void viewReportTicket() throws Exception {
        ArrayList<Ticket> purchaseTicket = Purchase.readFromTicketFile("purchaseTicket.txt");
        ArrayList<String> ticketCust = Purchase.ticketCust;

        int i = 0;
        for (Ticket ticket : purchaseTicket) {
            System.out.printf("%d. ", i + 1);
            System.out.println(ticket);
            System.out.println(ticketCust.get(i));
            i++;
            System.out.println("------------------------------------------------");

        }
    }
}
