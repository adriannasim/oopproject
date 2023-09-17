public class Purchase{
	private Ticket[] ticketList = new Ticket[100];
	private PaymentType paymentType;
	private FoodAndBeverage[] fnb = new FoodAndBeverage[100];
	private double purchaseAmount = 0;
	
	public Purchase(){
		
	}
	
	public PaymentType getPaymentType(){
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType){
		this.paymentType = paymentType;
	}
	
	public void purchaseTicket(Ticket[] ticketList){
		for (Ticket tickets: ticketList){
			int i = 0;
			this.ticketList[i] = tickets;
			i++;
		}
	}
	
	public void purchaseFnb(FoodAndBeverage[] fnb){
		for (FoodAndBeverage fnbs: fnb){
			int i = 0;
			this.fnb[i] = fnbs;
			i++;
		}
	}
	
	public Ticket[] getTicketList(){
		return ticketList;
	}
	
	public FoodAndBeverage[] getFnb(){
		return fnb;
	}

	public double getPurchaseAmount(){
		return purchaseAmount;
	}
	
	public void calPurchaseAmount(double amount){
		purchaseAmount += amount;
	}

	
	public String toString(){
		return String.format("Payment Type : %s", paymentType) + ticketList + fnb;
	}
}
