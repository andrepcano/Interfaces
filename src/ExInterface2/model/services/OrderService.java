package ExInterface2.model.services;

import ExInterface2.model.entities.Order;
import ExInterface2.model.entities.OrderItem;

public class OrderService {

    private ShippingService shippingService;

    public OrderService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public double processOrder(Order order) {
        double subTotalValue = 0.0;
        for (OrderItem orderItem : order.getOrderItemList()) {
            subTotalValue += orderItem.getQuantity() * orderItem.getUnitPrice();
        }
            double installment = shippingService.shipmentFee(subTotalValue);
            double discount = shippingService.discount(subTotalValue);

            return subTotalValue + installment - discount;

    }

}
