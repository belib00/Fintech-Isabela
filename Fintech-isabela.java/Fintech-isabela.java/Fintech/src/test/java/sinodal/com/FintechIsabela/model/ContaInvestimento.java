// src/main/java/com.sinodal/FinTechIsabela/model/ContaInvestimento.java
package test.java.sinodal.com.FintechIsabela.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contas_investimento") // Tabela específica para ContaInvestimento
public class ContaInvestimento extends TipoConta {
    private Double taxaRendimentoAnual; // Ex: 5% ao ano
    private String tipoInvestimento; // Ex: CDB, LCI, Tesouro Direto
    private Double impostoRendaPorcentagem; // Ex: 15%
    private Double cdiPorcentagem; // Ex: 100% do CDI

    // Construtor padrão
    public ContaInvestimento() {}

    public ContaInvestimento(Double taxaRendimentoAnual, String tipoInvestimento, Double impostoRendaPorcentagem, Double cdiPorcentagem) {
        this.taxaRendimentoAnual = taxaRendimentoAnual;
        this.tipoInvestimento = tipoInvestimento;
        this.impostoRendaPorcentagem = impostoRendaPorcentagem;
        this.cdiPorcentagem = cdiPorcentagem;
    }

    // Getters e Setters
    public Double getTaxaRendimentoAnual() { return taxaRendimentoAnual; }
    public void setTaxaRendimentoAnual(Double taxaRendimentoAnual) { this.taxaRendimentoAnual = taxaRendimentoAnual; }
    public String getTipoInvestimento() { return tipoInvestimento; }
    public void setTipoInvestimento(String tipoInvestimento) { this.tipoInvestimento = tipoInvestimento; }
    public Double getImpostoRendaPorcentagem() { return impostoRendaPorcentagem; }
    public void setImpostoRendaPorcentagem(Double impostoRendaPorcentagem) { this.impostoRendaPorcentagem = impostoRendaPorcentagem; }
    public Double getCdiPorcentagem() { return cdiPorcentagem; }
    public void setCdiPorcentagem(Double cdiPorcentagem) { this.cdiPorcentagem = cdiPorcentagem; }

    @Override
    public Double calcularRendimento(Double saldoBase) {
        // Exemplo simples: rendimento mensal baseado na taxa anual e CDI
        // Lógica de cálculo real de investimentos é mais complexa e envolveria datas, aportes, etc.
        if (saldoBase <= 0) {
            System.out.println("Saldo insuficiente para calcular rendimento de investimento.");
            return 0.0;
        }
        Double rendimentoBruto = saldoBase * (taxaRendimentoAnual / 12 / 100) * (cdiPorcentagem / 100);
        Double ir = rendimentoBruto * (impostoRendaPorcentagem / 100);
        Double rendimentoLiquido = rendimentoBruto - ir;
        System.out.println("Calculando rendimento da Conta Investimento. Rendimento líquido: R$" + String.format("%.2f", rendimentoLiquido));
        return rendimentoLiquido;
    }
}