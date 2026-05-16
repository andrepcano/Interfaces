package ExInterface.application;

import ExInterface.model.entities.Order;
import ExInterface.model.entities.Receipt;
import ExInterface.model.services.OrderService;
import ExInterface.model.services.StandardShipping;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Locale.setDefault(Locale.US);
        List<Order> list = new ArrayList<>();

        System.out.println("Enter how many products: ");
        int n = sc.nextInt();
        sc.nextLine();


        for (int i = 1; i <= n; i++) {
            System.out.println("ORDER:");
            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Quantity: ");
            int quantity = sc.nextInt();
            System.out.print("Price: ");
            double unitPrice = sc.nextDouble();
            System.out.print("Date: ");
            LocalDate date = LocalDate.parse(sc.next(), fmt);
            list.add(new Order(name, quantity, unitPrice, date));
        }

        OrderService service = new OrderService(new StandardShipping());

        //Receipt
        for (Order order : list) {
            // Processes the order and generates the receipt based on the selected shipping type
            service.processOrder(order);
            System.out.println("\nPedido: " + order.getOrder());
            System.out.printf("Valor base: R$ %.2f%n", order.getReceipt().getBasicPayment());
            System.out.printf("Frete: R$ %.2f%n", order.getReceipt().getFreight());
            System.out.printf("Total: R$ %.2f%n", order.getReceipt().getTotal());
        }




        sc.close();
    }
}
