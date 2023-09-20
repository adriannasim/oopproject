import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Purchase {
	private Ticket[] ticketList = new Ticket[100];
	private PaymentType paymentType;
	private FoodAndBeverage[] fnb = new FoodAndBeverage[100];
	private double purchaseAmount = 0;

	public Purchase() {

	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void purchaseTicket(Ticket[] ticketList) {
		for (Ticket tickets : ticketList) {
			int i = 0;
			this.ticketList[i] = tickets;
			i++;
		}
	}

	public void purchaseFnb(FoodAndBeverage[] fnb) {
		for (FoodAndBeverage fnbs : fnb) {
			int i = 0;
			this.fnb[i] = fnbs;
			i++;
		}
	}

	public Ticket[] getTicketList() {
		return ticketList;
	}

	public FoodAndBeverage[] getFnb() {
		return fnb;
	}

	public double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void calPurchaseAmount(double amount) {
		purchaseAmount += amount;
	}

	public String toString() {
		return String.format("Payment Type : %s", paymentType) + ticketList + fnb;
	}

	// Read from file
	public static ArrayList<String> snackCust = new ArrayList<String>();
	public static ArrayList<Double> snackTotalPrice = new ArrayList<Double>();
	public static ArrayList<String> drinkCust = new ArrayList<String>();
	public static ArrayList<Double> drinkTotalPrice = new ArrayList<Double>();
	public static ArrayList<String> ticketCust = new ArrayList<String>();

	
	public static ArrayList<Ticket> readFromTicketFile(String filename) throws Exception {
		File file = new File(filename);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		TrainStation station = new TrainStation();
		ArrayList<TrainStation> stationList = station.getStationList();
		TrainStation departStation = new TrainStation();
		TrainStation arriveStation = new TrainStation();

		if (file.exists()) {
			try (Scanner inputFile = new Scanner(file)) {
				while (inputFile.hasNext()) {
					String[] parts = inputFile.nextLine().split("\\|\\|");
					if (parts.length == 7) {
						String username = parts[0];
						String departL = parts[1];
						String arriveL = parts[2];
						double ticketPrice = Double.parseDouble(parts[3]);
						int day = Integer.parseInt(parts[4]);
						int month = Integer.parseInt(parts[5]);
						int year = Integer.parseInt(parts[6]);

						for (int i=0; i < stationList.size(); i++){
							if(departL.equalsIgnoreCase(stationList.get(i).getLocationName())){
								departStation = stationList.get(i);
							}
							if(arriveL.equalsIgnoreCase(stationList.get(i).getLocationName())){
								arriveStation = stationList.get(i);
							}
						}

						// Create a Snacks object and add it to the list
						Schedule ticketSchedule = new Schedule(departStation, arriveStation, ticketPrice);
						LocalDate ticketDate = LocalDate.of(year, month, day);
						ticketList.add(new Ticket(ticketSchedule, ticketDate));
						ticketCust.add(username);
					} else {
						// Handle lines with incorrect data format, e.g., log an error
						System.err.println("Invalid data format: " + Arrays.toString(parts));
					}
				}
			}
		}
		return ticketList;
	}
	

	public static ArrayList<Snacks> readFromSnackFile(String filename) throws Exception {
		File file = new File(filename);
		ArrayList<Snacks> snacksList = new ArrayList<Snacks>();

		if (file.exists()) {
			try (Scanner inputFile = new Scanner(file)) {
				while (inputFile.hasNext()) {
					String[] parts = inputFile.nextLine().split("\\|\\|");
					if (parts.length == 8) {
						String username = parts[0];
						String foodid = parts[1];
						String className = parts[2];
						String foodname = parts[3];
						double foodprice = Double.parseDouble(parts[4]);
						int purchaseQty = Integer.parseInt(parts[5]);
						double totalprice = Double.parseDouble(parts[6]);
						boolean partyPack = Boolean.parseBoolean(parts[7]);

						// Create a Snacks object and add it to the list
						snacksList.add(new Snacks(foodid, foodname, foodprice, purchaseQty, partyPack));
						snackCust.add(username);
						snackTotalPrice.add(totalprice);
					} else {
						// Handle lines with incorrect data format, e.g., log an error
						System.err.println("Invalid data format: " + Arrays.toString(parts));
					}
				}
			}
		}
		return snacksList;
	}

	public static ArrayList<Drinks> readFromDrinkFile(String filename) throws Exception {
		File file = new File(filename);
		ArrayList<Drinks> drinksList = new ArrayList<Drinks>();

		if (file.exists()) {
			try (Scanner inputFile = new Scanner(file)) {
				while (inputFile.hasNext()) {
					String[] parts = inputFile.nextLine().split("\\|\\|");
					if (parts.length == 10) {
						String username = parts[0];
						String foodid = parts[1];
						String className = parts[2];
						String foodname = parts[3];
						double foodprice = Double.parseDouble(parts[4]);
						int purchaseQty = Integer.parseInt(parts[5]);
						double totalprice = Double.parseDouble(parts[6]);
						String size = parts[7];
						String temperature = parts[8];
						boolean ice = Boolean.parseBoolean(parts[9]);

						// Create a Snacks object and add it to the list
						drinksList.add(new Drinks(foodid, foodname, foodprice, purchaseQty, size, temperature, ice));
						drinkCust.add(username);
						drinkTotalPrice.add(totalprice);
					} else {
						// Handle lines with incorrect data format, e.g., log an error
						System.err.println("Invalid data format: " + Arrays.toString(parts));
					}
				}
			}
		}
		return drinksList;
	}

	// ============================================================================================
	// MAKE PURCHASE
	// ============================================================================================
	// Without Login
	public static void makePurchase(int userOpt1, String staffname) throws Exception {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		ArrayList<FoodAndBeverage> fnbList = new ArrayList<FoodAndBeverage>();
		Ticket ticketObj = new Ticket();
		Snacks fnb = new Snacks();
		Purchase userPurchase = new Purchase();
		PaymentType pType = new PaymentType();

		boolean continueInputPT = true;

		int paymentTypeOpt = 0;
		char yesno1;

		switch (userOpt1) {
			case 1:
				ticketList = ticketObj.buyTicket(ticketList, scanner);
				System.out.println("=========================================================");
				System.out.println("                           Tickets");
				System.out.println("=========================================================");
				int z = 1;
				for (Ticket tickets : ticketList) {
					System.out.println();
					System.out.println(z + ".");
					System.out.println(tickets);
					z++;
					System.out.println();
					System.out.println("---------------------------------------------------------");
					System.out.println();
				}
				break;
			case 2:
				fnbList = fnb.buyFnb(fnbList, scanner);
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
				break;
			default:
				System.out.println("Invalid Input. ");
				break;
		}

		System.out.println("Confirm your purchase? (Y - YES, other - NO) > ");
		yesno1 = scanner.next().charAt(0);
		yesno1 = Character.toUpperCase(yesno1);

		if (yesno1 == 'Y') {

			// Confirm purchase
			// =================================================================================
			// ADD INTO PURCHASE ARRAYLIST
			// =================================================================================
			Ticket[] tickets = ticketList.toArray(new Ticket[ticketList.size()]);
			userPurchase.purchaseTicket(tickets);

			FoodAndBeverage[] fnbs = fnbList.toArray(new FoodAndBeverage[fnbList.size()]);
			userPurchase.purchaseFnb(fnbs);

			// =================================================================================
			// PROCEED TO PAYMENT
			// =================================================================================
			System.out.println("================================");
			System.out.println("           Payment Type");
			System.out.println("================================");
			System.out.println("1. Online Banking");
			System.out.println("2. E-wallet");
			System.out.println("3. Debit / Credit Card");
			do {

				do {
					try {
						System.out.print("Choose your payment type > ");
						paymentTypeOpt = scanner.nextInt();

						continueInputPT = false;
					} catch (InputMismatchException ex) {
						System.out.println("Incorrect Input. Please try agin. ");
						scanner.nextLine();
					}
				} while (continueInputPT);

			} while (paymentTypeOpt != 1 && paymentTypeOpt != 2 && paymentTypeOpt != 3);

			switch (paymentTypeOpt) {
				case 1:
					OnlineBanking ob = new OnlineBanking();
					pType = ob.addOnlinebanking(scanner);
					break;
				case 2:
					EWallet ew = new EWallet();
					pType = ew.addEwallet(scanner);
					break;
				case 3:
					DebitCreditCard dc = new DebitCreditCard();
					pType = dc.addCard(scanner);
					break;
				default:
					System.out.println("Invalid Input");
					break;
			}

			userPurchase.setPaymentType(pType);

			System.out.println("Purchase successsfully. ");

			// Write into file
			Ticket ticket = new Ticket();
			Snacks snack = new Snacks();
			Drinks drink = new Drinks();

			ticket.writePurchaseTicket(tickets, staffname);

			for (FoodAndBeverage foodAndBeverage : fnbs) {
				if (foodAndBeverage instanceof Snacks) {
					snack.writePurchaseFnB((Snacks)foodAndBeverage, staffname);
				} else if (foodAndBeverage instanceof Drinks) {
					drink.writePurchaseFnB((Drinks)foodAndBeverage, staffname);
				}
			}

		} else {
			return;
		}

	}

	// With Login
	public static void makePurchase(int userOpt1, Login login) throws Exception {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		ArrayList<FoodAndBeverage> fnbList = new ArrayList<FoodAndBeverage>();
		Ticket ticketObj = new Ticket();
		Snacks fnbObj = new Snacks();
		Purchase userPurchase = new Purchase();
		PaymentType pType = new PaymentType();

		boolean continueInputPT = true;

		int paymentTypeOpt = 0;
		char yesno1;

		switch (userOpt1) {
			case 1:
				ticketList = ticketObj.buyTicket(ticketList, scanner);
				System.out.println("=========================================================");
				System.out.println("                           Tickets");
				System.out.println("=========================================================");
				int z = 1;
				for (Ticket tickets : ticketList) {
					System.out.println();
					System.out.println(z + ".");
					System.out.println(tickets);
					z++;
					System.out.println();
					System.out.println("---------------------------------------------------------");
					System.out.println();
				}
				break;
			case 2:
				fnbList = fnbObj.buyFnb(fnbList, scanner);
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
				break;
			default:
				System.out.println("Invalid Input. ");
				break;
		}

		System.out.print("Confirm your purchase? (Y - YES, other - NO) >> ");
		yesno1 = scanner.next().charAt(0);
		yesno1 = Character.toUpperCase(yesno1);

		if (yesno1 == 'Y') {

			// Confirm purchase
			// =================================================================================
			// ADD INTO PURCHASE ARRAYLIST
			// =================================================================================
			Ticket[] tickets = ticketList.toArray(new Ticket[ticketList.size()]);
			userPurchase.purchaseTicket(tickets);

			FoodAndBeverage[] fnbs = fnbList.toArray(new FoodAndBeverage[ticketList.size()]);
			userPurchase.purchaseFnb(fnbs);

			// =================================================================================
			// PROCEED TO PAYMENT
			// =================================================================================
			System.out.println("================================");
			System.out.println("           Payment Type");
			System.out.println("================================");
			System.out.println("1. Online Banking");
			System.out.println("2. E-wallet");
			System.out.println("3. Debit / Credit Card");
			do {

				do {
					try {
						System.out.print("Choose your payment type > ");
						paymentTypeOpt = scanner.nextInt();

						continueInputPT = false;
					} catch (InputMismatchException ex) {
						System.out.println("Incorrect Input. Please try agin. ");
						scanner.nextLine();
					}
				} while (continueInputPT);

			} while (paymentTypeOpt != 1 && paymentTypeOpt != 2 && paymentTypeOpt != 3);

			switch (paymentTypeOpt) {
				case 1:
					OnlineBanking ob = new OnlineBanking();
					pType = ob.addOnlinebanking(scanner);
					break;
				case 2:
					EWallet ew = new EWallet();
					pType = ew.addEwallet(scanner);
					break;
				case 3:
					DebitCreditCard dc = new DebitCreditCard();
					pType = dc.addCard(scanner);
					break;
				default:
					System.out.println("Invalid Input");
					break;
			}

			userPurchase.setPaymentType(pType);

			System.out.println("Purchase successsfully. ");

			// Write into file
			Ticket ticket = new Ticket();
			Snacks snack = new Snacks();
			Drinks drink = new Drinks();

			if (ticketList.size() != 0){
				ticket.writePurchaseTicket(tickets, login.getUsername());
			} 
			if (fnbList.size() != 0){
				for (FoodAndBeverage foodAndBeverage : fnbs) {
				if (foodAndBeverage instanceof Snacks) {
					snack.writePurchaseFnB(foodAndBeverage, login.getUsername());
				} else if (foodAndBeverage instanceof Drinks) {
					drink.writePurchaseFnB(foodAndBeverage, login.getUsername());
				}
			}
			}

		} else {
			return;
		}

	}
}
