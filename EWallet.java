import java.util.InputMismatchException;
import java.util.Scanner;

public class EWallet extends PaymentType {
    private String eWalletType;
    private int telNo;

    public EWallet() {

    }

    public EWallet(String eWalletType, int telNo) {
        this.eWalletType = eWalletType;
        this.telNo = telNo;
    }

    public void setEWalletType(String eWalletType) {
        this.eWalletType = eWalletType;
    }

    public void setTelNo(int telNo) {
        this.telNo = telNo;
    }

    public String getEWalletType() {
        return eWalletType;
    }

    public int getTelNo() {
        return telNo;
    }

    public EWallet addEwallet(Scanner scanner) {
        EWallet eWallet;
        boolean continueInput = true;
        int telNo = 0;

        System.out.print("Enter e-wallet used > ");
        String eWalletType = scanner.next();

        do {
            try {
                System.out.print("Enter phone number > ");
                telNo = scanner.nextInt();

                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (continueInput);

        eWallet = new EWallet(eWalletType, telNo);

        return eWallet;
    }
}
