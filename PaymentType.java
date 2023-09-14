import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentType {
    private String paymentId;
    private LocalDateTime paymentDateTime;
    private boolean paymentStatus;

    public PaymentType(){

    }

    public void setPaymentStatus(boolean paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    public boolean getPaymentStatus(boolean paymentStatus){
        return paymentStatus;
    }

    public String toString(){
        return String.format("");
    }
}
