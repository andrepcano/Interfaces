package ExInterface.model.entities;

import java.time.LocalDate;

public class Receipt {
    private Double basicPayment;
    private Double freight;
    private Double total;

    public Receipt() {
        super();
    }

    public Receipt(Double basicPayment, Double freight, Double total) {
        this.basicPayment = basicPayment;
        this.freight = freight;
        this.total = total;
    }

    public Double getBasicPayment() {
        return basicPayment;
    }


    public Double getFreight() {
        return freight;
    }


    public Double getTotal() {
        return total;
    }

}
