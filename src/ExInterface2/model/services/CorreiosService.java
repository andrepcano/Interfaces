package ExInterface2.model.services;

public class CorreiosService implements ShippingService{
    @Override
    public double shipmentFee(double amount) {
        return amount * 0.10;
    }

    @Override
    public double discount(double amount) {
        return amount * 0.05;
    }
}
