package ExInterface.model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private String order;
    private Integer quantity;
    private Double unitPrice;
    private LocalDate date;

    private Receipt receipt;

    public Order() {
    }

    public Order(String order, Integer quantity, Double unitPrice, LocalDate date) {
        this.order = order;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
