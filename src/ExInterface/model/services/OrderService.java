package ExInterface.model.services;

import ExInterface.model.entities.Order;
import ExInterface.model.entities.Receipt;

public class OrderService {
    private ShippingService shippingService;

    public OrderService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void processOrder(Order order) {
        double basicPayment = order.getQuantity() * order.getUnitPrice();
        // freight calculation
        double freight = shippingService.fraight(basicPayment);
        double total = basicPayment + freight;
        order.setReceipt(new Receipt(basicPayment, freight, total));
    }
}
