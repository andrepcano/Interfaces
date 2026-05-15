package ExInterfaceSolucaoRuim.model.services;

import ExInterfaceSolucaoRuim.model.entities.CarRental;
import ExInterfaceSolucaoRuim.model.entities.Invoice;

import java.time.Duration;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService taxService;

    // Sem contrutor vazio pois quero obrigar informar esses dados quando colocar o (RentalService)
    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice (CarRental carRental){

        // calcula a diferença entre a data de início e fim do aluguel
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60;

        double basicPayment;

        if (hours <= 12) {
            // arredondando para cima
            basicPayment = pricePerHour * Math.ceil(hours); // se o aluguel for menos de 12 hours
        } else {
            basicPayment = pricePerDay * Math.ceil(hours / 24); // se o aluguel for mais de 12 hours
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
