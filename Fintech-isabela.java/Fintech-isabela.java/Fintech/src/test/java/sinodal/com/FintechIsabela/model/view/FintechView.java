// src/view/FintechView.java
package test.java.sinodal.com.FintechIsabela.model.view;

import model.Conta;
import java.util.List;
import java.util.Scanner;

public class FintechView {
    private Scanner scanner;

    public FintechView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Menu Fintech ---");
        System.out.println("1. Criar Conta Corrente");
        System.out.println("2. Criar Conta Poupança");
        System.out.println("3. Depositar");
        System.out.println("4. Sacar");
        System.out.println("5. Exibir Detalhes da Conta");
        System.out.println("6. Aplicar Operação Específica (Taxa/Juros)");
        System.out.println("7. Listar Todas as Contas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int getOpcaoUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next(); // Consumir a entrada inválida
            System.out.print("Escolha uma opção: ");
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha restante
        return opcao;
    }

    public String pedirNumeroConta() {
        System.out.print("Digite o número da conta: ");
        return scanner.nextLine();
    }

    public double pedirValor(String tipo) {
        System.out.print("Digite o valor para " + tipo + ": ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
            scanner.next();
            System.out.print("Digite o valor para " + tipo + ": ");
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    public String pedirTitular() {
        System.out.print("Digite o nome do titular: ");
        return scanner.nextLine();
    }

    public double pedirTaxaManutencao() {
        System.out.print("Digite a taxa de manutenção da Conta Corrente: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
            scanner.next();
            System.out.print("Digite a taxa de manutenção da Conta Corrente: ");
        }
        double taxa = scanner.nextDouble();
        scanner.nextLine();
        return taxa;
    }

    public double pedirTaxaJuros() {
        System.out.print("Digite a taxa de juros da Conta Poupança (%): ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
            scanner.next();
            System.out.print("Digite a taxa de juros da Conta Poupança (%): ");
        }
        double taxa = scanner.nextDouble();
        scanner.nextLine();
        return taxa;
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirListaContas(List<Conta> contas) {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        System.out.println("\n--- Lista de Contas Cadastradas ---");
        for (Conta conta : contas) {
            System.out.println("Número: " + conta.getNumeroConta() + ", Titular: " + conta.getTitular() + ", Saldo: R$" + String.format("%.2f", conta.getSaldo()));
        }
        System.out.println("------------------------------------");
    }

    public void fecharScanner() {
        scanner.close();
    }
}