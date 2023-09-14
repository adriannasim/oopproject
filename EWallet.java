public class EWallet extends PaymentType {
    private String eWalletType;
    private int telNo;

    public EWallet(){

    }
    
    public EWallet(String eWalletType, int telNo){
        this.eWalletType = eWalletType;
        this.telNo = telNo;
    }

    public void setEWalletType(String eWalletType){
        this.eWalletType = eWalletType;
    }

    public void setTelNo(int telNo){
        this.telNo = telNo;
    }

    public String getEWalletType(){
        return eWalletType;
    }

    public int getTelNo(){
        return telNo;
    }
}
