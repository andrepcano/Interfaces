# 🔌 Interfaces em Java — Sistema de Aluguel de Carros

Repositório criado para documentar os estudos e exercícios práticos sobre **Interfaces** em Java, comparando uma solução ruim sem interface e uma solução boa com interface, desenvolvidos com base no curso do professor Nélio Alves (Udemy).

---

## 📁 Estrutura do Projeto

```
src/
├── ExInterfaceSolucaoRuim/
│   ├── application/
│   │   └── Main.java                → Classe principal — solução sem interface
│   └── model/
│       ├── entities/
│       │   ├── CarRental.java       → Entidade de aluguel de carro
│       │   ├── Invoice.java         → Entidade de fatura
│       │   └── Vehicle.java         → Entidade de veículo
│       └── services/
│           ├── BrazilTaxService.java → Serviço de imposto brasileiro
│           └── RentalService.java   → Serviço de aluguel — acoplado ao Brasil
└── ExInterfaceSolucaoBoa/
    ├── application/
    │   └── Main.java                → Classe principal — solução com interface
    └── model/
        ├── entities/
        │   ├── CarRental.java       → Entidade de aluguel de carro
        │   ├── Invoice.java         → Entidade de fatura
        │   └── Vehicle.java         → Entidade de veículo
        └── services/
            ├── TaxService.java      → Interface do serviço de imposto
            ├── BrazilTaxService.java → Implementação brasileira da interface
            └── RentalService.java   → Serviço de aluguel — desacoplado
```

---

## 📚 O que aprendi

### 🔹 Interface (`interface` e `implements`)

Uma interface é um contrato que define o que uma classe deve fazer, sem dizer como. No projeto, `TaxService` é a interface que define o método `tax(double amount)` — qualquer classe que implementá-la é obrigada a fornecer sua própria regra de cálculo de imposto.

### 🔹 Por que usar interface aqui

Na solução ruim (`ExInterfaceSolucaoRuim`), o `RentalService` conhece diretamente o `BrazilTaxService` — se precisar trocar para outro país, é necessário alterar o código do serviço de aluguel. Na solução boa (`ExInterfaceSolucaoBoa`), o `RentalService` conhece apenas a interface `TaxService` — para trocar o país basta passar uma implementação diferente, sem mexer em nada do serviço.

### 🔹 Injeção de dependência via construtor

A interface `TaxService` é injetada no construtor do `RentalService`, permitindo passar qualquer implementação em tempo de execução — `BrazilTaxService`, `UsaTaxService`, etc.:

```java
// RentalService não sabe qual país — só sabe que recebe um TaxService
public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
    this.taxService = taxService;
}

// Na Main, decidimos qual implementação usar
RentalService rs = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
```

### 🔹 `LocalDateTime` e `DateTimeFormatter`

As datas de início e fim do aluguel são lidas como `String` e convertidas para `LocalDateTime` com `DateTimeFormatter` no padrão `dd/MM/yyyy HH:mm`.

### 🔹 `Duration.between`

Calcula a diferença em minutos entre a data de retirada e devolução do carro para determinar o valor do aluguel:

```java
// calcula a diferença entre a data de início e fim do aluguel
double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
double hours = minutes / 60;
```

### 🔹 `Math.ceil`

Arredonda o número de horas ou dias para cima, garantindo que frações sejam cobradas como unidade inteira:

```java
if (hours <= 12) {
    basicPayment = pricePerHour * Math.ceil(hours);
} else {
    basicPayment = pricePerDay * Math.ceil(hours / 24);
}
```

---

## 🧠 Conceito Principal

> A solução ruim acopla o serviço de aluguel diretamente à regra de imposto do Brasil. A solução boa usa uma interface como contrato — o `RentalService` não sabe qual país está calculando, só sabe que receberá algo que implementa `TaxService`. Isso torna o sistema flexível, extensível e alinhado com o princípio **aberto para extensão, fechado para modificação**.

---

## 🛠️ Tecnologias

Java 25 · IntelliJ IDEA · Git e GitHub

---

## 👨🏻‍💻 Autor

Feito por **André Peixoto Cano** — Estudante de Engenharia de Software na FIAP, aprendendo Java com o curso do professor Nélio Alves na Udemy.
