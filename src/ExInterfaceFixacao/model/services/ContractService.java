package ExInterfaceFixacao.model.services;

import ExInterfaceFixacao.model.entities.Contract;
import ExInterfaceFixacao.model.entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService (OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, Integer months) {
        double basicQuota = contract.getTotalValue() / months;
        for (int i = 1; i <= months; i++) {
            // data de vencimento: data do contrato + i meses
            LocalDate dueDate = contract.getDate().plusMonths(i);

            // aplica juros simples: amount * interestRate * mês
            double interest = onlinePaymentService.interest(basicQuota, i);

            double fee = onlinePaymentService.paymentFee(basicQuota + interest);

            // aplica taxa de pagamento sobre o valor com juros
            double quota = basicQuota + fee + interest;
            contract.getInstallments().add(new Installment(dueDate, quota));
        }
    }
}
