package test.java.sinodal.com.FintechIsabela.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contas_bancarias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroConta;

    @Column(nullable = false)
    private Double saldo;

    public ContaBancaria(String numeroConta, Double saldoInicial) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    // --- Estes métodos DEVEM estar em ContaBancaria ---
    public void depositar(Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        this.saldo += valor;
        System.out.println("Depósito de R$" + String.format("%.2f", valor) + " realizado. Saldo atual: R$" + String.format("%.2f", this.saldo));
    }

    public boolean sacar(Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        if (this.saldo >= valor) {
            this.saldo -= valor;
            System.out.println("Saque de R$" + String.format("%.2f", valor) + " realizado. Saldo atual: R$" + String.format("%.2f", this.saldo));
            return true;
        } else {
            System.out.println("Saldo insuficiente para saque de R$" + String.format("%.2f", valor) + ". Saldo atual: R$" + String.format("%.2f", this.saldo));
            return false;
        }
    }

    public void exibirDetalhes() {
        System.out.println("Número da Conta: " + this.numeroConta);
        System.out.println("Saldo: R$" + String.format("%.2f", this.saldo));
    }

    // Lombok fornece estes, mas estou mostrando para clareza
    public String getNumeroConta() {
        return numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }
}