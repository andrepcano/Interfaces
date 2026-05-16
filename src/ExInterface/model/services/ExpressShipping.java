package ExInterface.model.services;

public class ExpressShipping implements ShippingService {
    public double fraight(double basicPayment) {
        // calculates 20% of the basic payment as freight
        double freight = basicPayment * 0.20;
        // if freight is less than R$30, the minimum charge is R$30
        if (freight < 30) {
            return 30.0;
            // otherwise, charge the calculated freight
        } else {
            return freight;
        }
    }
}
