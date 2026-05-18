# 🔌 Interfaces em Java — Sistema de Aluguel de Carros, Pedidos & Contratos

Repositório criado para documentar os estudos e exercícios práticos sobre **Interfaces** em Java. Desenvolvidos com base no curso do professor Nélio Alves (Udemy).

---

## 📁 Estrutura do Projeto

```
src/
├── ExInterfaceSolucaoRuim/   → solução sem interface (acoplada)
├── ExInterfaceSolucaoBoa/    → solução com interface (desacoplada)
├── ExInterface/              → sistema de pedidos com frete
├── ExInterfaceFixacao/       → sistema de contratos com parcelamento
└── ExInterface2/             → sistema de pedidos com desconto (exercício independente)
```

---

## 🗂️ Exercícios

### ExInterfaceSolucaoRuim / ExInterfaceSolucaoBoa — Aluguel de Carros
Comparação direta entre solução acoplada e desacoplada. O `RentalService` na solução ruim conhece diretamente o `BrazilTaxService` — na boa, conhece apenas a interface `TaxService`. Para trocar o país basta passar outra implementação, sem tocar no serviço.

### ExInterface — Sistema de Pedidos com Frete
`OrderService` recebe um `ShippingService` via construtor e calcula o valor final do pedido. Duas implementações disponíveis: `StandardShipping` e `ExpressShipping`.

### ExInterfaceFixacao — Sistema de Contratos
`ContractService` recebe um `OnlinePaymentService` e processa um contrato parcelado aplicando juros simples mês a mês e taxa de pagamento sobre cada parcela:

```
Parcela 1: 200 + 1%×1 = 202 → 202 + 2% = 206.04
Parcela 2: 200 + 1%×2 = 204 → 204 + 2% = 208.08
Parcela 3: 200 + 1%×3 = 206 → 206 + 2% = 210.12
```

### ExInterface2 — Sistema de Pedidos com Desconto ⭐ novo
Exercício desenvolvido de forma independente. O sistema lê um pedido com múltiplos itens, calcula o subtotal percorrendo a lista e aplica frete e desconto via interface `ShippingService`:

```
Item 1: Camiseta — qtd: 2 — R$ 59.90   → 119.80
Item 2: Calça    — qtd: 1 — R$ 149.90  → 149.90
Item 3: Boné     — qtd: 3 — R$ 39.90   →  119.70
──────────────────────────────────────────────────
Subtotal:  389.40
Frete:      38.94  (10% — CorreiosService)
Desconto:   19.47  (5%  — CorreiosService)
Total:     408.87
```

Classes criadas: `OrderItem`, `Order`, `ShippingService` (interface), `CorreiosService`, `OrderService`, `Main`.

---

## 📚 O que aprendi

**Interface como contrato** — define o que existe sem dizer como. `TaxService`, `ShippingService` e `OnlinePaymentService` garantem que qualquer implementação terá os métodos necessários.

**Injeção de dependência via construtor** — a implementação concreta é decidida fora da classe, em tempo de execução:

```java
ContractService cs = new ContractService(new PaypalService());
OrderService os    = new OrderService(new CorreiosService());
```

**Princípio Aberto/Fechado** — para adicionar um novo serviço de pagamento ou transportadora basta criar uma nova classe que implementa a interface, sem alterar o código existente.

**Acumulação em loop** — para calcular subtotais percorrendo listas de objetos:

```java
double subtotal = 0;
for (OrderItem item : order.getItems()) {
    subtotal += item.getQuantity() * item.getUnitPrice();
}
```

**`LocalDate` e `plusMonths`** — datas lidas com `DateTimeFormatter` e vencimentos calculados somando meses:

```java
LocalDate dueDate = contract.getDate().plusMonths(i);
```

**Buffer do Scanner** — `nextInt()` e `nextDouble()` deixam o `\n` no buffer; usar `sc.nextLine()` após eles evita leituras erradas na iteração seguinte.

---

## 🛠️ Tecnologias

Java 25 · IntelliJ IDEA · Git e GitHub

---

## 👨🏻‍💻 Autor

Feito por **André Peixoto Cano** — Estudante de Engenharia de Software na FIAP, aprendendo Java com o curso do professor Nélio Alves na Udemy.