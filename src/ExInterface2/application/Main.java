package ExInterface2.application;

import ExInterface2.model.entities.Order;
import ExInterface2.model.entities.OrderItem;
import ExInterface2.model.services.CorreiosService;
import ExInterface2.model.services.OrderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.ENGLISH);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Enter the order date: ");
        System.out.print("Order number: ");
        int number = sc.nextInt();
        System.out.print("Date: ");
        LocalDate date = LocalDate.parse(sc.next(), fmt);
        System.out.print("Number of Items: ");
        int n = sc.nextInt();

        Order order = new Order(number, date);

        for (int i = 1; i <= n; i++) {
            System.out.println("Item " + i + ":");
            System.out.print("Name: ");
            String name = sc.next();
            sc.nextLine();
            System.out.print("Quantity: ");
            int quantity = sc.nextInt();
            System.out.print("Unit price: ");
            double unitPrice = sc.nextDouble();

            order.getOrderItemList().add(new OrderItem(name, quantity, unitPrice));
        }

        OrderService orderService = new OrderService(new CorreiosService());
        double total = orderService.processOrder(order);

        System.out.println("------------------------------");

        System.out.println("ORDER:");
        for (OrderItem item : order.getOrderItemList()) {
            System.out.println(item.getName() + " - quantity: " + item.getQuantity() + " - price: " + item.getUnitPrice());

            System.out.println("------------------------------");
        }

        System.out.println("Total: " + String.format("%.2f", total));


        sc.close();
    }
}
