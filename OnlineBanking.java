import java.util.Scanner;

public class OnlineBanking extends PaymentType {
    private String bank;

    public OnlineBanking() {

    }

    public OnlineBanking(String bank) {
        this.bank = bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    public OnlineBanking addOnlinebanking(Scanner scanner) {
        OnlineBanking onlineBanking;

        System.out.print("Enter bank > ");
        String bank = scanner.next();

        onlineBanking = new OnlineBanking(bank);

        return onlineBanking;
    }
}
