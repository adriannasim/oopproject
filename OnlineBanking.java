public class OnlineBanking extends PaymentType {
    private String bank;

    public OnlineBanking(){

    }

    public OnlineBanking(String bank){
        this.bank = bank;
    }

    public void setBank(String bank){
        this.bank = bank;
    }

    public String getBank(){
        return bank;
    }
}
