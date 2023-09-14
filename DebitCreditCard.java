import java.time.LocalDate;

public class DebitCreditCard extends PaymentType {
    private int cardNo;
    private LocalDate expiryDate;
    private String cardHolderName;
    private int ccv;

    public DebitCreditCard(){

    }

    public DebitCreditCard(int cardNo, LocalDate expiryDate, String cardHolderName, int ccv){
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
        this.ccv = ccv;
    }

    public void deleteDebitCreditCard(int cardNo){

    }

    public String toString(){
        return String.format("");
    }
}
