import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DebitCreditCard extends PaymentType {
    private int cardNo;
    private LocalDate expiryDate;
    private String cardHolderName;
    private int ccv;

    public DebitCreditCard() {

    }

    public DebitCreditCard(int cardNo, LocalDate expiryDate, String cardHolderName, int ccv) {
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
        this.ccv = ccv;
    }

    public void deleteDebitCreditCard(int cardNo) {

    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public int getCardNo() {
        return cardNo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public int getCcv() {
        return ccv;
    }

    public String toString() {
        return String.format("");
    }

    public DebitCreditCard addCard(Scanner scanner) {
        DebitCreditCard card;
        boolean continueInputNo = true;
        boolean continueInputCCV = true;
        boolean continueInputD = true;
        boolean continueInputM = true;
        boolean continueInputY = true;
        boolean validDate = true;

        int cardNo = 0;
        int ccv = 0;
        LocalDate expiryDate;
        LocalDate today = LocalDate.now();
        int day = 0, month = 0, year = 0;
        String cardHolder;

        // Enter cardNo
        do {
            try {
                System.out.print("Enter phone number > ");
                cardNo = scanner.nextInt();

                continueInputNo = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (!continueInputNo);

        // Enter expiry date
        do {
            System.out.println("Enter expiry date ");
            System.out.println();
            System.out.println("---------------------------------------------------------");
            System.out.println();

            // Enter year
            do {
                try {
                    System.out.print("Year > ");
                    year = scanner.nextInt();

                    continueInputY = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputY);

            // Enter month
            do {
                try {
                    System.out.print("Month > ");
                    month = scanner.nextInt();

                    continueInputM = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputM);

            // Enter day
            do {
                try {
                    System.out.print("Day > ");
                    day = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputD);

            expiryDate = LocalDate.of(year, month, day);

            if (expiryDate.isBefore(today)) {
                System.out.println("Invalid Date. Please enter again.");
                validDate = false;
            } else {
                validDate = true;
            }

        } while (!validDate);

        System.out.print("Enter card holder name > ");
        cardHolder = scanner.nextLine();

        // Enter ccv
        do {
            try {
                System.out.print("Enter ccv > ");
                ccv = scanner.nextInt();

                continueInputCCV = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (!continueInputCCV);

        card = new DebitCreditCard(cardNo, expiryDate, cardHolder, ccv);

        return card;
    }
}
