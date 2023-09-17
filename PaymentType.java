import java.time.LocalDateTime;

public class PaymentType {
    private String paymentId;
    private LocalDateTime paymentDateTime;
    private boolean paymentStatus;

    public PaymentType(){

    }

    public void setPaymentId(String paymentId){
        this.paymentId = paymentId;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime){
        this.paymentDateTime = paymentDateTime;
    }

    public void setPaymentStatus(boolean paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId(){
        return paymentId;
    }

    public LocalDateTime getPaymentDateTime(){
        return paymentDateTime;
    }
    public boolean getPaymentStatus(boolean paymentStatus){
        return paymentStatus;
    }

    public String toString(){
        return String.format("");
    }
}
