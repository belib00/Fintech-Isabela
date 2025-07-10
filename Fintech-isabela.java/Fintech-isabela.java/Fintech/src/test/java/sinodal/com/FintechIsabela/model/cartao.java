// src/main/java/com.sinodal/FinTechIsabela/model/Cartao.java
import jakarta.persistence.*;
import java.time.LocalDate; // Usar LocalDate para validade

@Entity
@Table(name = "cartoes")
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false) // Número do cartão deve ser único e não nulo
    private String numero;
    private LocalDate validade; // Data de validade (Ex: 12/2027)
    private String codigoSeguranca; // CVV
    private String bandeiraCartao; // Ex: VISA, MASTERCARD

    @ManyToOne(fetch = FetchType.LAZY) // Muitos cartões para uma conta
    @JoinColumn(name = "conta_id", nullable = false) // Chave estrangeira para Conta
    private Conta conta; // O campo que mapeia o @OneToMany na classe Conta

    // Construtor padrão
    public Cartao() {}

    // Construtor com campos
    public Cartao(String numero, LocalDate validade, String codigoSeguranca, String bandeiraCartao, Conta conta) {
        this.numero = numero;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
        this.bandeiraCartao = bandeiraCartao;
        this.conta = conta;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    public String getCodigoSeguranca() { return codigoSeguranca; }
    public void setCodigoSeguranca(String codigoSeguranca) { this.codigoSeguranca = codigoSeguranca; }
    public String getBandeiraCartao() { return bandeiraCartao; }
    public void setBandeiraCartao(String bandeiraCartao) { this.bandeiraCartao = bandeiraCartao; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
}