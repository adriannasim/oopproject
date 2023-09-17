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

    public void setCardNo(int cardNo){
        this.cardNo = cardNo;
    }

    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }

    public void setCardHolderName(String cardHolderName){
        this.cardHolderName = cardHolderName;
    }

    public void setCcv(int ccv){
        this.ccv = ccv;
    }

    public int getCardNo(){
        return cardNo;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public String getCardHolderName(){
        return cardHolderName;
    }

    public int getCcv(){
        return ccv;
    }

    public String toString(){
        return String.format("");
    }
}
