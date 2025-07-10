package test.java.sinodal.com.FintechIsabela.model;

// ContaPoupanca.java
public class ContaPoupanca extends Conta {
    private double taxaJuros;

    public ContaPoupanca(String numeroConta, double saldo, String titular, double taxaJuros) {
        super(numeroConta, saldo, titular);
        this.taxaJuros = taxaJuros;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void aplicarJuros() {
        double juros = this.saldo * (taxaJuros / 100);
        this.saldo += juros;
        System.out.println("Juros de R$" + String.format("%.2f", juros) + " aplicados na Conta Poupança " + numeroConta);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes da Conta Poupança ---");
        System.out.println("Número da Conta: " + numeroConta);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo Atual: R$" + saldo);
        System.out.println("Taxa de Juros: " + taxaJuros + "%");
        System.out.println("----------------------------------");
    }
}