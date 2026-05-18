# 🔌 Interfaces em Java — Sistema de Aluguel de Carros, Pedidos & Contratos

Repositório criado para documentar os estudos e exercícios práticos sobre **Interfaces** em Java, comparando uma solução ruim sem interface e uma solução boa com interface, além de exercícios extras desenvolvidos de forma independente. Desenvolvidos com base no curso do professor Nélio Alves (Udemy).

---

## 📁 Estrutura do Projeto

```
src/
├── ExInterfaceSolucaoRuim/
│   ├── application/
│   │   └── Main.java                      → Classe principal — solução sem interface
│   └── model/
│       ├── entities/
│       │   ├── CarRental.java             → Entidade de aluguel de carro
│       │   ├── Invoice.java               → Entidade de fatura
│       │   └── Vehicle.java               → Entidade de veículo
│       └── services/
│           ├── BrazilTaxService.java      → Serviço de imposto brasileiro
│           └── RentalService.java         → Serviço de aluguel — acoplado ao Brasil
├── ExInterfaceSolucaoBoa/
│   ├── application/
│   │   └── Main.java                      → Classe principal — solução com interface
│   └── model/
│       ├── entities/
│       │   ├── CarRental.java             → Entidade de aluguel de carro
│       │   ├── Invoice.java               → Entidade de fatura
│       │   └── Vehicle.java               → Entidade de veículo
│       └── services/
│           ├── TaxService.java            → Interface do serviço de imposto
│           ├── BrazilTaxService.java      → Implementação brasileira da interface
│           └── RentalService.java         → Serviço de aluguel — desacoplado
├── ExInterface/
│   ├── application/
│   │   └── Main.java                      → Classe principal — sistema de pedidos
│   └── model/
│       ├── entities/
│       │   ├── Order.java                 → Entidade de pedido
│       │   └── Receipt.java               → Entidade de recibo
│       └── services/
│           ├── ShippingService.java       → Interface do serviço de frete
│           ├── StandardShipping.java      → Implementação de frete padrão
│           ├── ExpressShipping.java       → Implementação de frete expresso
│           └── OrderService.java          → Serviço de pedido — desacoplado
└── ExInterfaceFixacao/
    ├── application/
    │   └── Main.java                      → Classe principal — sistema de contratos
    └── model/
        ├── entities/
        │   ├── Contract.java              → Entidade de contrato
        │   └── Installment.java           → Entidade de parcela
        └── services/
            ├── OnlinePaymentService.java  → Interface do serviço de pagamento online
            ├── PaypalService.java         → Implementação Paypal (1% juros + 2% taxa)
            └── ContractService.java       → Serviço de contrato — desacoplado
```

---

## 📚 O que aprendi

### 🔹 Interface (`interface` e `implements`)

Uma interface é um contrato que define o que uma classe deve fazer, sem dizer como. No projeto de aluguel, `TaxService` define o método `tax(double amount)`. No projeto de pedidos, `ShippingService` define o método `freight(double basicPayment)`. No projeto de contratos, `OnlinePaymentService` define os métodos `interest(amount, months)` e `paymentFee(amount)` — qualquer classe que implementá-las é obrigada a fornecer sua própria lógica de cálculo.

### 🔹 Por que usar interface

Na solução ruim (`ExInterfaceSolucaoRuim`), o `RentalService` conhece diretamente o `BrazilTaxService` — trocar o país exige alterar o código do serviço de aluguel. Na solução boa, o `RentalService` conhece apenas a interface `TaxService` — para trocar o comportamento, basta passar uma implementação diferente, sem mexer em nada do serviço.

O mesmo princípio se aplica ao `OrderService` e ao `ContractService`: nenhum deles sabe qual implementação concreta está usando — só sabem que receberão algo que cumpre o contrato da interface.

### 🔹 Composição com interface

Em vez de herdar comportamento, as classes recebem colaboradores via construtor — isso é composição. `RentalService` tem um `TaxService`. `OrderService` tem um `ShippingService`. `ContractService` tem um `OnlinePaymentService`. Cada um cuida da sua responsabilidade.

### 🔹 Injeção de dependência via construtor

A implementação concreta é decidida fora da classe, em tempo de execução:

```java
// sistema de aluguel
RentalService rs = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());

// sistema de pedidos
OrderService service = new OrderService(new StandardShipping());

// sistema de contratos
ContractService contractService = new ContractService(new PaypalService());
```

### 🔹 Processamento de parcelas com juros e taxa

No `ContractService`, o método `processContract` divide o valor total pelo número de meses, aplica os juros simples mês a mês e sobre o resultado aplica a taxa de pagamento, gerando um `Installment` para cada parcela:

```java
double basicInstallment = contract.getTotalValue() / months;
for (int i = 1; i <= months; i++) {
    LocalDate dueDate = contract.getDate().plusMonths(i);
    double withInterest = basicInstallment + onlinePaymentService.interest(basicInstallment, i);
    double withFee = withInterest + onlinePaymentService.paymentFee(withInterest);
    contract.getInstallments().add(new Installment(dueDate, withFee));
}
```

### 🔹 Princípio da Responsabilidade Única (SRP)

Cada classe tem um único motivo para mudar:

- `RentalService` muda só se a lógica de aluguel mudar
- `BrazilTaxService` muda só se o imposto brasileiro mudar
- `ContractService` muda só se a lógica de parcelamento mudar
- `PaypalService` muda só se as regras do Paypal mudarem

### 🔹 Princípio Aberto/Fechado (OCP)

Para adicionar um novo país, tipo de frete ou serviço de pagamento, basta criar uma nova classe que implementa a interface — sem tocar no código existente:

```java
// novo serviço de pagamento = nova classe, nada muda no resto
class StripeService implements OnlinePaymentService {
    public Double paymentFee(Double amount) { return amount * 0.025; }
    public Double interest(Double amount, Integer months) { return (amount * months) / 100; }
}
```

### 🔹 `LocalDate` e `DateTimeFormatter`

As datas são lidas como `String` e convertidas com `DateTimeFormatter`:

```java
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate date = LocalDate.parse(sc.next(), fmt);
```

### 🔹 `LocalDate.plusMonths`

Calcula a data de vencimento de cada parcela somando meses à data do contrato:

```java
LocalDate dueDate = contract.getDate().plusMonths(i);
// i=1 → 25/07/2018, i=2 → 25/08/2018, i=3 → 25/09/2018
```

### 🔹 `LocalDateTime` e `Duration.between`

No sistema de aluguel, a diferença entre início e fim é calculada em minutos para determinar o valor:

```java
double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
double hours = minutes / 60;
```

### 🔹 `Math.ceil`

Arredonda horas ou dias para cima, garantindo que frações sejam cobradas como unidade inteira:

```java
if (hours <= 12) {
    basicPayment = pricePerHour * Math.ceil(hours);
} else {
    basicPayment = pricePerDay * Math.ceil(hours / 24);
}
```

---

## 🧠 Conceito Principal

> A solução ruim acopla o serviço diretamente à implementação concreta. A solução boa usa uma interface como contrato — o serviço não sabe qual implementação está usando, só sabe que receberá algo que cumpre o contrato. Isso torna o sistema flexível, extensível e alinhado com os princípios **S** (responsabilidade única) e **O** (aberto para extensão, fechado para modificação) do SOLID.

---

## 🛠️ Tecnologias

Java 17+ · IntelliJ IDEA · Git e GitHub

---

## 👨🏻‍💻 Autor

Feito por **André Peixoto Cano** — Estudante de Engenharia de Software na FIAP, aprendendo Java com o curso do professor Nélio Alves na Udemy.