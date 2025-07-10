 // ContaCorrente.java
public class ContaCorrente extends Conta {
    private double taxaManutencao;

    public ContaCorrente(String numeroConta, double saldo, String titular, double taxaManutencao) {
        super(numeroConta, saldo, titular);
        this.taxaManutencao = taxaManutencao;
    }

    public double getTaxaManutencao() {
        return taxaManutencao;
    }

    public void aplicarTaxaManutencao() {
        this.saldo -= taxaManutencao;
        System.out.println("Taxa de manutenção de R$" + taxaManutencao + " aplicada na Conta Corrente " + numeroConta);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes da Conta Corrente ---");
        System.out.println("Número da Conta: " + numeroConta);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo Atual: R$" + saldo);
        System.out.println("Taxa de Manutenção: R$" + taxaManutencao);
        System.out.println("----------------------------------");
    }
}
