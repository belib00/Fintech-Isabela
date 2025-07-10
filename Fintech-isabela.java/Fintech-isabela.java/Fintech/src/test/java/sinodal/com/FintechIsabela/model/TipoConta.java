// src/main/java/com.sinodal/FinTechIsabela/model/TipoConta.java
package test.java.sinodal.com.FintechIsabela.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_conta") // Tabela para a superclasse abstrata
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança
public abstract class TipoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Removido 'rendimento' aqui, pois cada tipo de conta calculará de forma diferente

    // Construtor padrão
    public TipoConta() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Método abstrato para calcular rendimento
    public abstract Double calcularRendimento(Double saldoBase); // Passar saldo como parâmetro
}
