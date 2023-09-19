public class Reporting {
    private FoodAndBeverage[] fnbSold;
    private Ticket[] ticketSold;

    public double calculateFnbSales(FoodAndBeverage[] fnbSold){
        double totalFnb = 0;
        for(int i = 0;i <= fnbSold.length;i++){
            totalFnb += fnbSold[i].getFoodPrice();
        }
        return totalFnb;
    }

    public double fnbDailySales(FoodAndBeverage[] fnb){
        return calculateFnbSales(fnb);
    }

    public double calculateTicketSales(Ticket[] ticketSold){
        double totalTicketSales = 0;
        for (int i = 0; i < ticketSold.length; i++) {
            totalTicketSales += ticketSold[i].getTicketPrice();
        }
        return totalTicketSales;
    }
    }
