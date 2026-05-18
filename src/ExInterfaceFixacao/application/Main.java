package ExInterfaceFixacao.application;

import ExInterfaceFixacao.model.entities.Contract;
import ExInterfaceFixacao.model.entities.Installment;
import ExInterfaceFixacao.model.services.ContractService;
import ExInterfaceFixacao.model.services.PaypalService;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        System.out.println("Enter the contract date: ");
        System.out.print("Number: ");
        int number = sc.nextInt();
        System.out.print("Date (dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(sc.next(), fmt);
        System.out.print("Value of contract: ");
        double totalValue = sc.nextDouble();

        Contract obj = new Contract(number, date, totalValue);


        System.out.println("Enter the number of installments: ");
        int n = sc.nextInt();

        ContractService contractService = new ContractService(new PaypalService());

        contractService.processContract(obj, n);


        System.out.println("INSTALLMENTS: ");
        for(Installment installment : obj.getInstallments()) {
            System.out.println(installment);
        }


        sc.close();
    }
}
