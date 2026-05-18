package ExInterface2.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Integer number;
    private LocalDate date;

    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order() {
    }

    public Order(Integer number, LocalDate date) {
        this.number = number;
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

}
