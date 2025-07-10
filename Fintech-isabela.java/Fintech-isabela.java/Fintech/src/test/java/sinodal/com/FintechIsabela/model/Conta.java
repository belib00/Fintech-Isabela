// src/main/java/com/sinodal/FinTechIsabela/model/Conta.java


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A conta (entidade) está associada a uma conta bancária (entidade com saldo/operações)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "conta_bancaria_id", referencedColumnName = "id", unique = true)
    private ContaBancaria contaBancaria;

    // A conta (entidade) tem um tipo de conta (que define a lógica polimórfica)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // FetchType.EAGER se TipoConta for sempre necessária
    @JoinColumn(name = "tipo_conta_id", referencedColumnName = "id")
    private TipoConta tipoConta;

    // Outros atributos da entidade Conta, se houver
    private LocalDate dataAbertura;
    // ...

    // Construtor útil (pode ter outros com base nas suas necessidades)
    public Conta(ContaBancaria contaBancaria, TipoConta tipoConta, LocalDate dataAbertura) {
        this.contaBancaria = contaBancaria;
        this.tipoConta = tipoConta;
        this.dataAbertura = dataAbertura;
    }
}