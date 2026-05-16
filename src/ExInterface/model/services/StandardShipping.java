package ExInterface.model.services;

public class StandardShipping implements ShippingService{
    public double fraight(double basicPayment) {
        if (basicPayment <= 100) {
            return 15.0;
        } else {
            return 0.0;
        }
    }
}
