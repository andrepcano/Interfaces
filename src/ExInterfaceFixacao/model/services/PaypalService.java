package ExInterfaceFixacao.model.services;

public class PaypalService implements OnlinePaymentService{

    @Override
    public Double paymentFee(Double amount) { //taxa de pagamento
        return amount * 0.02;
    }

    @Override
    public Double interest(Double amount, Integer months) { //juros
        return (amount * months) / 100;
    }
}
