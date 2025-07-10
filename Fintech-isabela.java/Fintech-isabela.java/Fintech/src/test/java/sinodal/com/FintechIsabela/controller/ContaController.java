// src/controller/ContaController.java
package test.java.sinodal.com.FintechIsabela.controller;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import java.util.ArrayList;
import java.util.List;

public class ContaController {
    private List<Conta> contas;

    public ContaController() {
        this.contas = new ArrayList<>();
    }

    public void criarContaCorrente(String numeroConta, double saldoInicial, String titular, double taxaManutencao) {
        ContaCorrente novaConta = new ContaCorrente(numeroConta, saldoInicial, titular, taxaManutencao);
        contas.add(novaConta);
        System.out.println("Conta Corrente " + numeroConta + " criada com sucesso!");
    }

    public void criarContaPoupanca(String numeroConta, double saldoInicial, String titular, double taxaJuros) {
        ContaPoupanca novaConta = new ContaPoupanca(numeroConta, saldoInicial, titular, taxaJuros);
        contas.add(novaConta);
        System.out.println("Conta Poupança " + numeroConta + " criada com sucesso!");
    }

    public Conta buscarConta(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null; // Retorna null se a conta não for encontrada
    }

    public void realizarDeposito(String numeroConta, double valor) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
        } else {
            System.out.println("Conta " + numeroConta + " não encontrada para depósito.");
        }
    }

    public void realizarSaque(String numeroConta, double valor) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            conta.sacar(valor);
        } else {
            System.out.println("Conta " + numeroConta + " não encontrada para saque.");
        }
    }

    public void exibirDetalhesConta(String numeroConta) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            conta.exibirDetalhes(); // Polimorfismo em ação!
        } else {
            System.out.println("Conta " + numeroConta + " não encontrada para exibir detalhes.");
        }
    }

    // Métodos para aplicar lógicas específicas (exemplo de downcast controlado)
    public void aplicarOperacaoEspecifica(String numeroConta) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            if (conta instanceof ContaCorrente) {
                ((ContaCorrente) conta).aplicarTaxaManutencao();
            } else if (conta instanceof ContaPoupanca) {
                ((ContaPoupanca) conta).aplicarJuros();
            } else {
                System.out.println("Nenhuma operação específica aplicável para este tipo de conta.");
            }
        } else {
            System.out.println("Conta " + numeroConta + " não encontrada para aplicar operação específica.");
        }
    }

    public List<Conta> getTodasContas() {
        return new ArrayList<>(contas); // Retorna uma cópia para evitar modificações externas diretas
    }
}